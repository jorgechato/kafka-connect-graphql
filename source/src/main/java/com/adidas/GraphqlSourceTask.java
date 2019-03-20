package com.adidas;

import com.adidas.config.GraphqlSourceConfig;
import io.confluent.connect.avro.AvroData;
import io.confluent.connect.avro.AvroDataConfig;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.apache.kafka.connect.source.SourceTaskContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class GraphqlSourceTask extends SourceTask {
    private static final Logger LOG = LoggerFactory.getLogger(GraphqlSourceTask.class);

    private GraphqlSourceConfig sourceConfig;
    private Map props = new HashMap<String, String>();
    private AvroData avroData;
    private SourceService service;

    private volatile boolean stop = true;
    private volatile String cursor = "";

    @Override
    public void initialize(SourceTaskContext context) {
        Map<String, Object> offset = context
                .offsetStorageReader()
                .offset(
                        Collections.singletonMap(
                                GraphqlSourceConfig.Config.PARTITION_KEY,
                                GraphqlSourceConfig.Config.PARTITION
                        )
                );

        LOG.info(String.format("Is offset ready? %b", offset != null));

        if (offset != null) {
            String lastRecordedOffset = (String) offset.get(GraphqlSourceConfig.Config.OFFSET_KEY);
            if (lastRecordedOffset != null) {
                LOG.info(String.format("Offset value %s", lastRecordedOffset));
                this.cursor = lastRecordedOffset;
            }
        }
    }

    @Override
    public String version() {
        return GraphqlSourceConfig.Config.VERSION;
    }

    @Override
    public void start(Map<String, String> props) {
        LOG.info("Starting GraphqlSourceTask");

        this.stop = false;
        this.props = props;
        this.sourceConfig = new GraphqlSourceConfig(props);
        this.avroData = new AvroData(
                new AvroDataConfig(this.props)
        );

        this.service = new SourceService(
                this.sourceConfig.getURL(),
                this.sourceConfig.getTokenURL(),
                this.sourceConfig.getToken()
        );
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        LOG.info(String.format("Polling GraphqlSourceTask with offset %s", this.cursor));

        if (this.stop){
            return new ArrayList<>();
        }

        List<LogEventsRecord> records = new ArrayList<>();

        while (records.isEmpty()) {
            records = this.service.getRecords(this.cursor);

            if (records.isEmpty()) {
                Thread.sleep(this.sourceConfig.getSleepTime());
            } else {
                this.cursor = records.get(0).getCursor();
            }
        }

        Collections.reverse(records);

        return records
                .stream()
                .map(msg ->
                        GraphqlAvroBuilder.build(
                                this.sourceConfig,
                                this.avroData,
                                msg.getKey(), msg.getValue(),
                                GraphqlSourceConfig.Config.OFFSET_KEY, msg.getCursor()
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public void stop() {
        this.stop = true;
        LOG.info("Stopping GraphqlSourceTask");
    }
}

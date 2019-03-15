package com.adidas;

import com.adidas.config.GraphqlSourceConfig;
import io.confluent.connect.avro.AvroData;
import io.confluent.connect.avro.AvroDataConfig;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GraphqlSourceTask extends SourceTask {
    private static final Logger LOG = LoggerFactory.getLogger(GraphqlSourceTask.class);

    private GraphqlSourceConfig sourceConfig;
    private Map props = new HashMap<String, String>();
    private AvroData avroData;
    private SourceService service;

    private int sleepTimes;
    private boolean stop = true;
    private String cursor = "";

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
                this.sourceConfig.getHost(),
                this.sourceConfig.getBasePath(),
                this.sourceConfig.getToken()
        );
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        LOG.trace("Polling GraphqlSourceTask");

        if (this.stop){
            return new ArrayList<>();
        }

        this.sleepTimes = 0;

        List<LogEventsRecord> records = new ArrayList<>();

        while (records.isEmpty()){
            Thread.sleep(this.sourceConfig.getSleepTime() + this.sleepTimes);
            this.sleepTimes += 500;
            records = this.service.getRecords(this.cursor);
        }

        this.cursor = records.get(0).getCursor();

        return records
                .stream()
                .map(msg ->
                        GraphqlAvroBuilder.build(
                                this.sourceConfig,
                                this.avroData,
                                msg.getKey(),
                                msg.getValue(),
                                msg.getCursor()
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

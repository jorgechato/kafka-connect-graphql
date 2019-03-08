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

public class GraphqlSourceTask extends SourceTask {
    private Logger log = LoggerFactory.getLogger(GraphqlSourceConnector.class);

    private GraphqlSourceConfig sourceConfig;
    private Map props = new HashMap<String, String>();
    private AvroData avroData;

    private boolean stop = true;
    private long id = 0L;

    @Override
    public String version() {
        return GraphqlSourceConfig.Config.VERSION;
    }

    @Override
    public void start(Map<String, String> props) {
        log.info("Starting GraphqlSourceTask");

        this.stop = false;
        this.props = props;
        this.sourceConfig = new GraphqlSourceConfig(props);
        this.avroData = new AvroData(
                new AvroDataConfig(this.props)
        );
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        if (this.stop){
            return new ArrayList<SourceRecord>();
        }

        this.id++;

        List<SourceRecord> records = new ArrayList<>();
        SourceService service = new SourceService();

        records.add(
                GraphqlAvroBuilder.build(
                        this.sourceConfig,
                        this.avroData,
                        service.getKey(this.id),
                        service.getValue(),
                        this.id
                )
        );

        Thread.sleep(
                this.sourceConfig.getSleepTime()
        );

        return records;
    }

    @Override
    public void stop() {
        this.stop = true;
        log.info("Stopping GraphqlSourceTask");
    }
}

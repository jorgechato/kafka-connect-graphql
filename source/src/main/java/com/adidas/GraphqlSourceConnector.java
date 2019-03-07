package com.adidas;

import com.adidas.config.GraphqlSourceConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigException;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.apache.kafka.connect.errors.ConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class GraphqlSourceConnector extends SourceConnector {
    private Logger log = LoggerFactory.getLogger(GraphqlSourceConnector.class);

    private GraphqlSourceConfig sourceConfig;
    private final Map configParams = new HashMap<String, String>();

    @Override
    public String version() {
        return GraphqlSourceConfig.Config.VERSION;
    }

    @Override
    public void start(Map<String, String> props) {
        log.info("Starting GraphqlSourceConnector");

        this.configParams.putAll(props);
        try {
            this.sourceConfig = new GraphqlSourceConfig(props);
        } catch (ConfigException e) {
            throw new ConnectException("Couldn't start source due to conf error " + e);
        }
    }

    @Override
    public Class<? extends Task> taskClass() {
        return GraphqlSourceTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        List<Map<String, String>> configs = new ArrayList<>(maxTasks);
        Map <String, String> taskProps = new HashMap<>(this.configParams);

        for (int i = 0; i < maxTasks; i++){
            configs.add(taskProps);
        }

        return configs;
    }

    @Override
    public void stop() {
        log.info("Stopping GraphqlSourceConnector");
    }

    @Override
    public ConfigDef config() {
        return GraphqlSourceConfig.Config.conf();
    }
}

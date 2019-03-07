package com.adidas.config;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

public class GraphqlSourceConfig extends AbstractConfig {

    public GraphqlSourceConfig(Map<?, ?> originals) {
        super(Config.conf(), originals);
    }

    public long getSleepTime() {
        return this.getLong(Config.SLEEP_TIME);
    }

    public String getTopic() {
        return this.getString(Config.KAFKA_TOPIC);
    }


    public static final class Config {
        public static final String VERSION = "0.0.1";
        public static final String PARTITION = "sample";

        private static final String KAFKA_TOPIC = "kafka.topic";
        private static final String KAFKA_TOPIC_DOC = "Kafka topic to send messages";
        private static final String SLEEP_TIME = "connector.sleep.time";
        private static final String SLEEP_TIME_DOC = "how much time the connector should wait between messages";

        public static ConfigDef conf() {
            return new ConfigDef()
                    .define(
                            KAFKA_TOPIC,
                            ConfigDef.Type.STRING,
                            "default-sample-graphql-topic",
                            ConfigDef.Importance.LOW,
                            KAFKA_TOPIC_DOC
                    )
                    .define(
                            SLEEP_TIME,
                            ConfigDef.Type.LONG,
                            2000,
                            ConfigDef.Importance.LOW,
                            SLEEP_TIME_DOC
                    );
        }
    }
}

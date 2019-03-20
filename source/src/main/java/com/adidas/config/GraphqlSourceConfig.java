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

    public String getToken() {
        return this.getString(Config.PROVIDER_TOKEN);
    }

    public String getTokenURL() {
        return this.getString(Config.PROVIDER_TOKEN_URL);
    }

    public String getURL() {
        return this.getString(Config.PROVIDER_URL);
    }

    public static final class Config {
        public static final String VERSION = "0.0.1";
        public static final String PARTITION = "sample";

        private static final String KAFKA_TOPIC = "kafka.topic";
        private static final String KAFKA_TOPIC_DOC = "Kafka topic to send messages";
        private static final String SLEEP_TIME = "connector.sleep.time";
        private static final String SLEEP_TIME_DOC = "how much time the connector should wait between messages";

        private static final String PROVIDER_TOKEN = "provider.token";
        private static final String PROVIDER_TOKEN_DOC = "provider token if needed";
        private static final String PROVIDER_URL = "provider.url";
        private static final String PROVIDER_URL_DOC = "provider graphql url to get messages from";
        private static final String PROVIDER_TOKEN_URL = "provider.token.url";
        private static final String PROVIDER_TOKEN_URL_DOC = "provider token url if needed";

        public static ConfigDef conf() {
            return new ConfigDef()
                    .define(
                            PROVIDER_URL,
                            ConfigDef.Type.STRING,
                            ConfigDef.Importance.HIGH,
                            PROVIDER_URL_DOC
                    )
                    .define(
                            PROVIDER_TOKEN,
                            ConfigDef.Type.STRING,
                            ConfigDef.Importance.HIGH,
                            PROVIDER_TOKEN_DOC
                    )
                    .define(
                            PROVIDER_TOKEN_URL,
                            ConfigDef.Type.STRING,
                            ConfigDef.Importance.HIGH,
                            PROVIDER_TOKEN_URL_DOC
                    )
                    .define(
                            KAFKA_TOPIC,
                            ConfigDef.Type.STRING,
                            "default-sample-graphql-topic",
                            ConfigDef.Importance.MEDIUM,
                            KAFKA_TOPIC_DOC
                    )
                    .define(
                            SLEEP_TIME,
                            ConfigDef.Type.LONG,
                            1000,
                            ConfigDef.Importance.LOW,
                            SLEEP_TIME_DOC
                    );
        }
    }
}

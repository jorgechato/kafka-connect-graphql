package com.adidas.config;

import com.adidas.GraphqlSourceTask;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static Map<String, String> props;
    static {
        props = new HashMap<>();
        props.put("kafka.topic", "leanix-avro-source.connector");
        props.put("connector.class", "com.adidas.GraphqlSourceConnector");
        props.put("key.converter", "io.confluent.connect.avro.AvroConverter");
        props.put("key.converter.schema.registry.url", "http://schema-registry:8081");
        props.put("key.converter.key.subject.name.strategy", "io.confluent.kafka.serializers.subject.TopicRecordNameStrategy");
        props.put("value.converter", "io.confluent.connect.avro.AvroConverter");
        props.put("value.converter.schema.registry.url", "http://schema-registry:8081");
        props.put("value.converter.value.subject.name.strategy", "io.confluent.kafka.serializers.subject.TopicRecordNameStrategy");
        props.put("tasks.max", "1");

        props.put("provider.token", "VJx3bAwpemHKYNTGZkyMhjO4HNOF6sK9GyAaqzZz");
        props.put("provider.host", "adidas.leanix.net");
        props.put("provider.base.path", "https://adidas.leanix.net/services/pathfinder/v1");
    }
    
    public static void main(String[] args) throws InterruptedException {
        GraphqlSourceTask task = new GraphqlSourceTask();

        task.start(props);
        task.poll();
//                .stream()
//                .forEach(it ->
//                        System.out.println(it.toString())
//                );

        System.out.println(task.poll().size());
    }
}

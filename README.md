```zsh
$ curl -s --request POST \
      --url http://localhost:8084/api/kafka-connect-1/connectors \
      --header 'content-type: application/json' \
      --data '{
                "name": "GraphqlSourceConnector",
                "config": {
                  "kafka.topic": "avro",
                  "connector.class": "com.adidas.GraphqlSourceConnector",
                  "key.converter": "io.confluent.connect.avro.AvroConverter",
                  "key.converter.schema.registry.url": "http://schema-registry:8081",
                  "key.converter.key.subject.name.strategy": "io.confluent.kafka.serializers.subject.TopicRecordNameStrategy",
                  "value.converter": "io.confluent.connect.avro.AvroConverter",
                  "value.converter.schema.registry.url": "http://schema-registry:8081",
                  "value.converter.value.subject.name.strategy": "io.confluent.kafka.serializers.subject.TopicRecordNameStrategy",
                  "tasks.max": 1
                }
              }'
```
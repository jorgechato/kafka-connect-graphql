# GraphQL connector + Leanix module

## Build

```zsh
# Generate the .jar
$ gradle clean shadowJar

# Move .jar connector to connectors folder
$ mv build/libs/kafka-connect-leanix-1.0-SNAPSHOT-all.jar $CONNECT_PLUGIN_PATH
```

After you add the connector to the specific folder, keep in mind that you need to
restart the kafka connect service.

## Run

```zsh
$ curl -X POST http://<kafka-connect-ui-url>:<kafka-connect-ui-port>/api/kafka-connect-1/connectors \
      -H 'content-type: application/json' \
      -d @'source.template.json'
```

```json
{
  "name": "LeanixSourceConnector",
  "config": {
    "kafka.topic": "avro",
    "connector.class": "com.adidas.GraphqlSourceConnector",
    "key.converter": "io.confluent.connect.avro.AvroConverter",
    "key.converter.schema.registry.url": "http://schema-registry:8081",
    "key.converter.key.subject.name.strategy": "io.confluent.kafka.serializers.subject.TopicRecordNameStrategy",
    "value.converter": "io.confluent.connect.avro.AvroConverter",
    "value.converter.schema.registry.url": "http://schema-registry:8081",
    "value.converter.value.subject.name.strategy": "io.confluent.kafka.serializers.subject.TopicRecordNameStrategy",
    "tasks.max": 1,

    // time to sleep if the request to the provider is empty
    "connector.sleep.time": 1000,
    
    "provider.url": "<provider-graphql-url>",
    "provider.token.url": "<provider-token-request-url>",
    "provider.token": "<provider-basic-auth-token>"
  }
}
```

## Structure

This is a multi-module project.

```zsh
.
├── README.md
├── build.gradle
├── gradle.properties
├── settings.gradle
├── source.template.json
│
├── assets
│   ├── graphql_logo.png
│   └── leanix_logo.png
│
├── leanix # leanix module
│   ├── build.gradle
│   ├── gradle.properties
│   ├── settings.gradle
│   └── src
│       ├── main
│       │   ├── avro
│       │   │   ├── SampleMessage.avsc
│       │   │   └── SampleMessageKey.avsc
│       │   ├── java
│       │   │   └── com
│       │   │       └── adidas
│       │   │           ├── LogEventsRecord.java
│       │   │           ├── SourceService.java
│       │   │           ├── dao
│       │   │           │   ├── GetLogEventsByCursor.java
│       │   │           │   └── GraphQLQuery.java
│       │   │           └── dto
│       │   │               ├── AllLogEvents.java
│       │   │               ├── Edges.java
│       │   │               └── LogEvents.java
│       │   └── resources
│       │       └── leanix.properties
│       └── test
...
│
└── source # Kafka connect source module
    ├── build.gradle
    ├── settings.gradle
    └── src
        ├── main
        │   ├── java
        │   │   └── com
        │   │       └── adidas
        │   │           ├── GraphqlAvroBuilder.java
        │   │           ├── GraphqlSourceConnector.java
        │   │           ├── GraphqlSourceTask.java
        │   │           └── config
        │   │               └── GraphqlSourceConfig.java
        │   └── resources
        └── test
 ...
```
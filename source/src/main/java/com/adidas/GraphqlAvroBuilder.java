package com.adidas;

import com.adidas.config.GraphqlSourceConfig;
import io.confluent.connect.avro.AvroData;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.connect.data.SchemaAndValue;
import org.apache.kafka.connect.source.SourceRecord;

import java.util.HashMap;
import java.util.Map;


public final class GraphqlAvroBuilder {
    public static SourceRecord build (
            GraphqlSourceConfig sourceConfig,
            AvroData avroData,
            SpecificRecord key,
            SpecificRecord value,
            long id){
        Map partition = new HashMap<String, String>();
        partition.put("source", GraphqlSourceConfig.Config.PARTITION);
        Map offset = new HashMap<String, String>();
        offset.put("id", id);

        SchemaAndValue keySchemaAndValue = avroData.toConnectData(key.getSchema(), key);
        SchemaAndValue valueSchemaAndValue = avroData.toConnectData(value.getSchema(), value);

        return buildSourceRecord(
                partition,
                offset,
                sourceConfig,
                keySchemaAndValue,
                valueSchemaAndValue
        );
    }

    private static SourceRecord buildSourceRecord (
            Map<String, ?> partition,
            Map<String, ?> offset,
            GraphqlSourceConfig sourceConfig,
            SchemaAndValue keySchemaAndValue,
            SchemaAndValue valueSchemaAndValue){
        return new SourceRecord(
                partition, offset,
                sourceConfig.getTopic(),
                keySchemaAndValue.schema(),
                keySchemaAndValue.value(),
                valueSchemaAndValue.schema(),
                valueSchemaAndValue.value()
        );
    }
}

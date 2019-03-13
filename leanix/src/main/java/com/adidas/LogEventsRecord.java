package com.adidas;

import com.adidas.leanix.kafka.connector.source.sample.records.SampleMessage;
import com.adidas.leanix.kafka.connector.source.sample.records.SampleMessageKey;
import org.apache.avro.specific.SpecificRecord;

public class LogEventsRecord {
    public static final String MODEL = "LogEvents";

    private SpecificRecord key;
    private SpecificRecord value;
    private String cursor;

    public LogEventsRecord(String cursor, String id, String message, String newValue, String oldValue, long sec) {
        this.key = buildKey(id, this.MODEL);
        this.value = buildValue(
                message,
                newValue,
                oldValue,
                sec
        );
        this.cursor = cursor;
    }

    public SpecificRecord getKey() {
        return key;
    }

    public SpecificRecord getValue() {
        return value;
    }

    public String getCursor() {
        return cursor;
    }

    private SpecificRecord buildValue(String message, String newValue, String oldValue, long sec) {
        SampleMessage record = new SampleMessage();

        return record.newBuilder()
                .setMessage(message)
                .setNewValue(newValue)
                .setOldValue(oldValue)
                .setSecondsPast(sec)
                .build();
    }

    private SpecificRecord buildKey(String id, String model) {
        SampleMessageKey record = new SampleMessageKey();

        return record.newBuilder()
                .setId(id)
                .setModel(model)
                .build();
    }
}

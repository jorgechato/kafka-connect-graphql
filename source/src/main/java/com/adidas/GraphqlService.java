package com.adidas;

import com.adidas.graphql.kafka.connector.source.sample.records.SampleMessage;
import com.adidas.graphql.kafka.connector.source.sample.records.SampleMessageKey;
import org.apache.avro.specific.SpecificRecord;

import java.util.Random;

public class GraphqlService {
//    TODO: change random number to actual values

    public SpecificRecord getKey(long id) {
        SampleMessageKey msg = new SampleMessageKey();

        return msg.newBuilder()
                .setId(id)
                .build();
    }

    public SpecificRecord getValue() {
        SampleMessage msg = new SampleMessage();

        Random rn = new Random();

        return msg.newBuilder()
                .setSomeIntField(rn.nextInt())
                .build();
    }
}

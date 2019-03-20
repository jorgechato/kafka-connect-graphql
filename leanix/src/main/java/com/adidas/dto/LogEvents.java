package com.adidas.dto;

import io.aexp.nodes.graphql.annotations.GraphQLProperty;

@GraphQLProperty(name = "node")
public class LogEvents {
    private String id, oldValue, newValue, message;
    private long secondsPast;

    public String getId() {
        return id;
    }

    public String getOldValue() {
        return oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public String getMessage() {
        return message;
    }

    public long getSecondsPast() {
        return secondsPast;
    }
}

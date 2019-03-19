package com.adidas.dto;

import io.aexp.nodes.graphql.annotations.GraphQLProperty;

@GraphQLProperty(name = "node")
public class LogEvents {
    private String id, oldValue, newValue, message;
    private long secondsPast;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOldValue() {
        return oldValue == null ? "" : oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue == null ? "" : newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getSecondsPast() {
        return secondsPast;
    }

    public void setSecondsPast(long secondsPast) {
        this.secondsPast = secondsPast;
    }
}

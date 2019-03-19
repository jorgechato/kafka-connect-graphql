package com.adidas.dto;

import io.aexp.nodes.graphql.annotations.GraphQLProperty;

@GraphQLProperty(name = "edges")
public class Edges {
    private LogEvents node;
    private String cursor;

    public LogEvents getNode() {
        return node;
    }

    public void setNode(LogEvents node) {
        this.node = node;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }
}

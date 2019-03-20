package com.adidas.dto;

import io.aexp.nodes.graphql.annotations.GraphQLProperty;

@GraphQLProperty(name = "edges")
public class Edges {
    private LogEvents node;
    private String cursor;

    public LogEvents getNode() {
        return node;
    }

    public String getCursor() {
        return cursor;
    }
}

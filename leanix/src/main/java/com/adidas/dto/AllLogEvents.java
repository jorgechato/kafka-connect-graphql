package com.adidas.dto;

import io.aexp.nodes.graphql.annotations.GraphQLArgument;
import io.aexp.nodes.graphql.annotations.GraphQLProperty;

import java.util.List;

@GraphQLProperty(name="allLogEvents", arguments={
        @GraphQLArgument(name="before", value=""),
        @GraphQLArgument(name="factSheetId", value=""),
})
public class AllLogEvents {
    private List<Edges> edges;

    public List<Edges> getEdges() {
        return edges;
    }

    public void setEdges(List<Edges> edges) {
        this.edges = edges;
    }
}

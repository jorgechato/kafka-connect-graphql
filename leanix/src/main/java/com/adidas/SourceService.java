package com.adidas;

import com.adidas.dao.GetLogEventsByCursor;
import com.adidas.dao.GraphQLQuery;
import com.adidas.dto.Edges;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SourceService {
    private final GraphQLQuery client;

    public SourceService(GraphQLQuery client) {
        this.client = client;
    }

    public SourceService(String graphqlUrl, String tokenUrl, String token) {
        this(new GraphQLQuery(graphqlUrl, tokenUrl, token));
    }

    public List<LogEventsRecord> getRecords (String cursor) {
        return getEdges(cursor)
                .stream()
                .map(edge ->
                        new LogEventsRecord(
                                edge.getCursor(),
                                edge.getNode().getId(),
                                edge.getNode().getMessage(),
                                edge.getNode().getNewValue(),
                                edge.getNode().getOldValue(),
                                edge.getNode().getSecondsPast()
                        )
                )
                .collect(Collectors.toList());
    }

    private List<Edges> getEdges(String cursor) {
        try {
            return new GetLogEventsByCursor(cursor, this.client.build())
                    .execute()
                    .getResponse()
                    .getEdges();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}

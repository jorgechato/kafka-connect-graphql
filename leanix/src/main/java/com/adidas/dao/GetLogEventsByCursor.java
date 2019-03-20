package com.adidas.dao;

import com.adidas.dto.AllLogEvents;
import io.aexp.nodes.graphql.*;

public class GetLogEventsByCursor {
    private final String before;
    private final String factSheetId;
    private final GraphQLRequestEntity.RequestBuilder builder;
    private final GraphQLTemplate template;

    private static final String DEFAULT_FACT_SHEET_ID = "ec254751-86be-4b7d-82b8-b716bedb87ce";

    public GetLogEventsByCursor(String before, String factSheetId, GraphQLRequestEntity.RequestBuilder builder, GraphQLTemplate template) {
        this.before = before;
        this.factSheetId = factSheetId;
        this.builder = builder;
        this.template = template;
    }

    public GetLogEventsByCursor(String before, String factSheetId, GraphQLRequestEntity.RequestBuilder builder) {
        this(before, factSheetId, builder, new GraphQLTemplate());
    }

    public GetLogEventsByCursor(String before, GraphQLRequestEntity.RequestBuilder builder) {
        this(before, DEFAULT_FACT_SHEET_ID, builder);
    }

    private GraphQLRequestEntity build() {
        return this.builder
                .arguments(
                        new Arguments(
                                "allLogEvents",
                                new Argument("before", this.before),
                                new Argument("factSheetId", this.factSheetId)
                        )
                )
                .request(AllLogEvents.class)
                .build();
    }

    public GraphQLResponseEntity<AllLogEvents> execute() {
        return template.query(
                build(),
                AllLogEvents.class
        );
    }
}

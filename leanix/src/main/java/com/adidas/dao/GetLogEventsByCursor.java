package com.adidas.dao;

import com.adidas.dto.AllLogEvents;
import io.aexp.nodes.graphql.*;

public class GetLogEventsByCursor {
    private final String BEFORE;
    private final String FACT_SHEET_ID;
    private final GraphQLRequestEntity.RequestBuilder BUILDER;

    private static final String DEFAULT_FACT_SHEET_ID = "ec254751-86be-4b7d-82b8-b716bedb87ce";
    private static final GraphQLTemplate TEMPLATE = new GraphQLTemplate();

    public GetLogEventsByCursor(String BEFORE, String FACT_SHEET_ID, GraphQLRequestEntity.RequestBuilder BUILDER) {
        this.BEFORE = BEFORE;
        this.FACT_SHEET_ID = FACT_SHEET_ID;
        this.BUILDER = BUILDER;
    }

    public GetLogEventsByCursor(String BEFORE, GraphQLRequestEntity.RequestBuilder BUILDER) {
        this(BEFORE, DEFAULT_FACT_SHEET_ID, BUILDER);
    }

    private GraphQLRequestEntity build() {
        return this.BUILDER
                .arguments(
                        new Arguments(
                                "allLogEvents",
                                new Argument("before", this.BEFORE),
                                new Argument("factSheetId", this.FACT_SHEET_ID)
                        )
                )
                .request(AllLogEvents.class)
                .build();
    }

    public GraphQLResponseEntity<AllLogEvents> execute() {
        return TEMPLATE.query(
                build(),
                AllLogEvents.class
        );
    }
}

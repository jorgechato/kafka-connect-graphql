package com.adidas;

public class GetLogEventsByCursor extends GraphQLQuery {
    private static final String DEFAULT_MODEL = "edges { node { id, oldValue, newValue, message, secondsPast }, cursor }";
    private static final String BODY_TEMPLATE = "{ allLogEvents( before: \"%s\", factSheetId: \"%s\" ) { %s } }";
    private static final String FACT_SHEET_ID = "ec254751-86be-4b7d-82b8-b716bedb87ce";

    private String cursor;
    private final String returnModel;

    public GetLogEventsByCursor(String cursor) {
        this(cursor, DEFAULT_MODEL);
    }

    public GetLogEventsByCursor(String cursor, String returnModel) {
        this.cursor = cursor;
        this.returnModel = returnModel;
    }

    @Override
    public String build() {
        return String.format(
                this.BODY_TEMPLATE,
                this.cursor,
                this.FACT_SHEET_ID,
                this.returnModel
        );
    }
}

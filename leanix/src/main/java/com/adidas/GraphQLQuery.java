package com.adidas;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.leanix.api.GraphqlApi;
import net.leanix.api.common.ApiClient;
import net.leanix.api.common.ApiException;
import net.leanix.api.models.GraphQLRequest;
import net.leanix.api.models.GraphQLResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class GraphQLQuery {
    private static final ObjectMapper mapper;
    static {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private static final Logger LOG = LoggerFactory.getLogger(GraphQLQuery.class);

    public static JsonNode executeQuery(ApiClient client, GraphQLQuery query) throws ApiException {
        GraphQLRequest gqlRequest = new GraphQLRequest();
        gqlRequest.setQuery(query.build());

        GraphQLResult res = new GraphqlApi(client).processGraphQL(gqlRequest);

        if (res.getErrors() != null) {
            throw new ApiException(res.getErrors().toString());
        }
        return mapper.valueToTree(res.getData());
    }

    public JsonNode execute(ApiClient client) throws ApiException {
        return GraphQLQuery.executeQuery(client, this);
    }

    public abstract String build();
}

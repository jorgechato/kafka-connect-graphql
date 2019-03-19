package com.adidas.dao;

import io.aexp.nodes.graphql.GraphQLRequestEntity;
import okhttp3.*;
import org.json.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class GraphQLQuery {
    private static final OkHttpClient CLIENT = new OkHttpClient();

    private static Map<String, String> props = new HashMap<>();

    private final String GRAPHQL_URL;
    private final String TOKEN_URL;
    private final String ACCESS_TOKEN;

    private String token;

    public GraphQLQuery(String GRAPHQL_URL, String TOKEN_URL, String ACCESS_TOKEN, String token) {
        this.GRAPHQL_URL = GRAPHQL_URL;
        this.TOKEN_URL = TOKEN_URL;
        this.ACCESS_TOKEN = ACCESS_TOKEN;
        this.token = token;
    }

    public GraphQLQuery(String GRAPHQL_URL, String TOKEN_URL, String ACCESS_TOKEN) {
        this(GRAPHQL_URL, TOKEN_URL, ACCESS_TOKEN, null);
        this.token = getToken();
    }

    public GraphQLRequestEntity.RequestBuilder build() throws MalformedURLException {
        props.put(
                "Authorization",
                String.format("Bearer %s", this.token)
        );

        return GraphQLRequestEntity
                .Builder()
                .url(this.GRAPHQL_URL)
                .headers(props);
    }

    private String getToken(){
        try {
            Request request = new Request
                    .Builder()
                    .url(this.TOKEN_URL)
                    .post(
                            RequestBody.create(
                                    MediaType.parse("application/x-www-form-urlencoded"),
                                    "grant_type=client_credentials"
                            )
                    )
                    .addHeader(
                            "authorization",
                            Credentials.basic(
                                    "apitoken",
                                    this.ACCESS_TOKEN
                            )
                    )
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .build();

            return new JSONObject(
                    CLIENT
                            .newCall(request)
                            .execute()
                            .body()
                            .string()
            )
                    .getString("access_token");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return token;
    }
}

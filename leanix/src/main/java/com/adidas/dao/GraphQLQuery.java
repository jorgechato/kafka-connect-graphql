package com.adidas.dao;

import io.aexp.nodes.graphql.GraphQLRequestEntity;
import okhttp3.*;
import org.json.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class GraphQLQuery {
    private final OkHttpClient client;

    private static Map<String, String> props = new HashMap<>();

    private final String graphqlUrl;
    private final String tokenUrl;
    private final String accessToken;

    private String token;

    public GraphQLQuery(OkHttpClient client, String graphqlUrl, String tokenUrl, String accessToken, String token) {
        this.client = client;
        this.graphqlUrl = graphqlUrl;
        this.tokenUrl = tokenUrl;
        this.accessToken = accessToken;
        this.token = token;
    }

    public GraphQLQuery(String graphqlUrl, String tokenUrl, String accessToken, String token) {
        this(new OkHttpClient(), graphqlUrl, tokenUrl, accessToken, token);
    }

    public GraphQLQuery(String graphqlUrl, String tokenUrl, String accessToken) {
        this(graphqlUrl, tokenUrl, accessToken, null);
        this.token = getToken();
    }

    public GraphQLRequestEntity.RequestBuilder build() throws MalformedURLException {
        props.put(
                "Authorization",
                String.format("Bearer %s", getToken())
        );

        return GraphQLRequestEntity
                .Builder()
                .url(this.graphqlUrl)
                .headers(props);
    }

    private String getToken(){
        try {
            Request request = new Request
                    .Builder()
                    .url(this.tokenUrl)
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
                                    this.accessToken
                            )
                    )
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .build();

            return new JSONObject(
                    client
                            .newCall(request)
                            .execute()
                            .body()
                            .string()
            )
                    .getString("access_token");
        } catch (IOException e) {
//            TODO
            e.printStackTrace();
        }

        return this.token;
    }
}

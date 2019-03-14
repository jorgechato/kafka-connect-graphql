package com.adidas;

import com.adidas.config.LeanixConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.leanix.api.common.ApiClient;
import net.leanix.api.common.ApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SourceService {
    private final ApiClient client;
    private final ObjectMapper mapper = new ObjectMapper();

    public SourceService(ApiClient client) {
        this.client = client;
    }

    public SourceService(String host, String basePath, String token) {
        this(LeanixConfig.client(
                host,
                basePath,
                token
        ));
    }

    public List<LogEventsRecord> getRecords (String cursor) {
        return getEdges(cursor)
                .stream()
                .map(edge -> {
                    String newValue = edge.get("node")
                            .has("newValue") ?
                            edge.get("node")
                                    .get("newValue")
                                    .asText() :
                            "null";

                    String oldValue = edge.get("node")
                            .has("oldValue") ?
                            edge.get("node")
                                    .get("oldValue")
                                    .asText() :
                            "null";

                    return new LogEventsRecord(
                            edge.get("cursor").asText(),
                                edge.get("node").get("id").asText(),
                                edge.get("node").get("message").asText(),
                                newValue,
                                oldValue,
                                edge.get("node").get("secondsPast").asLong()
                        );
                })
                .collect(Collectors.toList());
    }

    private List<JsonNode> getEdges(String cursor) {
        try {
            return mapper.readValue(
                    new GetLogEventsByCursor(cursor)
                            .execute(client)
                            .get("allLogEvents")
                            .get("edges")
                            .traverse(),
                    mapper
                            .getTypeFactory()
                            .constructCollectionType(
                                    List.class,
                                    JsonNode.class
                            )
            );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return new ArrayList<JsonNode>();
    }
}

package com.adidas;

import com.adidas.config.LeanixConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.leanix.api.common.ApiClient;
import net.leanix.api.common.ApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SourceService {
    private ApiClient client;

    public SourceService(String host, String basePath, String token) {
        this.client = LeanixConfig.client(
                host,
                basePath,
                token
        );
    }

    public List<LogEventsRecord> getRecords (String cursor) {
        return getEdges(cursor)
                .stream()
                .map(edge ->
                        new LogEventsRecord(
                                edge.get("cursor").toString(),
                                edge.get("node").get("id").toString(),
                                edge.get("node").get("message").toString(),
                                edge.get("node").get("newValue").toString(),
                                edge.get("node").get("oldValue").toString(),
                                edge.get("node").get("secondsPast").asLong()
                        )
                )
                .collect(Collectors.toList());
    }

    private List<JsonNode> getEdges(String cursor) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(
                    new GetLogEventsByCursor(cursor)
                            .execute(client)
                            .get("allLogEvents")
                            .get("edges")
                            .toString(),
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

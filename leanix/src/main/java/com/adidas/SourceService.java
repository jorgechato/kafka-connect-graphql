package com.adidas;

import com.adidas.config.LeanixConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.leanix.api.common.ApiClient;
import net.leanix.api.common.ApiException;
import net.leanix.api.common.auth.ClientCredentialRefreshingOAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SourceService {
    private final ApiClient client;
    private final ObjectMapper mapper = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(SourceService.class);

    public SourceService(ApiClient client) {
        this.client = client;
    }

    public SourceService(String host, String basePath, String token) {
        this(LeanixConfig.client(
                host,
                basePath,
                token
        ));

        ClientCredentialRefreshingOAuth tk = (ClientCredentialRefreshingOAuth) this.client.getAuthentication("token");
        LOG.info(String.format("CLIENT 2 JORGE\n%s\n", tk.getAccessToken()));
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
                            .execute(this.client)
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

package com.adidas.config;

import net.leanix.api.common.ApiClient;
import net.leanix.api.common.ApiClientBuilder;

public class LeanixConfig {
    public static ApiClient client(String host, String basePath, String token) {
        return new ApiClientBuilder()
                .withApiToken(token)
                .withTokenProviderHost(host)
                .withBasePath(basePath)
                .withDebugging(true)
                .build();
    }
}

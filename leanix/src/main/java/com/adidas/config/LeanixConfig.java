package com.adidas.config;

import net.leanix.api.common.ApiClient;
import net.leanix.api.common.ApiClientBuilder;

public class LeanixConfig {

    public static ApiClient client(String url, String basePath, String token) {
        return new ApiClientBuilder()
                .withApiToken(token)
                .withTokenProviderHost(url)
                .withBasePath(basePath)
                .withDebugging(true)
                .build();
    }
}

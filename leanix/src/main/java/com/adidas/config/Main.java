package com.adidas.config;

import com.adidas.SourceService;

public class Main {
    public static void main(String[] args) {
        SourceService service = new SourceService(
                "adidas.leanix.net",
                "https://adidas.leanix.net/services/pathfinder/v1",
                "VJx3bAwpemHKYNTGZkyMhjO4HNOF6sK9GyAaqzZz"
        );

        System.out.println(
                service
                        .getRecords("")
                        .get(0)
                        .getCursor()
        );
    }
}

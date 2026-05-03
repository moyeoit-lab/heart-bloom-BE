package com.heartbloom.be.app.application.bouquet.implementation.generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BouquetLinkUrlGenerator {

    @Value("${bouquet.link.prefix}")
    private String prefix;

    private final String BOUQUET_LINK_PATH = "/bouquet/";

    public String generate(String linkToken, String origin) {
        String baseUrl = origin != null ? origin + BOUQUET_LINK_PATH : prefix;
        return baseUrl + linkToken;
    }

}
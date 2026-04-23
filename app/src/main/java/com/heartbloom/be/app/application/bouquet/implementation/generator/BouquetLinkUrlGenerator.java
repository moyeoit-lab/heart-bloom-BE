package com.heartbloom.be.app.application.bouquet.implementation.generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BouquetLinkUrlGenerator {

    @Value("${bouquet.link.prefix}")
    private String prefix;

    public String generate(String linkToken) {
        return prefix + linkToken;
    }

}
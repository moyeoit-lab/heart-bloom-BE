package com.heartbloom.be.app.application.bouquet.implementation.generator;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BouquetLinkTokenGenerator {

    public String generate() {
        // 하이픈 제거
        return UUID.randomUUID().toString().replace("-", "");
    }

}

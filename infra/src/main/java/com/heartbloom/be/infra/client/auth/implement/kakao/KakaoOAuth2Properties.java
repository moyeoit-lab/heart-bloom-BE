package com.heartbloom.be.infra.client.auth.implement.kakao;

import com.heartbloom.be.infra.client.auth.OAuth2Properties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oauth2.kakao")
public class KakaoOAuth2Properties extends OAuth2Properties {}
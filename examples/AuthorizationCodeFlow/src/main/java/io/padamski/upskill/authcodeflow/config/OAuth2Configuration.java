package io.padamski.upskill.authcodeflow.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("oauth2")
@Data
public class OAuth2Configuration {
    private String domain;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
}

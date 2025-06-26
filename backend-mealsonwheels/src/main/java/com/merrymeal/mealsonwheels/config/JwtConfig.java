package com.merrymeal.mealsonwheels.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class JwtConfig {

    private String jwtSecret;
    private long jwtExpirationInMs;
}

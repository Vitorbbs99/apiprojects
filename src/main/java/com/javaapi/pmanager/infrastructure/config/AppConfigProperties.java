package com.javaapi.pmanager.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
@Data
public class AppConfigProperties {

    private final General general;

    @Data
    public static class General {
        private final int pageSize;
    }
}

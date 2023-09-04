package com.sky.ombdservice.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "com.sky.ombdservice")
public class OmdbConfigurationProperties {

    private OMDB omdb = new OMDB();

    @Data
    public class OMDB {
        private String apiKey;
        private String url;
    }

}

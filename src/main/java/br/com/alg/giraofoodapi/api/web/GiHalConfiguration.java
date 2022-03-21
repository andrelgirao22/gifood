package br.com.alg.giraofoodapi.api.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;
import org.springframework.http.MediaType;

@Configuration
public class GiHalConfiguration {

    @Bean
    public HalConfiguration globalPolicy() {
        return new HalConfiguration()
                .withMediaType(MediaType.APPLICATION_JSON)
                .withMediaType(GiMediaTypes.V1_APPLICATION_JSON)
                .withMediaType(GiMediaTypes.V2_APPLICATION_JSON);
    }

}

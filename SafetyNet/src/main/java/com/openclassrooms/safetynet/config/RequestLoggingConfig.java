package com.openclassrooms.safetynet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingConfig {

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);  // Limite de la taille des logs des données POST
        filter.setIncludeHeaders(true);  // Ajoute les en-têtes dans le log
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }
}

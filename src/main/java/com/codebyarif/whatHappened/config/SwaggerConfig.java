package com.codebyarif.whatHappened.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
    private static final String SWAGGER_URL = "/swagger-ui/index.html";

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {

        registry.addRedirectViewController("/", SWAGGER_URL);
        registry.addRedirectViewController("/swagger-ui", SWAGGER_URL);
        registry.addRedirectViewController("swagger-ui.html", SWAGGER_URL);
    }
}

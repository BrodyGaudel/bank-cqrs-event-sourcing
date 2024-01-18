package com.brodygaudel.bank.common.configuration;


import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for Web-related settings.
 *
 * <p>
 * This class defines configuration settings related to the web environment, such as Cross-Origin Resource Sharing (CORS).
 * It includes a method to configure CORS for the application using the allowed origins and headers specified in the
 * properties file.
 * </p>
 *
 * @see Configuration
 */
@Configuration
public class WebConfiguration {

    /**
     * The allowed origins for CORS configuration. Retrieved from the application properties.
     */
    @Value("${allowed.origins}")
    private String allowedOrigins;

    /**
     * Configures CORS for the application.
     *
     * <p>
     * This method creates a WebMvcConfigurer bean to customize CORS settings. It allows requests from specified origins,
     * headers, and methods. The configuration is retrieved from the application properties.
     * </p>
     *
     * @return The configured WebMvcConfigurer bean for CORS.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedHeaders(HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION)
                        .exposedHeaders(HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION)
                        .maxAge(3600)
                        .allowedMethods(
                                HttpMethod.GET.name(),
                                HttpMethod.POST.name(),
                                HttpMethod.PUT.name(),
                                HttpMethod.DELETE.name()
                        );
            }
        };
    }
}


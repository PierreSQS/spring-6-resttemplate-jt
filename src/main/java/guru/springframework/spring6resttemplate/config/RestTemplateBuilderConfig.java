package guru.springframework.spring6resttemplate.config;

import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;

/**
 * Modified by Pierrot on 03-06-2024.
 */
@Configuration
public class RestTemplateBuilderConfig {

    public static final String BASE_URL = "http://localhost:8080";

    @Bean
    RestTemplateBuilder restTemplateBuilder(RestTemplateBuilderConfigurer configurer) {
        RestTemplateBuilder builder = configurer.configure(new RestTemplateBuilder());
        DefaultUriBuilderFactory builderFactory = new DefaultUriBuilderFactory(BASE_URL);

        return builder.uriTemplateHandler(builderFactory);
    }
}

package guru.springframework.spring6resttemplate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;

/**
 * Re-Modified by Pierrot on 17-06-2024.
 */
@Configuration
public class RestTemplateBuilderConfig {

    @Value("${rest.template.rootURL}")
    String rootUrl;

    @Value("${rest.template.user}")
    String user;

    @Value("${rest.template.password}")
    String password;

    @Bean
    RestTemplateBuilder restTemplateBuilder(RestTemplateBuilderConfigurer configurer) {

        assert rootUrl != null;

        RestTemplateBuilder builder = configurer.configure(new RestTemplateBuilder());
        DefaultUriBuilderFactory builderFactory = new DefaultUriBuilderFactory(rootUrl);

        RestTemplateBuilder builderWithAuth = builder.basicAuthentication(user, password);

        return builderWithAuth.uriTemplateHandler(builderFactory);
    }
}

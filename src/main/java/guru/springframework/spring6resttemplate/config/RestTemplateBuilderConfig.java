package guru.springframework.spring6resttemplate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;

/**
 * Created by jt, Spring Framework Guru.
 */
@Configuration
public class RestTemplateBuilderConfig {

    @Value("user")
    private String username;
    @Value("password")
    private String password;
    @Value("${rest.template.rootUrl}")
    String rootUrl;

    @Bean
    RestTemplateBuilder restTemplateBuilder(RestTemplateBuilderConfigurer configurer){

        assert rootUrl != null;

        return configurer.configure(new RestTemplateBuilder())
                .basicAuthentication(username,password)
                .uriTemplateHandler(new DefaultUriBuilderFactory(rootUrl));
    }
}

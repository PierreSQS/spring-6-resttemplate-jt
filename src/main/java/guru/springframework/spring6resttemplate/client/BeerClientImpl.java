package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Modified by Pierrot, 06.02.2023.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BeerClientImpl implements BeerClient {

    private static final String API_URL = "/api/v1/beer";
    private static final String BASE_URL = "http://localhost:8080";
    private final RestTemplateBuilder restTemplateBuilder;

    @Override
    public Page<BeerDTO> listBeers() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<String> respEntity =
                restTemplate.getForEntity(BASE_URL+API_URL, String.class);

        System.out.printf("The Response Body: %s%n",respEntity.getBody());

        return null;
    }
}

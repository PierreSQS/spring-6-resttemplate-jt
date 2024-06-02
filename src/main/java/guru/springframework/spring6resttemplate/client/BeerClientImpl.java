package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Modified by Pierrot on 01-06-2024.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BeerClientImpl implements BeerClient {

    public static final String BASE_URL = "http://localhost:8080";
    public static final String API_URL = "/api/v1/beer";

    private final RestTemplateBuilder restTemplateBuilder;

    @Override
    public Page<BeerDTO> listBeers() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<String> stringResponseEntity =
                restTemplate.getForEntity(BASE_URL + API_URL, String.class);

        log.info("the Response content: {}", stringResponseEntity.getBody());

        ResponseEntity<Map> mapResponseEntity =
                restTemplate.getForEntity(BASE_URL + API_URL, Map.class);

        log.info("the Response content: {}", mapResponseEntity.getBody());

        return null;
    }
}

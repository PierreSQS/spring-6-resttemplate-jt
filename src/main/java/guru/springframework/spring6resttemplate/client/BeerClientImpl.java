package guru.springframework.spring6resttemplate.client;

import com.fasterxml.jackson.databind.JsonNode;
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

        ResponseEntity<String> stringRespEntity =
                restTemplate.getForEntity(BASE_URL+API_URL, String.class);

        ResponseEntity<Map> mapRespEntity =
                restTemplate.getForEntity(BASE_URL+API_URL, Map.class);


        ResponseEntity<JsonNode> jsonNodRespEntity =
                restTemplate.getForEntity(BASE_URL+API_URL, JsonNode.class);

        JsonNode jsonNode = jsonNodRespEntity.getBody().findPath("content");

        System.out.printf("The String Response Body: %s%n",stringRespEntity.getBody());
        System.out.printf("%nThe first Item of the Key 'content' in the Map Response Body:%n%s%n",mapRespEntity.getBody().get("content"));

        System.out.println("\nThe items from the key 'content' of the JSON-Node:");

        jsonNodRespEntity.getBody().findPath("content")
                .elements().forEachRemaining(node ->
                        System.out.println(node.get("beerName").asText()));

        System.out.printf("%nThe content of the json-node:%n%s%n",jsonNode.toString());

        return null;
    }
}

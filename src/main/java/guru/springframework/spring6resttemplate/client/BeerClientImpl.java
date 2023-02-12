package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerDTOPageImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Modified by Pierrot, 06.02.2023.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BeerClientImpl implements BeerClient {

    private static final String BEER_API_URL = "/api/v1/beer";
    private final RestTemplateBuilder restTemplateBuilder;

    @Override
    public Page<BeerDTO> listBeers() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        // introduced URI-Builder for Query-Parameters later
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(BEER_API_URL);

        ResponseEntity<BeerDTOPageImpl> pageRespEntity =
                restTemplate.getForEntity(uriComponentsBuilder.toUriString(), BeerDTOPageImpl.class);

        BeerDTOPageImpl respEntityBody = pageRespEntity.getBody();
        if (respEntityBody != null) {
            log.info("the amount of BeerDTOs in the response body-Page1: {}", respEntityBody.getContent().size());
        } else {
            log.info("no BeerDTOs found in the response body-Page1");

        }

        return respEntityBody;
    }
}

package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerDTOPageImpl;
import guru.springframework.spring6resttemplate.model.BeerStyle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

/**
 * Modified by Pierrot on 04-06-2024.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BeerClientImpl implements BeerClient {

    public static final String BEER_API_URL = "/api/v1/beer";

    private final RestTemplateBuilder restTemplateBuilder;

    @Override
    public Page<BeerDTO> listBeers() {
        return listBeers(null,null,null, null, null);
    }

    @Override
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle,
                                   Boolean showInventory, Integer pageNumber, Integer pageSize) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(BEER_API_URL);

        if (beerName != null) {
            uriComponentsBuilder.queryParam("beerName", beerName);
        }

        if (beerName != null) {
            uriComponentsBuilder.queryParam("beerStyle", beerStyle);
        }

        if (beerName != null) {
            uriComponentsBuilder.queryParam("showInventory", showInventory);
        }

        if (beerName != null) {
            uriComponentsBuilder.queryParam("pageNumber", pageNumber);
        }

        if (beerName != null) {
            uriComponentsBuilder.queryParam("pageSize", pageSize);
        }

        ResponseEntity<BeerDTOPageImpl> pageRespEntity =
                restTemplate.getForEntity(uriComponentsBuilder.toUriString(), BeerDTOPageImpl.class);

        BeerDTOPageImpl body = pageRespEntity.getBody();

        log.info("the content as String: {}", Objects.requireNonNull(body));

        return body;
    }
}

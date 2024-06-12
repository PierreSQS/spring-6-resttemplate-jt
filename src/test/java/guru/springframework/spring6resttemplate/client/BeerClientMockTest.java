package guru.springframework.spring6resttemplate.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6resttemplate.config.RestTemplateBuilderConfig;
import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerDTOPageImpl;
import guru.springframework.spring6resttemplate.model.BeerStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.web.client.MockServerRestTemplateCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestToUriTemplate;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withAccepted;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withNoContent;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withResourceNotFound;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Modified by Pierrot on 12-06-2024.
 */
@RestClientTest
@Import(RestTemplateBuilderConfig.class)
class BeerClientMockTest {

    static final String URL = "http://localhost:8080";

    BeerClient beerClient;

    MockRestServiceServer server;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RestTemplateBuilder restTemplateBuilderSpringConfigured;

    @Mock
    RestTemplateBuilder mockRestTemplateBuilder = new RestTemplateBuilder(new MockServerRestTemplateCustomizer());
    
    BeerDTO beerDTO;
    
    String jsonResponse;

    @BeforeEach
    void setUp() throws JsonProcessingException{
        RestTemplate restTemplate = restTemplateBuilderSpringConfigured.build();
        server = MockRestServiceServer.bindTo(restTemplate).build();

        when(mockRestTemplateBuilder.build()).thenReturn(restTemplate);

        beerClient = new BeerClientImpl(mockRestTemplateBuilder);

        beerDTO = getBeerDto();

        jsonResponse = objectMapper.writeValueAsString(beerDTO);
    }


    @Test
    void testListBeersWithQueryParams() throws JsonProcessingException {
        // WHEN
        String response = objectMapper.writeValueAsString(getPage());

        URI uri = UriComponentsBuilder.fromHttpUrl(URL + BeerClientImpl.BEER_API_URL)
                .queryParam("beerName","ALE")
                .build()
                .toUri();

        // Mock Server DELETE
        server.expect(method(HttpMethod.GET))
                .andExpect(requestTo(uri))
                .andExpect(queryParam("beerName","ALE"))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        // THEN
        Page<BeerDTO> beerDTOPage = beerClient
                .listBeers("ALE", null, null, null, null);

        assertThat(beerDTOPage.getContent()).isNotEmpty();
    }

    @Test
    void testDeleteBeerNotFound() {
        // WHEN
        // Mock Server DELETE
        server.expect(method(HttpMethod.DELETE))
                .andExpect(requestToUriTemplate(URL+BeerClientImpl.BEER_ID_API_URL, beerDTO.getId()))
                .andRespond(withResourceNotFound());

        UUID id = beerDTO.getId();

        // THEN
        // Assert that Exception is thrown when making the DELETE Call to Server
        assertThatExceptionOfType(HttpClientErrorException.class)
                .isThrownBy(() -> beerClient.deleteBeer(id))
                .withMessageContaining("404 Not Found");

        // verify call to server occurred
        server.verify();

    }

    @Test
    void testDeleteBeer() {
        // WHEN
        // Mock Server DELETE
        server.expect(method(HttpMethod.DELETE))
                .andExpect(requestToUriTemplate(URL+BeerClientImpl.BEER_ID_API_URL, beerDTO.getId()))
                .andRespond(withNoContent());

        // THEN
        // Make the DELETE Call to Server
        beerClient.deleteBeer(beerDTO.getId());

        // Verify that the previous call occurred
        // since the DELETE operation is a Void operation
        server.verify();
    }

    @Test
    void testUpdateBeer() {

        // WHEN
        // Mock Server PUT
        server.expect(method(HttpMethod.PUT))
                .andExpect(requestToUriTemplate(URL+BeerClientImpl.BEER_ID_API_URL, beerDTO.getId()))
                .andRespond(withNoContent());

        // As the name says, Mock Server GET
        mockGetOperation();

        // THEN
        // Make the PUT Call to Server
        BeerDTO updateBeerDTO = beerClient.updateBeer(this.beerDTO);

        // ASSERT
        assertThat(updateBeerDTO.getId()).isEqualTo(beerDTO.getId());
    }

    @Test
    void testCreateBeer() {

        URI uri = UriComponentsBuilder.fromPath(BeerClientImpl.BEER_ID_API_URL)
                .build(beerDTO.getId());

        server.expect(method(HttpMethod.POST))
                .andExpect(requestTo(URL + BeerClientImpl.BEER_API_URL))
                .andRespond(withAccepted().location(uri));

        mockGetOperation();

        BeerDTO responseBeerDTO = beerClient.createBeer(beerDTO);

        System.out.println(responseBeerDTO.toString());

        assertThat(responseBeerDTO.getBeerName()).isEqualTo(beerDTO.getBeerName());
    }

    @Test
    void testGetByBeerId() {

        mockGetOperation();

        BeerDTO foundBeerDTO = beerClient.getBeerById(beerDTO.getId());

        assertThat(foundBeerDTO.getBeerName()).isEqualTo(beerDTO.getBeerName());
    }

    private void mockGetOperation() {
        server.expect(method(HttpMethod.GET))
                .andExpect(requestToUriTemplate(URL + BeerClientImpl.BEER_ID_API_URL,beerDTO.getId()))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));
    }

    @Test
    void testListBeers() throws JsonProcessingException {
        String payload = objectMapper.writeValueAsString(getPage());

        server.expect(method(HttpMethod.GET))
                .andExpect(requestTo(URL + BeerClientImpl.BEER_API_URL))
                .andRespond(withSuccess(payload, MediaType.APPLICATION_JSON));

        Page<BeerDTO> dtos = beerClient.listBeers();
        assertThat(dtos.getContent()).hasSize(1);
    }

    BeerDTO getBeerDto(){
        return BeerDTO.builder()
                .id(UUID.randomUUID())
                .price(new BigDecimal("10.99"))
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(500)
                .upc("123245")
                .build();
    }

    BeerDTOPageImpl getPage(){
        return new BeerDTOPageImpl(List.of(getBeerDto()), 1, 25, 1);
    }
}

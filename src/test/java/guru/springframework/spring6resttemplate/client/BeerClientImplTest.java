package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Modified by Pierrot on 04-06-2024.
 */
@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClient beerClient;

    @Test
    void listBeersNoBeerName() {

        beerClient.listBeers();
    }

    @Test
    void listBeers() {
        beerClient.listBeers("ALE", BeerStyle.ALE, Boolean.FALSE, 0, 25);
    }

    @Test
    void getBeerByID() {
        BeerDTO beerDTO = beerClient.listBeers().getContent().getFirst();

        BeerDTO beerById = beerClient.getBeerById(beerDTO.getId());

        assertThat(beerById).isNotNull();
    }
}
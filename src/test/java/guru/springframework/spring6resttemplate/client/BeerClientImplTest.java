package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Modified by Pierrot on 04-06-2024.
 */
@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClient beerClient;

    @Test
    void testCreateBeer() {

        BeerDTO newDto = BeerDTO.builder()
                .price(new BigDecimal("10.99"))
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(500)
                .upc("123245")
                .build();

        BeerDTO savedDto = beerClient.createBeer(newDto);
        assertNotNull(savedDto);
    }

    @Test
    void getBeerByID() {

        BeerDTO beerDTO = beerClient.listBeers().getContent().getFirst();

        BeerDTO beerById = beerClient.getBeerById(beerDTO.getId());

        assertThat(beerById).isNotNull();
    }

    @Test
    void listBeersNoBeerName() {

        beerClient.listBeers();
    }

    @Test
    void listBeers() {

        beerClient.listBeers("ALE", BeerStyle.ALE, Boolean.FALSE, 0, 25);
    }
}
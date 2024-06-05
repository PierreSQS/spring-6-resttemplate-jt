package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Modified by Pierrot on 06-06-2024.
 */
@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClient beerClient;

    @Test
    void testDeleteBeer() {

        BeerDTO newDto = BeerDTO.builder()
                .price(new BigDecimal("10.99"))
                .beerName("Beer to delete")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(500)
                .upc("123245")
                .build();

        BeerDTO beerDto = beerClient.createBeer(newDto);

        UUID beerDtoId = beerDto.getId();
        beerClient.deleteBeer(beerDtoId);

        assertThatExceptionOfType(HttpClientErrorException.class)
                .isThrownBy(() -> beerClient.deleteBeer(beerDtoId))
                .withMessageContaining("Not Found");
    }

    @Test
    void testUpdateBeer() {

        BeerDTO newDto = BeerDTO.builder()
                .price(new BigDecimal("10.99"))
                .beerName("Mango Bobs 2")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(500)
                .upc("123245")
                .build();

        BeerDTO beerDto = beerClient.createBeer(newDto);

        final String newName = "Mango Bobs 3";
        beerDto.setBeerName(newName);
        BeerDTO updatedBeer = beerClient.updateBeer(beerDto);

        assertEquals(newName, updatedBeer.getBeerName());
    }

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
        assertThat(savedDto).isNotNull();
    }

    @Test
    void getBeerByID() {

        BeerDTO beerDTO = beerClient.listBeers().getContent().getFirst();

        BeerDTO beerById = beerClient.getBeerById(beerDTO.getId());

        assertThat(beerById).isNotNull();
    }

    @Test
    void listBeersNoBeerName() {

        Page<BeerDTO> beerDTOS = beerClient.listBeers();

        assertThat(beerDTOS.getContent()).hasSize(25);
    }

    @Test
    void listBeers() {

        Page<BeerDTO> beerDTOS =
                beerClient.listBeers("ALE", BeerStyle.ALE, Boolean.FALSE, 0, 25);

        assertThat(beerDTOS.getContent()).hasSize(25);
    }
}
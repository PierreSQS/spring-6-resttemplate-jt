package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClient beerClient;

    @Test
    void listBeers() {

        Page<BeerDTO> beerDTOS = beerClient.listBeers(null);

        assertThat(beerDTOS.getContent()).hasSize(25);
    }

    @Test
    void listBeersByBeerName() {

        String blacklager="IPA";

        Page<BeerDTO> beerDTOS = beerClient.listBeers(blacklager);

        assertThat(beerDTOS.getContent()).hasSize(25);
    }
}
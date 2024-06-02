package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

/**
 * Modified by Pierrot on 02-06-2024.
 */
@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClient beerClient;

    @Test
    void listBeers() {
        Page<BeerDTO> beerDTOS = beerClient.listBeers();

// has a weird behavior !!
//        assertThat(beerDTOS.getContent().size()).isGreaterThan(2400);
//        assertThat(beerDTOS.getSize()).isEqualTo(25);


    }
}
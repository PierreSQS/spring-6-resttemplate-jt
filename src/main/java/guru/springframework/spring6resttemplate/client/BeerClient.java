package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerStyle;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * Modified by Pierrot, 12.02.2023.
 */
public interface BeerClient {

    Page<BeerDTO> listBeers();

    Page<BeerDTO> listBeers(String beerName,
                            BeerStyle beerStyle,
                            Boolean showInventory,
                            Integer pageNumber,
                            Integer pageSize);

    BeerDTO getBeerByID(UUID beerUUID);
}

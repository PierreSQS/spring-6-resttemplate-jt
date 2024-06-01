package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import org.springframework.data.domain.Page;

/**
 * Created by Pierrot on 01-06-2024.
 */
public interface BeerClient {
    Page<BeerDTO> listBeers();
}

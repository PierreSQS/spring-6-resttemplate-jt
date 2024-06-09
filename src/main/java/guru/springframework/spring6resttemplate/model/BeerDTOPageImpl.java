package guru.springframework.spring6resttemplate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Modified by Pierrot on 09-06-2024.
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = "pageable")
public class BeerDTOPageImpl extends PageImpl<guru.springframework.spring6resttemplate.model.BeerDTO> {
    public BeerDTOPageImpl(List<guru.springframework.spring6resttemplate.model.BeerDTO> content) {
        super(content);
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BeerDTOPageImpl(@JsonProperty("content") List<guru.springframework.spring6resttemplate.model.BeerDTO> content,
                           @JsonProperty("number") int page,
                           @JsonProperty("size") int size,
                           @JsonProperty("totalElements") long total) {
        super(content, PageRequest.of(page, 25), 25);
    }

    public BeerDTOPageImpl(List<guru.springframework.spring6resttemplate.model.BeerDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}

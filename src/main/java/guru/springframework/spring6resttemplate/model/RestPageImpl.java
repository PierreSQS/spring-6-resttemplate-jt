package guru.springframework.spring6resttemplate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@JsonIgnoreProperties("pageable")
public class RestPageImpl<BeerDTO> extends PageImpl<BeerDTO> {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RestPageImpl(@JsonProperty("content") List<BeerDTO> content,
                        @JsonProperty("page") int page,
                        @JsonProperty("size") int size,
                        @JsonProperty("totalElements") int totalElements ) {
        super(content, PageRequest.of(page,size),totalElements);

    }

    public RestPageImpl(List<BeerDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public RestPageImpl(List<BeerDTO> content) {
        super(content);
    }
}

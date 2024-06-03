package guru.springframework.spring6resttemplate.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BeerDTOPageImpl<T> extends PageImpl<T> {
    public BeerDTOPageImpl(List<T> content) {
        super(content);
    }

    public BeerDTOPageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}

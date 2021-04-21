package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookSpecificationBuilder {

    private final List<SearchCriteria> params;


    public BookSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public BookSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Book> build() {
        if (params.size() == 0) {
            return null;
        }

        List<BookSpecification> specs = params.stream()
                    .map(BookSpecification::new)
                    .collect(Collectors.toList());

        Specification<Book> result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}

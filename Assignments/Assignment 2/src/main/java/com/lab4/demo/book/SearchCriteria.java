package com.lab4.demo.book;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class SearchCriteria {

    private String key;
    private String operation;
    private Object value;
}

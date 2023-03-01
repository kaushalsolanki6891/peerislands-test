package com.peerislands.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchFilter {
    private String operator;
    private String fieldName;
    private Object fieldValue;


}
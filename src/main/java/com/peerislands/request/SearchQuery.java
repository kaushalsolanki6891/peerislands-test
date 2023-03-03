package com.peerislands.request;

import lombok.Data;

import java.util.List;

@Data
public class SearchQuery {
    private String parentOperator = "OR";
    private List<SearchFilter> searchFilter;
    private List<JoinColumnProps> joinColumnProps;
    private String childOperator;

}
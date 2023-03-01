package com.peerislands.request;

import lombok.Data;

import java.util.List;

@Data
public class SearchQuery {
    private List<SearchFilter> searchFitler;

    private int pageNumber;
    private int pageSize;

    private SortOrder sortOrder;

    private List<JoinColumnProps> joinColumnProps;

    public List<SearchFilter> getSearchFitler() {
        return searchFitler;
    }


}
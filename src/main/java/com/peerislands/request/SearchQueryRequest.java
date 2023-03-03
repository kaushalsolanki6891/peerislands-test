package com.peerislands.request;

import lombok.Data;

import java.util.LinkedList;

@Data
public class SearchQueryRequest {
    private LinkedList<SearchQuery> searchQueries;

    private int pageNumber = 0;
    private int pageSize = 10;
    private SortOrder sortOrder;

}

package com.peerislands.request;

import lombok.Data;

import java.util.List;

@Data
public class SortOrder {

    private List<String> ascendingOrder;

    private List<String> descendingOrder;

}
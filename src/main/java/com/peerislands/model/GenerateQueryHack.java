package com.peerislands.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Data
@AllArgsConstructor
public class GenerateQueryHack {

    private Specification spec;
    private Pageable pageable;
}

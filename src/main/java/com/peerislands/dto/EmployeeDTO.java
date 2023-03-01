package com.peerislands.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class EmployeeDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Integer age;

    private Set<HobbyDTO> hobbies;

}
package com.peerislands.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HobbyDTO {

    private int id;

    private String hobby;

    private EmployeeDTO employeeDTO;

}
package com.peerislands.mapper;

import com.peerislands.dto.EmployeeDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    com.peerislands.model.Employee toEntity(EmployeeDTO dto);

    EmployeeDTO toDto(com.peerislands.model.Employee entity);

    List<com.peerislands.model.Employee> toEntities(List<EmployeeDTO> dtoList);

    List<EmployeeDTO> toDtos(List<com.peerislands.model.Employee> entities);

}

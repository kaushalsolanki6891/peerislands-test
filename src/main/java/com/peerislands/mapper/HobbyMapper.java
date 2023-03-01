package com.peerislands.mapper;

import com.peerislands.dto.HobbyDTO;
import com.peerislands.model.Hobby;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface HobbyMapper {

    com.peerislands.model.Hobby toEntity(HobbyDTO dto);

    HobbyDTO toDto(com.peerislands.model.Hobby entity);

    List<Hobby> toEntities(List<HobbyDTO> dtoList);

    List<HobbyDTO> toDtos(List<Hobby> entities);

  /*  Set<Hobby> toEntitiesSet(Set<HobbyDTO> dtoList);

    Set<HobbyDTO> toDtosSet(Set<Hobby> entities);*/
}

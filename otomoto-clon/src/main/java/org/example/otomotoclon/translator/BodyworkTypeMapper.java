package org.example.otomotoclon.translator;

import org.example.otomotoclon.dto.BodyworkTypeDTO;
import org.example.otomotoclon.entity.BodyworkType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BodyworkTypeMapper {

    @Mapping(source = "name", target = "name")
    BodyworkTypeDTO toDto(BodyworkType bodyworkType);

    @Mapping(source = "name", target = "name")
    BodyworkType toEntity(BodyworkTypeDTO bodyworkTypeDTO);
}

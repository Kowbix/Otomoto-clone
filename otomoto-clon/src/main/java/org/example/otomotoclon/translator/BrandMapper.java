package org.example.otomotoclon.translator;

import org.example.otomotoclon.dto.BrandDTO;
import org.example.otomotoclon.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface BrandMapper {

    @Mapping(source = "name", target = "name")
    BrandDTO toDto(Brand brand);

    @Mapping(source = "name", target = "name")
    Brand toEntity(BrandDTO brandDTO);
}

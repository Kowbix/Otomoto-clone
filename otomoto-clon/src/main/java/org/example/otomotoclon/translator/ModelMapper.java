package org.example.otomotoclon.translator;

import org.example.otomotoclon.dto.ModelDTO;
import org.example.otomotoclon.entity.Model;
import org.example.otomotoclon.repository.BrandRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface ModelMapper {

    @Mappings({
            @Mapping(source = "name", target = "modelName"),
            @Mapping(source = "brand.name", target = "brandName")
    })
    ModelDTO toDTO(Model model);

}

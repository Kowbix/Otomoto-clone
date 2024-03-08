package org.example.otomotoclon.translator;

import org.example.otomotoclon.dto.GenerationDTO;
import org.example.otomotoclon.entity.Generation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface GenerationMapper {

    @Mappings({
            @Mapping(source = "name", target = "generationName"),
            @Mapping(source = "model.name", target = "modelName"),
            @Mapping(source = "startProductionYear", target = "startProductionYear"),
            @Mapping(source = "endProductionYear", target = "endProductionYear")
    })
    GenerationDTO toDTO(Generation generation);
}

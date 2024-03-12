package org.example.otomotoclon.translator;

import org.example.otomotoclon.dto.EngineDTO;
import org.example.otomotoclon.entity.Engine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface EngineMapper {

    @Mappings({
            @Mapping(source = "capacity", target = "capacity"),
            @Mapping(source = "horsepower", target = "horsepower")
    })
    EngineDTO toDTO(Engine engine);

    @Mappings({
            @Mapping(source = "capacity", target = "capacity"),
            @Mapping(source = "horsepower", target = "horsepower")
    })
    Engine toEntity(EngineDTO engineDTO);
}

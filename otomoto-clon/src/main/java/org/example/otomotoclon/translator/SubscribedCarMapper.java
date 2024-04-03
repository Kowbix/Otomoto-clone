package org.example.otomotoclon.translator;

import org.example.otomotoclon.dto.SubscribedCarDTO;
import org.example.otomotoclon.dto.SubscribedCarDTOExtended;
import org.example.otomotoclon.entity.SubscribedCar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface SubscribedCarMapper {

    @Mappings({
            @Mapping(target = "brand", source = "brand.name"),
            @Mapping(target = "model", source = "model.name"),
            @Mapping(target = "generation", source = "generation.name")
    })
    SubscribedCarDTO toDTO(SubscribedCar subscribedCar);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "brand", source = "brand.name"),
            @Mapping(target = "model", source = "model.name"),
            @Mapping(target = "generation", source = "generation.name")
    })
    SubscribedCarDTOExtended toDTOExtended(SubscribedCar subscribedCar);
}

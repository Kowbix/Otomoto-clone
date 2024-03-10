package org.example.otomotoclon.translator;

import org.example.otomotoclon.dto.FuelDTO;
import org.example.otomotoclon.entity.Fuel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface FuelMapper {

    @Mapping(source = "name", target = "name")
    FuelDTO toDto(Fuel fuel);

    @Mapping(source = "name", target = "name")
    Fuel toEntity(FuelDTO fuelDTO);
}

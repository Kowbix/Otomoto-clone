package org.example.otomotoclon.translator;

import org.example.otomotoclon.dto.LocationDTO;
import org.example.otomotoclon.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface LocationMapper {

    @Mappings({
        @Mapping(source = "cityName", target = "cityName"),
        @Mapping(source = "voivodeship.name", target = "voivodeshipName")
    })
    LocationDTO toDto(Location location);
}

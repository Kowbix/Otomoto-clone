package org.example.otomotoclon.translator;

import org.example.otomotoclon.dto.announcement.AnnouncementDTO;
import org.example.otomotoclon.dto.car.CarDTO;
import org.example.otomotoclon.entity.Announcement;
import org.example.otomotoclon.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface AnnouncementToDTOMapper {


    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(expression = "java(toCarDTO(announcement.getCar()))", target = "car"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "mainImageUrl", source = "announcement.mainImage.url")
    })
    AnnouncementDTO toDTO(Announcement announcement);

    @Mappings({
            @Mapping(target = "brand", source = "car.brand.name"),
            @Mapping(target = "model", source = "car.model.name"),
            @Mapping(target = "generation", source = "car.generation.name"),
            @Mapping(target = "yearProduction", source = "yearProduction"),
            @Mapping(target = "fuel", source = "car.fuel.name"),
            @Mapping(target = "millage", source = "millage"),
            @Mapping(target = "horsepower", source = "car.engine.horsepower"),
            @Mapping(target = "capacity", source = "car.engine.capacity"),
            @Mapping(target = "transmission", source = "car.transmission.name")
    })
    CarDTO toCarDTO(Car car);
}

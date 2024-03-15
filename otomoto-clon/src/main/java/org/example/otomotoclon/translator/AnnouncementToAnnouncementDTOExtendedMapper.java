package org.example.otomotoclon.translator;

import org.example.otomotoclon.dto.EngineDTO;
import org.example.otomotoclon.dto.LocationDTO;
import org.example.otomotoclon.dto.announcement.AnnouncementDTOExtended;
import org.example.otomotoclon.dto.car.CarDTOExtended;
import org.example.otomotoclon.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface AnnouncementToAnnouncementDTOExtendedMapper {


    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(expression = "java(toCarDTOExtended(announcement.getCar()))", target = "car"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "mainImageUrl", source = "announcement.mainImage.url"),
            @Mapping(target = "descriptionUrl", source = "descriptionUrl"),
            @Mapping(target = "addedDate", source = "addedDate"),
            @Mapping(target = "views", source = "views"),
            @Mapping(expression = "java(toLocationDTO(announcement.getLocation()))", target = "locationDTO"),
    })
    AnnouncementDTOExtended toAnnouncementDTOExtended(Announcement announcement);

    @Mappings({
            @Mapping(target = "brand", source = "car.brand.name"),
            @Mapping(target = "model", source = "car.model.name"),
            @Mapping(target = "generation", source = "car.generation.name"),
            @Mapping(target = "yearProduction", source = "yearProduction"),
            @Mapping(target = "bodyworkType", source = "car.bodyworkType.name"),
            @Mapping(target = "fuel", source = "car.fuel.name"),
            @Mapping(target = "millage", source = "millage"),
            @Mapping(target = "damageCondition", source = "car.damageCondition.name"),
            @Mapping(target = "transmission", source = "car.transmission.name"),
            @Mapping(target = "vin", source = "vin"),
            @Mapping(target = "imageUrls", source = "car.images", qualifiedByName = "mapImages"),
            @Mapping(expression = "java(engineToEngineDTO(car.getEngine()))", target = "engineDTO"),
            @Mapping(target = "doorCount", source = "doorCount"),
            @Mapping(target = "seatCount", source = "seatCount")
    })
    CarDTOExtended toCarDTOExtended(Car car);

    @Mappings({
            @Mapping(source = "capacity", target = "capacity"),
            @Mapping(source = "horsepower", target = "horsepower")
    })
    EngineDTO engineToEngineDTO(Engine engine);

    @Named("mapImages")
    default List<String> mapImages(List<Image> images) {
        return images.stream()
                .map(Image::getUrl)
                .collect(Collectors.toList());
    }

    @Mappings({
            @Mapping(source = "cityName", target = "cityName"),
            @Mapping(source = "voivodeship.name", target = "voivodeshipName")
    })
    LocationDTO toLocationDTO(Location location);
}

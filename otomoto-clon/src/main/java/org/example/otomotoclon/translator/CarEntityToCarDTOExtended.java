package org.example.otomotoclon.translator;

import org.example.otomotoclon.dto.EngineDTO;
import org.example.otomotoclon.dto.car.CarDTOExtended;
import org.example.otomotoclon.entity.Car;
import org.example.otomotoclon.entity.Engine;
import org.example.otomotoclon.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface CarEntityToCarDTOExtended {

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
            @Mapping(expression = "java(EngineToEngineDTO(car.getEngine()))", target = "engineDTO"),
            @Mapping(target = "doorCount", source = "doorCount"),
            @Mapping(target = "seatCount", source = "seatCount")
    })
    CarDTOExtended toCarDTOExtended(Car car);

    @Mappings({
            @Mapping(source = "capacity", target = "capacity"),
            @Mapping(source = "horsepower", target = "horsepower")
    })
    EngineDTO EngineToEngineDTO(Engine engine);

    @Named("mapImages")
    default List<String> mapImages(List<Image> images) {
        return images.stream()
                .map(Image::getUrl)
                .collect(Collectors.toList());
    }
}

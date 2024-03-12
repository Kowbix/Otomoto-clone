package org.example.otomotoclon.dto.car;

import lombok.Getter;
import lombok.Setter;
import org.example.otomotoclon.dto.EngineDTO;

import java.util.List;

@Getter
@Setter
public class CarDTOExtended {

    private String brand;
    private String model;
    private String generation;
    private int yearProduction;
    private String bodyworkType;
    private String fuel;
    private int millage;
    private String damageCondition;
    private String vin;
    private List<String> imageUrls;
    private EngineDTO engineDTO;
    private String transmission;
    private int doorCount;
    private int seatCount;
}

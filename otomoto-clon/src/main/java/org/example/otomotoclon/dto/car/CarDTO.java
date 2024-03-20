package org.example.otomotoclon.dto.car;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarDTO {

    private String brand;
    private String model;
    private String generation;
    private int yearProduction;
    private String fuel;
    private int millage;
    private int horsepower;
    private int capacity;
    private String transmission;
}

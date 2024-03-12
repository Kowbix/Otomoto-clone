package org.example.otomotoclon.dto.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private int engineCapacity;
    private String transmission;
}

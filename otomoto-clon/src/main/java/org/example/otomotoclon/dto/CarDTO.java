package org.example.otomotoclon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {

    private long id;
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

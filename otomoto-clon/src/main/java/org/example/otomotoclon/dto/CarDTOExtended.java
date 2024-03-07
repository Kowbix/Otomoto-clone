package org.example.otomotoclon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDTOExtended {

    private long id;
    private String brand;
    private String model;
    private String generation;
    private int yearProduction;
    private String bodyworkType;
    private String fuel;
    private int millage;
    private String vin;
    private int horsepower;
    private int engineCapacity;
    private String transmission;
    private int doorCount;
    private int seatCount;
    Set<String> imageUrls;
}

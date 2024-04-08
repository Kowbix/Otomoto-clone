package org.example.otomotoclon.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscribedCarDTOExtended {

    private long id;
    private String brand;
    private String model;
    private String generation;
    private String userEmail;
}

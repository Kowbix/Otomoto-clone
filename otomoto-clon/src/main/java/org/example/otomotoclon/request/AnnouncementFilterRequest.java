package org.example.otomotoclon.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnnouncementFilterRequest {

    private String brand;
    private String model;
    private String generation;
    private Float priceMin;
    private Float priceMax;
    private Integer yearProductionMin;
    private Integer yearProductionMax;
    private String bodyworkType;
    private String fuel;
    private Integer millageMin;
    private Integer millageMax;
    private boolean isVin;
    private Integer capacityMin;
    private Integer capacityMax;
    private Integer horsepowerMin;
    private Integer horsepowerMax;
    private Integer doorsMin;
    private Integer doorsMax;
    private Integer seatsMin;
    private Integer seatsMax;
    private String transmission;
    private int page;
    private int limit;
    private String sort;
    private String order;

    public AnnouncementFilterRequest() {
        this.page = 1;
        this.limit = 10;
        this.sort = "date";
        this.order = "asc";
    }
}

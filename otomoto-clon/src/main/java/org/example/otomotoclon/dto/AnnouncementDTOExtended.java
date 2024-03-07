package org.example.otomotoclon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDTOExtended {

    private long id;
    private CarDTO car;
    private float price;
    private String mainImageUrl;
    private String descriptionUrl;
    private Date addedDate;
    private long views;
    private String voivodeship;
    private String cityName;
}

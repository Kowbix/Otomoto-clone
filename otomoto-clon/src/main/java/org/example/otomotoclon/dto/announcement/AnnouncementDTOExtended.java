package org.example.otomotoclon.dto.announcement;

import lombok.Getter;
import lombok.Setter;
import org.example.otomotoclon.dto.LocationDTO;
import org.example.otomotoclon.dto.car.CarDTOExtended;

import java.util.Date;

@Getter
@Setter
public class AnnouncementDTOExtended {

    private long id;
    private CarDTOExtended car;
    private float price;
    private String mainImageUrl;
    private String descriptionUrl;
    private Date addedDate;
    private long views;
    private LocationDTO locationDTO;
}

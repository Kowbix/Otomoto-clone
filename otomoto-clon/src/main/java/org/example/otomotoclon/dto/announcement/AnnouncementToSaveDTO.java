package org.example.otomotoclon.dto.announcement;

import lombok.Getter;
import lombok.Setter;
import org.example.otomotoclon.dto.LocationDTO;
import org.example.otomotoclon.dto.car.CarToSaveDTO;

@Getter
@Setter
public class AnnouncementToSaveDTO {

    private CarToSaveDTO carToSaveDTO;
    private String description;
    private float price;
    private LocationDTO locationDTO;
}

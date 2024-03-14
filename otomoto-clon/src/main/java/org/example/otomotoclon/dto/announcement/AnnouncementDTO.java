package org.example.otomotoclon.dto.announcement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.otomotoclon.dto.car.CarDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDTO {

    private long id;
    private CarDTO car;
    private float price;
    private String mainImageUrl;
}

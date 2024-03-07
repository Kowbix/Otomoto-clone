package org.example.otomotoclon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

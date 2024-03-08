package org.example.otomotoclon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerationDTO {

    private String generationName;
    private String modelName;
    private int startProductionYear;
    private int endProductionYear;
}

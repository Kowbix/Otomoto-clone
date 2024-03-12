package org.example.otomotoclon.serivce;

import org.example.otomotoclon.dto.GenerationDTO;
import org.example.otomotoclon.entity.Generation;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface GenerationService {

    void create(GenerationDTO generationDTO);
    ResponseEntity<List<GenerationDTO>> getGenerationsByModelName(String model);
    Generation getGenerationByNameAndModelName(String generationName, String modelName);

}

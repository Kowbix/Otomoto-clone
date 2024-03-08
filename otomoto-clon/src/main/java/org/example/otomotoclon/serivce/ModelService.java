package org.example.otomotoclon.serivce;

import org.example.otomotoclon.dto.ModelDTO;
import org.example.otomotoclon.entity.Model;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ModelService {

    void create(ModelDTO modelDto);
    ResponseEntity<List<ModelDTO>> getModelsByBrand(String brand);
    Model getModelByName(String name);
}

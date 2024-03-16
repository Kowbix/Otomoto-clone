package org.example.otomotoclon.serivce;

import org.example.otomotoclon.dto.ModelDTO;
import org.example.otomotoclon.entity.Model;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ModelService {

    void create(ModelDTO modelDto) throws ObjectExistInDBException, ObjectDontExistInDBException;
    ResponseEntity<List<ModelDTO>> getModelsByBrand(String brand);
    Model getModelByNameAndBrand(String modelName, String brandName) throws ObjectDontExistInDBException;
    Model getModelByName(String name);
}

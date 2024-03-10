package org.example.otomotoclon.serivce;

import org.example.otomotoclon.dto.FuelDTO;
import org.example.otomotoclon.entity.Fuel;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FuelService {

    void create(FuelDTO fuelDTO) throws ObjectExistInDBException;
    ResponseEntity<List<FuelDTO>> getAllFuels();
    Fuel getFuelByName(String name) throws ObjectDontExistInDBException;

}

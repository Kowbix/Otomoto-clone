package org.example.otomotoclon.serivce;

import org.example.otomotoclon.dto.EngineDTO;
import org.example.otomotoclon.entity.Engine;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.exception.ObjectExistInDBException;

import java.util.Optional;

public interface EngineService {

    Engine getOrCreateEngineForCar(EngineDTO engineDTO);
}

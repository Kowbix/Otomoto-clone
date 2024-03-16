package org.example.otomotoclon.serivce;

import org.example.otomotoclon.dto.EngineDTO;
import org.example.otomotoclon.entity.Engine;

public interface EngineService {

    Engine getOrCreateEngineForCar(EngineDTO engineDTO);

    boolean compareEngineToEngineDTO(Engine engine, EngineDTO engineDTO);
}

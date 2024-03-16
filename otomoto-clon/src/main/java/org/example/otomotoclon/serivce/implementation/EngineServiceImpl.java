package org.example.otomotoclon.serivce.implementation;

import org.example.otomotoclon.dto.EngineDTO;
import org.example.otomotoclon.entity.Engine;
import org.example.otomotoclon.repository.EngineRepository;
import org.example.otomotoclon.serivce.EngineService;
import org.example.otomotoclon.translator.EngineMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EngineServiceImpl implements EngineService {

    private final EngineRepository engineRepository;
    private final EngineMapper engineMapper;

    public EngineServiceImpl(EngineRepository engineRepository,
                             EngineMapper engineMapper) {
        this.engineRepository = engineRepository;
        this.engineMapper = engineMapper;
    }

    @Override
    public Engine getOrCreateEngineForCar(EngineDTO engineDTO) {
         Optional<Engine> existingEngineOptional = engineRepository.findEngineByCapacityAndHorsepower(
                 engineDTO.getCapacity(),
                 engineDTO.getHorsepower()
         );

         if(existingEngineOptional.isPresent()) {
             return existingEngineOptional.get();
         }
        return create(engineDTO);
    }

    @Override
    public boolean compareEngineToEngineDTO(Engine engine, EngineDTO engineDTO) {
        if (engine == null || engineDTO == null) {
            return false;
        }
        return engine.getCapacity() == engineDTO.getCapacity() &&
                engine.getHorsepower() == engineDTO.getHorsepower();
    }

    private Engine create(EngineDTO engineDTO) {
        Engine engine = new Engine();
        engine = engineMapper.toEntity(engineDTO);
        return engineRepository.save(engine);
    }
}

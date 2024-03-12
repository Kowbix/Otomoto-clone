package org.example.otomotoclon.serivce.implementation;

import org.example.otomotoclon.dto.GenerationDTO;
import org.example.otomotoclon.entity.Generation;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.example.otomotoclon.repository.GenerationRepository;
import org.example.otomotoclon.repository.ModelRepository;
import org.example.otomotoclon.serivce.GenerationService;
import org.example.otomotoclon.serivce.ModelService;
import org.example.otomotoclon.translator.GenerationMapper;
import org.example.otomotoclon.translator.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenerationServiceImpl implements GenerationService {

    private final GenerationRepository generationRepository;
    private final ModelService modelService;
    private final GenerationMapper generationMapper;

    public GenerationServiceImpl(GenerationRepository generationRepository,
                                 ModelService modelService,
                                 GenerationMapper generationMapper) {
        this.generationRepository = generationRepository;
        this.modelService = modelService;
        this.generationMapper = generationMapper;
    }

    @Override
    @Transactional
    public void create(GenerationDTO generationDTO) throws ObjectExistInDBException, ObjectDontExistInDBException {
        generationRepository.findGenerationByNameAndModelName(generationDTO.getGenerationName(),
                generationDTO.getModelName()).ifPresent(value -> {
            throw new ObjectExistInDBException("Generation exists in database with given name");
        });
        Generation generation = new Generation();
        generation.setName(generationDTO.getGenerationName());
        generation.setModel(modelService.getModelByName(generationDTO.getModelName()));
        generation.setStartProductionYear(generationDTO.getStartProductionYear());
        generation.setEndProductionYear(generationDTO.getEndProductionYear());
        generationRepository.save(generation);
    }

    @Override
    public ResponseEntity<List<GenerationDTO>> getGenerationsByModelName(String modelName) {
        List<Generation> generations = generationRepository.findGenerationsByModelName(modelName);
        return ResponseEntity.ok(
                generations.stream()
                        .map(generation -> generationMapper.toDTO(generation))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Generation getGenerationByNameAndModelName(String generationName, String modelName)  throws ObjectDontExistInDBException {
        return generationRepository.findGenerationByNameAndModelName(generationName, modelName)
                .orElseThrow(() ->
                        new ObjectDontExistInDBException("Generation does not exists with generation name: " + generationName + " and model name: " + modelName));
    }
}

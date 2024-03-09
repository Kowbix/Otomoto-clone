package org.example.otomotoclon.serivce.implementation;

import org.example.otomotoclon.dto.BodyworkTypeDTO;
import org.example.otomotoclon.entity.BodyworkType;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.example.otomotoclon.repository.BodyworkTypeRepository;
import org.example.otomotoclon.serivce.BodyworkTypeService;
import org.example.otomotoclon.translator.BodyworkTypeMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BodyworkTypeServiceImpl implements BodyworkTypeService {


    private final BodyworkTypeRepository bodyworkTypeRepository;
    private final BodyworkTypeMapper bodyworkTypeMapper;

    public BodyworkTypeServiceImpl(BodyworkTypeRepository bodyworkTypeRepository,
                                   BodyworkTypeMapper bodyworkTypeMapper) {
        this.bodyworkTypeRepository = bodyworkTypeRepository;
        this.bodyworkTypeMapper = bodyworkTypeMapper;
    }

    @Override
    public void create(BodyworkTypeDTO bodyworkTypeDTO) throws ObjectExistInDBException {
        bodyworkTypeRepository.findBodyworkTypeByName(bodyworkTypeDTO.getName()).ifPresent(value -> {
            throw new ObjectExistInDBException("Bodywork type exists in database with given name");
        });
        BodyworkType bodyworkType = bodyworkTypeMapper.toEntity(bodyworkTypeDTO);
        bodyworkTypeRepository.save(bodyworkType);
    }

    @Override
    public BodyworkType getBodyworkTypeByName(String name) throws ObjectDontExistInDBException {
        return bodyworkTypeRepository.findBodyworkTypeByName(name)
                .orElseThrow(() -> new ObjectDontExistInDBException("Bodywork type does not exist in the database"));
    }

    @Override
    public ResponseEntity<List<BodyworkTypeDTO>> getAllBodyworkTypes() {
        List<BodyworkType> bodyworkTypes = bodyworkTypeRepository.findAll();
        return ResponseEntity.ok(
                bodyworkTypes.stream()
                    .map(bodyworkType -> bodyworkTypeMapper.toDto(bodyworkType))
                    .collect(Collectors.toList()));
    }
}

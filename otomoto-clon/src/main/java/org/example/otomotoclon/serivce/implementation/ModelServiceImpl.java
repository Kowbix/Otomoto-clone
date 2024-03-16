package org.example.otomotoclon.serivce.implementation;

import org.example.otomotoclon.dto.ModelDTO;
import org.example.otomotoclon.entity.Brand;
import org.example.otomotoclon.entity.Model;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.example.otomotoclon.repository.ModelRepository;
import org.example.otomotoclon.serivce.BrandService;
import org.example.otomotoclon.serivce.ModelService;
import org.example.otomotoclon.translator.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;
    private final BrandService brandService;
    private final ModelMapper modelMapper;

    public ModelServiceImpl(ModelRepository modelRepository, BrandService brandService, ModelMapper modelMapper) {
        this.modelRepository = modelRepository;
        this.brandService = brandService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void create(ModelDTO modelDto) throws ObjectExistInDBException, ObjectDontExistInDBException {
        modelRepository.findModelByNameAndBrandName(modelDto.getModelName(), modelDto.getBrandName()).ifPresent(value -> {
            throw new ObjectExistInDBException("Model exists in database with given name");
        });
        Model model = new Model();
        model.setName(modelDto.getModelName());
        model.setBrand(brandService.getBrandByName(modelDto.getBrandName()));
        modelRepository.save(model);
    }

    @Override
    public ResponseEntity<List<ModelDTO>> getModelsByBrand(String brandName) {
        List<Model> models = modelRepository.findModelsByBrandName(brandName);
        return ResponseEntity.ok(
                models.stream()
                        .map(model -> modelMapper.toDTO(model))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Model getModelByNameAndBrand(String name, String brandName) throws ObjectDontExistInDBException {
        return modelRepository.findModelByNameAndBrandName(name, brandName)
                .orElseThrow(() -> new ObjectDontExistInDBException("Model " + name + " does not exist with brand " + brandName + " in the database"));
    }

    @Override
    public Model getModelByName(String name) throws ObjectDontExistInDBException {
        return modelRepository.findModelByName(name)
                .orElseThrow(() -> new ObjectDontExistInDBException("Model " + name + " does not exist in the database"));
    }
}

package org.example.otomotoclon.serivce.implementation;

import org.example.otomotoclon.dto.car.CarToSaveDTO;
import org.example.otomotoclon.entity.Car;
import org.example.otomotoclon.exception.InvalidFileExtension;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.repository.CarRepository;
import org.example.otomotoclon.serivce.*;
import org.example.otomotoclon.translator.CarEntityToCarDTOExtended;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final BrandService brandService;
    private final ModelService modelService;
    private final GenerationService generationService;
    private final BodyworkTypeService bodyworkTypeService;

    private final FuelService fuelService;
    private final DamageConditionService damageConditionService;
    private final ImageService imageService;
    private final EngineService engineService;
    private final TransmissionService transmissionService;
    private final CarEntityToCarDTOExtended toCarDTOExtendedMapper;

    public CarServiceImpl(CarRepository carRepository,
                          BrandService brandService,
                          ModelService modelService,
                          GenerationService generationService,
                          BodyworkTypeService bodyworkTypeService,
                          FuelService fuelService,
                          DamageConditionService damageConditionService,
                          ImageService imageService,
                          EngineService engineService,
                          TransmissionService transmissionService,
                          CarEntityToCarDTOExtended toCarDTOExtendedMapper) {
        this.carRepository = carRepository;
        this.brandService = brandService;
        this.modelService = modelService;
        this.generationService = generationService;
        this.bodyworkTypeService = bodyworkTypeService;
        this.fuelService = fuelService;
        this.damageConditionService = damageConditionService;
        this.imageService = imageService;
        this.engineService = engineService;
        this.transmissionService = transmissionService;
        this.toCarDTOExtendedMapper = toCarDTOExtendedMapper;
    }

    @Override
    public Car createCar(CarToSaveDTO carToSaveDTO, List<MultipartFile> images) throws ObjectDontExistInDBException, InvalidFileExtension {
        Car car = mapCarToSaveToCarEntity(carToSaveDTO, images);
        return carRepository.save(car);
    }

    @Override
    public void deleteCar(long carId) throws ObjectDontExistInDBException {
        Car carToDelete = carRepository.findById(carId).orElseThrow(() ->
                 new ObjectDontExistInDBException("Car with id: " + carId + " dose not exists in database"));
        imageService.deleteImages(carToDelete.getImages());
        carRepository.delete(carToDelete);
    }

    private Car mapCarToSaveToCarEntity(CarToSaveDTO carToSaveDTO, List<MultipartFile> images) throws ObjectDontExistInDBException {
        Car car = new Car();
        car.setBrand(brandService.getBrandByName(carToSaveDTO.getBrand()));
        car.setModel(modelService.getModelByName(carToSaveDTO.getModel()));
        if (!carToSaveDTO.getGeneration().isEmpty()) {
            car.setGeneration(generationService.getGenerationByNameAndModelName(
                    carToSaveDTO.getGeneration(),
                    carToSaveDTO.getModel()));
        }
        car.setYearProduction(carToSaveDTO.getYearProduction());
        car.setBodyworkType(bodyworkTypeService.getBodyworkTypeByName(carToSaveDTO.getBodyworkType()));
        car.setFuel(fuelService.getFuelByName(carToSaveDTO.getFuel()));
        car.setMillage(carToSaveDTO.getMillage());
        car.setDamageCondition(damageConditionService.getDamageConditionByName(carToSaveDTO.getDamageCondition()));
        car.setVin(carToSaveDTO.getVin());
        car.setImages(imageService.uploadImages(images));
        car.setEngine(engineService.getOrCreateEngineForCar(carToSaveDTO.getEngineDTO()));
        car.setTransmission(transmissionService.getTransmissionByName(carToSaveDTO.getTransmission()));
        car.setDoorCount(carToSaveDTO.getDoorCount());
        car.setSeatCount(car.getSeatCount());
        return car;
    }

}

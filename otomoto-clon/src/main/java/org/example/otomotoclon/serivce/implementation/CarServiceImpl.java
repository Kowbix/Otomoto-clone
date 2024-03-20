package org.example.otomotoclon.serivce.implementation;

import org.example.otomotoclon.dto.car.CarDTOExtended;
import org.example.otomotoclon.dto.car.CarToSaveDTO;
import org.example.otomotoclon.entity.Car;
import org.example.otomotoclon.entity.Engine;
import org.example.otomotoclon.entity.Image;
import org.example.otomotoclon.exception.InvalidFileExtension;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.repository.CarRepository;
import org.example.otomotoclon.serivce.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public CarServiceImpl(CarRepository carRepository,
                          BrandService brandService,
                          ModelService modelService,
                          GenerationService generationService,
                          BodyworkTypeService bodyworkTypeService,
                          FuelService fuelService,
                          DamageConditionService damageConditionService,
                          ImageService imageService,
                          EngineService engineService,
                          TransmissionService transmissionService) {
        this.carRepository = carRepository;
        this.carMapper = new CarMapper(brandService, modelService, generationService,
                bodyworkTypeService, fuelService, damageConditionService, imageService,
                engineService, transmissionService);
    }

    @Override
    public Car createCar(CarToSaveDTO carToSaveDTO, List<MultipartFile> images) throws ObjectDontExistInDBException, InvalidFileExtension {
        Car car = carMapper.mapCarToSaveToCarEntity(carToSaveDTO, images);
        return carRepository.save(car);
    }

    @Override
    public void deleteCar(Car carToDelete) throws ObjectDontExistInDBException {
        carMapper.imageService.deleteImages(carToDelete.getImages());
        carRepository.delete(carToDelete);
    }

    @Override
    public Car updateCar(Car carToUpdate, CarDTOExtended carDTOExtended, List<MultipartFile> newImages) throws ObjectDontExistInDBException, InvalidFileExtension {
        return carMapper.updateCarFromDTOExtended(carToUpdate, carDTOExtended, newImages);
    }


    private static class CarMapper {
        private final BrandService brandService;
        private final ModelService modelService;
        private final GenerationService generationService;
        private final BodyworkTypeService bodyworkTypeService;

        private final FuelService fuelService;
        private final DamageConditionService damageConditionService;
        private final ImageService imageService;
        private final EngineService engineService;
        private final TransmissionService transmissionService;

        public CarMapper(BrandService brandService,
                         ModelService modelService,
                         GenerationService generationService,
                         BodyworkTypeService bodyworkTypeService,
                         FuelService fuelService,
                         DamageConditionService damageConditionService,
                         ImageService imageService,
                         EngineService engineService,
                         TransmissionService transmissionService) {
            this.brandService = brandService;
            this.modelService = modelService;
            this.generationService = generationService;
            this.bodyworkTypeService = bodyworkTypeService;
            this.fuelService = fuelService;
            this.damageConditionService = damageConditionService;
            this.imageService = imageService;
            this.engineService = engineService;
            this.transmissionService = transmissionService;
        }

        public Car mapCarToSaveToCarEntity(CarToSaveDTO carToSaveDTO, List<MultipartFile> images) throws ObjectDontExistInDBException {
            Car car = new Car();
            car.setBrand(brandService.getBrandByName(carToSaveDTO.getBrand()));
            car.setModel(modelService.getModelByNameAndBrand(carToSaveDTO.getModel(), carToSaveDTO.getBrand()));
            if (carToSaveDTO.getGeneration() != null) {
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
            car.setSeatCount(carToSaveDTO.getSeatCount());
            return car;
        }

        public Car updateCarFromDTOExtended(Car carToUpdate, CarDTOExtended carDTOExtended, List<MultipartFile> newImages) throws ObjectDontExistInDBException, InvalidFileExtension {
            updateBrandIfDifferent(carToUpdate, carDTOExtended);
            updateModelIfDifferent(carToUpdate, carDTOExtended);
            updateGenerationIfDifferent(carToUpdate, carDTOExtended);
            updateYearProductionIfDifferent(carToUpdate, carDTOExtended);
            updateBodyworkTypeIfDifferent(carToUpdate, carDTOExtended);
            updateFuelIfDifferent(carToUpdate, carDTOExtended);
            updateMillageIfDifferent(carToUpdate, carDTOExtended);
            updateDamageConditionIfDifferent(carToUpdate, carDTOExtended);
            updateVinIfDifferent(carToUpdate, carDTOExtended);
            updateImagesIfDifferent(carToUpdate, carDTOExtended);
            uploadNewImages(carToUpdate, newImages);
            updateEngineIfDifferent(carToUpdate, carDTOExtended);
            updateTransmissionIfDifferent(carToUpdate, carDTOExtended);
            updateDoorCountIfDifferent(carToUpdate, carDTOExtended);
            updateSeatCountIfDifferent(carToUpdate, carDTOExtended);

            return carToUpdate;
        }

        private void updateBrandIfDifferent(Car carToUpdate, CarDTOExtended carDTOExtended) {
            if (!carToUpdate.getBrand().getName().equals(carDTOExtended.getBrand())){
                carToUpdate.setBrand(brandService.getBrandByName(carDTOExtended.getBrand()));
            }
        }

        private void updateModelIfDifferent(Car carToUpdate, CarDTOExtended carDTOExtended) {
            if (!carToUpdate.getModel().getName().equals(carDTOExtended.getModel())) {
                carToUpdate.setModel(modelService.getModelByNameAndBrand(carDTOExtended.getModel(), carDTOExtended.getBrand()));
            }
        }

        private void updateGenerationIfDifferent(Car carToUpdate, CarDTOExtended carDTOExtended) {
            if (!carToUpdate.getGeneration().getName().equals(carDTOExtended.getGeneration())) {
                carToUpdate.setGeneration(generationService.getGenerationByNameAndModelName(
                        carDTOExtended.getGeneration(),
                        carDTOExtended.getModel()
                ));
            }
        }

        private void updateYearProductionIfDifferent(Car carToUpdate, CarDTOExtended carDTOExtended) {
            if (carToUpdate.getYearProduction() != carDTOExtended.getYearProduction()) {
                carToUpdate.setYearProduction(carDTOExtended.getYearProduction());
            }
        }

        private void updateBodyworkTypeIfDifferent(Car carToUpdate, CarDTOExtended carDTOExtended) {
            if (!carToUpdate.getBodyworkType().getName().equals(carDTOExtended.getBodyworkType())) {
                carToUpdate.setBodyworkType(bodyworkTypeService.getBodyworkTypeByName(carDTOExtended.getBodyworkType()));
            }
        }

        private void updateFuelIfDifferent(Car carToUpdate, CarDTOExtended carDTOExtended) {
            if (!carToUpdate.getFuel().getName().equals(carDTOExtended.getFuel())) {
                carToUpdate.setFuel(fuelService.getFuelByName(carDTOExtended.getFuel()));
            }
        }

        private void updateMillageIfDifferent(Car carToUpdate, CarDTOExtended carDTOExtended) {
            if (carToUpdate.getMillage() != carDTOExtended.getMillage()) {
                carToUpdate.setMillage(carDTOExtended.getMillage());
            }
        }

        private void updateDamageConditionIfDifferent(Car carToUpdate, CarDTOExtended carDTOExtended) {
            if (!carToUpdate.getDamageCondition().getName().equals(carDTOExtended.getDamageCondition())) {
                carToUpdate.setDamageCondition(damageConditionService.getDamageConditionByName(carDTOExtended.getDamageCondition()));
            }
        }

        private void updateVinIfDifferent(Car carToUpdate, CarDTOExtended carDTOExtended) {
            if (!carToUpdate.getVin().equals(carDTOExtended.getVin())) {
                carToUpdate.setVin(carDTOExtended.getVin());
            }
        }

        private void updateImagesIfDifferent(Car carToUpdate, CarDTOExtended carDTOExtended) {
            if (carToUpdate.getImages().size() != carDTOExtended.getImageUrls().size()) {
                List<Image> filteredImages = imageService.updateImages(
                        carToUpdate.getImages(),
                        carDTOExtended.getImageUrls()
                );
                carToUpdate.setImages(filteredImages);
            }
        }

        private void uploadNewImages(Car car, List<MultipartFile> newImagesFiles) {
            if (newImagesFiles != null && !newImagesFiles.isEmpty()) {
                List<Image> images = car.getImages();
                List<Image> newImages = imageService.uploadImages(newImagesFiles);
                images.addAll(newImages);
                car.setImages(images);
            }
        }

        private void updateEngineIfDifferent(Car carToUpdate, CarDTOExtended carDTOExtended) {
            if (!engineService.compareEngineToEngineDTO(carToUpdate.getEngine(), carDTOExtended.getEngineDTO())) {
                Engine newEngineToCar = engineService.getOrCreateEngineForCar(carDTOExtended.getEngineDTO());
                carToUpdate.setEngine(newEngineToCar);
            }
        }

        private void updateTransmissionIfDifferent(Car carToUpdate, CarDTOExtended carDTOExtended) {
            if (!carToUpdate.getTransmission().getName().equals(carDTOExtended.getTransmission())) {
                carToUpdate.setTransmission(transmissionService.getTransmissionByName(carDTOExtended.getTransmission()));
            }
        }

        private void updateDoorCountIfDifferent(Car carToUpdate, CarDTOExtended carDTOExtended) {
            if (carToUpdate.getDoorCount() != carDTOExtended.getDoorCount()) {
                carToUpdate.setDoorCount(carDTOExtended.getDoorCount());
            }
        }

        private void updateSeatCountIfDifferent(Car carToUpdate, CarDTOExtended carDTOExtended) {
            if (carToUpdate.getSeatCount() != carDTOExtended.getSeatCount()) {
                carToUpdate.setSeatCount(carDTOExtended.getSeatCount());
            }
        }

    }

}

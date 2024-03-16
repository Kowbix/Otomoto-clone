package org.example.otomotoclon.serivce;

import org.example.otomotoclon.dto.car.CarDTOExtended;
import org.example.otomotoclon.dto.car.CarToSaveDTO;
import org.example.otomotoclon.entity.Car;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarService {

    Car createCar(CarToSaveDTO carToSaveDTO, List<MultipartFile> images);
    void deleteCar(Car carToDelete);
    Car updateCar(Car carToUpdate, CarDTOExtended carDTOExtended, List<MultipartFile> newImages);
}

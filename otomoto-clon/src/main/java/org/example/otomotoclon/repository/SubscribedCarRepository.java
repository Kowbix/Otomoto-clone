package org.example.otomotoclon.repository;

import org.example.otomotoclon.entity.SubscribedCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribedCarRepository extends JpaRepository<SubscribedCar, Long> {

    boolean existsByBrandNameAndModelNameAndGenerationNameAndUserUsername(String brand, String model, String generation, String username);
    List<SubscribedCar> findByUserUsername(String username);
    List<SubscribedCar> findByBrandNameAndModelNameAndGenerationName(String brand, String model, String generation);
}

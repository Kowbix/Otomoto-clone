package org.example.otomotoclon.repository;

import org.example.otomotoclon.entity.Engine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EngineRepository extends JpaRepository<Engine, Long> {

    Optional<Engine> findEngineByCapacityAndHorsepower(int capacity, int horsepower);
}

package org.example.otomotoclon.repository;

import org.example.otomotoclon.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

    Optional<Model> findModelByNameAndBrandName(String modelName, String brandName);
    Optional<Model> findModelByName(String modelName);
    List<Model> findModelsByBrandName(String name);
}

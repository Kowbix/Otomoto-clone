package org.example.otomotoclon.repository;

import org.example.otomotoclon.entity.Generation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenerationRepository extends JpaRepository<Generation, Long> {

    Optional<Generation> findGenerationByNameAndModelName(String generationName, String modelName);
    List<Generation> findGenerationsByModelName(String name);
}

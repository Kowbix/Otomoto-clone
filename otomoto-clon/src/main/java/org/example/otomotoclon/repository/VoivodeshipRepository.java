package org.example.otomotoclon.repository;

import org.example.otomotoclon.entity.Voivodeship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoivodeshipRepository extends JpaRepository<Voivodeship, Long> {

    Optional<Voivodeship> findVoivodeshipByName(String name);
}

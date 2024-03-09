package org.example.otomotoclon.repository;

import org.example.otomotoclon.entity.BodyworkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BodyworkTypeRepository extends JpaRepository<BodyworkType, Long> {

    Optional<BodyworkType> findBodyworkTypeByName(String name);
}

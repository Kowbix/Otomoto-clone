package org.example.otomotoclon.repository;

import org.example.otomotoclon.entity.DamageCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DamageConditionRepository extends JpaRepository<DamageCondition, Long> {

    Optional<DamageCondition> findDamageConditionByName(String name);
}

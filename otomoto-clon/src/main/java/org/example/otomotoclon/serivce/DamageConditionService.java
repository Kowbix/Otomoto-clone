package org.example.otomotoclon.serivce;

import org.example.otomotoclon.dto.DamageConditionDTO;
import org.example.otomotoclon.entity.DamageCondition;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DamageConditionService {
    void creat(DamageConditionDTO damageConditionDTO) throws ObjectExistInDBException;
    ResponseEntity<List<DamageConditionDTO>> getAllDamageConditions();
    DamageCondition getDamageConditionByName(String name) throws ObjectDontExistInDBException;
}

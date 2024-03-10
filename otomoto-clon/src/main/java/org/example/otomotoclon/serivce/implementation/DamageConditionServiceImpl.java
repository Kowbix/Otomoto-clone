package org.example.otomotoclon.serivce.implementation;

import org.example.otomotoclon.dto.DamageConditionDTO;
import org.example.otomotoclon.entity.DamageCondition;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.example.otomotoclon.repository.DamageConditionRepository;
import org.example.otomotoclon.serivce.DamageConditionService;
import org.example.otomotoclon.translator.DamageConditionMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DamageConditionServiceImpl implements DamageConditionService {

    private final DamageConditionRepository damageConditionRepository;
    private final DamageConditionMapper damageConditionMapper;

    public DamageConditionServiceImpl(DamageConditionRepository damageConditionRepository,
                                      DamageConditionMapper damageConditionMapper) {
        this.damageConditionRepository = damageConditionRepository;
        this.damageConditionMapper = damageConditionMapper;
    }

    @Override
    public void creat(DamageConditionDTO damageConditionDTO) throws ObjectExistInDBException {
        damageConditionRepository.findDamageConditionByName(damageConditionDTO.getName())
                .ifPresent(value -> {
                    throw new ObjectExistInDBException("Damage condition exists in database with given name");
                });
        DamageCondition damageCondition = damageConditionMapper.toEntity(damageConditionDTO);
        damageConditionRepository.save(damageCondition);
    }

    @Override
    public ResponseEntity<List<DamageConditionDTO>> getAllDamageConditions() {
        List<DamageCondition> damageConditions = damageConditionRepository.findAll();
        return ResponseEntity.ok(
                damageConditions.stream()
                    .map(damageConditionMapper::toDto)
                    .collect(Collectors.toList()));
    }

    @Override
    public DamageCondition getDamageConditionByName(String name) throws ObjectDontExistInDBException {
        return damageConditionRepository.findDamageConditionByName(name)
                .orElseThrow(() -> new ObjectDontExistInDBException("Damage condition does not exist in the database"));
    }
}

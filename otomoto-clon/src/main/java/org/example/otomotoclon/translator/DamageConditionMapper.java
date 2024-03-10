package org.example.otomotoclon.translator;

import org.example.otomotoclon.dto.BrandDTO;
import org.example.otomotoclon.dto.DamageConditionDTO;
import org.example.otomotoclon.entity.Brand;
import org.example.otomotoclon.entity.DamageCondition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DamageConditionMapper {
    @Mapping(source = "name", target = "name")
    DamageConditionDTO toDto(DamageCondition damageCondition);

    @Mapping(source = "name", target = "name")
    DamageCondition toEntity(DamageConditionDTO damageConditionDTO);
}

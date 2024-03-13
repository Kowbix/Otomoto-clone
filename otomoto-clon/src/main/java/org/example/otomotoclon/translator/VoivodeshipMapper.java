package org.example.otomotoclon.translator;

import org.example.otomotoclon.dto.VoivodeshipDTO;
import org.example.otomotoclon.entity.Voivodeship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface VoivodeshipMapper {

    @Mapping(source = "name", target = "name")
    VoivodeshipDTO toDto(Voivodeship voivodeship);

    @Mapping(source = "name", target = "name")
    Voivodeship toEntity(VoivodeshipDTO voivodeshipDTO);
}

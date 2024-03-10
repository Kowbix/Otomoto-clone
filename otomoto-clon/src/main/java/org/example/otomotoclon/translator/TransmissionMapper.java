package org.example.otomotoclon.translator;

import org.example.otomotoclon.dto.TransmissionDTO;
import org.example.otomotoclon.entity.Transmission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TransmissionMapper {

    @Mapping(source = "name", target = "name")
    TransmissionDTO toDto(Transmission transmission);

    @Mapping(source = "name", target = "name")
    Transmission toEntity(TransmissionDTO transmissionDTO);
}

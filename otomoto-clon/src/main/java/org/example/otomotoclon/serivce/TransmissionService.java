package org.example.otomotoclon.serivce;

import org.example.otomotoclon.dto.TransmissionDTO;
import org.example.otomotoclon.entity.Transmission;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransmissionService {
    void create(TransmissionDTO transmissionDTO) throws ObjectExistInDBException;
    ResponseEntity<List<TransmissionDTO>> getAllTransition();
    Transmission getTransmissionByName(String name) throws ObjectDontExistInDBException;
}

package org.example.otomotoclon.serivce;

import org.example.otomotoclon.dto.BodyworkTypeDTO;
import org.example.otomotoclon.entity.BodyworkType;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BodyworkTypeService {

    void create(BodyworkTypeDTO bodyworkTypeDTO) throws ObjectExistInDBException;
    BodyworkType getBodyworkTypeByName(String name) throws ObjectDontExistInDBException;

    ResponseEntity<List<BodyworkTypeDTO>> getAllBodyworkTypes();
}

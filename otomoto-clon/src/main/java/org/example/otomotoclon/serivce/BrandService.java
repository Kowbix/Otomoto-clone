package org.example.otomotoclon.serivce;

import org.example.otomotoclon.dto.BrandDTO;
import org.example.otomotoclon.entity.Brand;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BrandService {

    void create(BrandDTO brandDTO);
    ResponseEntity<List<BrandDTO>> getAllBrands();
    Brand getBrandByName(String name);
}

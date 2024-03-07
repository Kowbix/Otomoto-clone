package org.example.otomotoclon.controller;

import lombok.RequiredArgsConstructor;
import org.example.otomotoclon.dto.BrandDTO;
import org.example.otomotoclon.entity.Response;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.example.otomotoclon.serivce.BrandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping()
    public ResponseEntity<Response> createBrand(@RequestBody BrandDTO brandDTO) {
        try {
            brandService.create(brandDTO);
        } catch (ObjectExistInDBException e) {
            return ResponseEntity.status(400).body(new Response("Brand exist in database", HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok(new Response("Brand saved", HttpStatus.OK.value()));
    }

    @GetMapping()
    public ResponseEntity<List<BrandDTO>> getAllBrands() {
        return brandService.getAllBrands();
    }
}

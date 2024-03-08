package org.example.otomotoclon.controller;

import lombok.RequiredArgsConstructor;
import org.example.otomotoclon.dto.ModelDTO;
import org.example.otomotoclon.entity.Response;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.example.otomotoclon.serivce.ModelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/model")
@RequiredArgsConstructor
public class ModelController {

    private final ModelService modelService;

    @PostMapping()
    public ResponseEntity<Response> createBrand(@RequestBody ModelDTO modelDTO) {
        try {
            modelService.create(modelDTO);
        } catch (ObjectExistInDBException e) {
            return ResponseEntity.status(400).body(new Response("Model exist in database", HttpStatus.BAD_REQUEST.value()));
        } catch (ObjectDontExistInDBException e) {
            return ResponseEntity.status(400).body(new Response("Brand do not exist in database", HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok(new Response("Brand saved", HttpStatus.OK.value()));
    }

    @GetMapping()
    public ResponseEntity<List<ModelDTO>> getModelsByBrandName(@RequestParam String brandName) {
        return modelService.getModelsByBrand(brandName);
    }
}

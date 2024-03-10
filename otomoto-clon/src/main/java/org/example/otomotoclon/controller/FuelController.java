package org.example.otomotoclon.controller;

import lombok.RequiredArgsConstructor;
import org.example.otomotoclon.dto.FuelDTO;
import org.example.otomotoclon.entity.Response;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.example.otomotoclon.serivce.FuelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/fuel")
@RequiredArgsConstructor
public class FuelController {

    private final FuelService fuelService;

    @PostMapping()
    public ResponseEntity<Response> createFuel(@RequestBody FuelDTO fuelDTO) {
        try {
            fuelService.create(fuelDTO);
        } catch (ObjectExistInDBException e) {
            return ResponseEntity.status(400).body(new Response(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok(new Response("Fuel saved", HttpStatus.OK.value()));
    }

    @GetMapping()
    public ResponseEntity<List<FuelDTO>> getAllFuels(){
        return fuelService.getAllFuels();
    }
}

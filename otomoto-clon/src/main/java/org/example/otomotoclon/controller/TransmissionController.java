package org.example.otomotoclon.controller;

import lombok.RequiredArgsConstructor;
import org.example.otomotoclon.dto.TransmissionDTO;
import org.example.otomotoclon.entity.Response;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.example.otomotoclon.serivce.TransmissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/transmission")
@RequiredArgsConstructor
public class TransmissionController {

    private final TransmissionService transmissionService;

    @PostMapping
    public ResponseEntity<Response> createTransmission(@RequestBody TransmissionDTO transmissionDTO) {
        try {
            transmissionService.create(transmissionDTO);
        } catch (ObjectExistInDBException e) {
            return ResponseEntity.status(400).body(new Response(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok(new Response("Transmission saved", HttpStatus.OK.value()));
    }

    @GetMapping()
    public ResponseEntity<List<TransmissionDTO>> getAllTransmission() {
        return transmissionService.getAllTransition();
    }

}

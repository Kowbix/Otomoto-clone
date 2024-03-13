package org.example.otomotoclon.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.otomotoclon.dto.VoivodeshipDTO;
import org.example.otomotoclon.entity.Response;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.example.otomotoclon.serivce.VoivodeshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/voivodeship")
@RequiredArgsConstructor
public class VoivodeshipController {

    private final VoivodeshipService voivodeshipService;

    @PostMapping
    public ResponseEntity<Response> createVoivodeship(@RequestBody VoivodeshipDTO voivodeshipDTO) {
        try {
            voivodeshipService.create(voivodeshipDTO);
        } catch (ObjectExistInDBException e) {
            return ResponseEntity.status(400).body(new Response(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok(new Response("Voivodeship saved", HttpStatus.OK.value()));
    }

    @GetMapping
    public ResponseEntity<List<VoivodeshipDTO>> getAllVoivodeship() {
        return voivodeshipService.getAllVoivodeships();
    }
}

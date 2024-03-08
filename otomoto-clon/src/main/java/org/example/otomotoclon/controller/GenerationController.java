package org.example.otomotoclon.controller;

import lombok.RequiredArgsConstructor;
import org.example.otomotoclon.dto.GenerationDTO;
import org.example.otomotoclon.entity.Response;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.example.otomotoclon.serivce.GenerationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/generation")
@RequiredArgsConstructor
public class GenerationController {

    private final GenerationService generationService;

    @PostMapping
    public ResponseEntity<Response> createGeneration(@RequestBody GenerationDTO generationDTO) {
        try {
            generationService.create(generationDTO);
        } catch (ObjectExistInDBException e) {
            return ResponseEntity.status(400).body(new Response(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } catch (ObjectDontExistInDBException e) {
            return ResponseEntity.status(400).body(new Response(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok(new Response("Generation saved", HttpStatus.OK.value()));
    }

    @GetMapping
    public ResponseEntity<List<GenerationDTO>> getGenerationsByModelName(@RequestParam String modelName) {
        return generationService.getGenerationsByModelName(modelName);
    }
}

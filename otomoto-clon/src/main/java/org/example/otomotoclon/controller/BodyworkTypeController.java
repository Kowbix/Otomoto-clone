package org.example.otomotoclon.controller;

import lombok.RequiredArgsConstructor;
import org.example.otomotoclon.dto.BodyworkTypeDTO;
import org.example.otomotoclon.entity.Response;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.example.otomotoclon.serivce.BodyworkTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/bodywork")
@RequiredArgsConstructor
public class BodyworkTypeController {

    private final BodyworkTypeService bodyworkTypeService;

    @PostMapping()
    public ResponseEntity<Response> createBodyworkType(@RequestBody BodyworkTypeDTO bodyworkTypeDTO) {
        try {
            bodyworkTypeService.create(bodyworkTypeDTO);
        } catch (ObjectExistInDBException e) {
            return ResponseEntity.status(400).body(new Response(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok(new Response("Bodywork type saved", HttpStatus.OK.value()));
    }

    @GetMapping()
    public ResponseEntity<List<BodyworkTypeDTO>> getAllBodyworkTypes(){
        return bodyworkTypeService.getAllBodyworkTypes();
    }
}

package org.example.otomotoclon.controller;

import lombok.RequiredArgsConstructor;
import org.example.otomotoclon.dto.DamageConditionDTO;
import org.example.otomotoclon.entity.Response;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.example.otomotoclon.serivce.DamageConditionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/damage-condition")
@RequiredArgsConstructor
public class DamageConditionController {

    private final DamageConditionService damageConditionService;

    @PostMapping()
    public ResponseEntity<Response> createDamageCondition(@RequestBody DamageConditionDTO damageConditionDTO) {
        try {
            damageConditionService.creat(damageConditionDTO);
        } catch (ObjectExistInDBException e) {
            return ResponseEntity.status(400).body(new Response(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.ok(new Response("Damage condition saved", HttpStatus.OK.value()));
    }

    @GetMapping
    public ResponseEntity<List<DamageConditionDTO>> getAllDamageConditions() {
        return damageConditionService.getAllDamageConditions();
    }
}

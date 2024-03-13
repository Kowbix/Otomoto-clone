package org.example.otomotoclon.serivce;

import org.example.otomotoclon.dto.VoivodeshipDTO;
import org.example.otomotoclon.entity.Voivodeship;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VoivodeshipService {

    void create(VoivodeshipDTO voivodeshipDTO);
    ResponseEntity<List<VoivodeshipDTO>> getAllVoivodeships();
    Voivodeship getVoivodeshipByName(String name);
}

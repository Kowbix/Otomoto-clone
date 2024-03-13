package org.example.otomotoclon.serivce.implementation;

import org.example.otomotoclon.dto.VoivodeshipDTO;
import org.example.otomotoclon.entity.Voivodeship;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.example.otomotoclon.repository.VoivodeshipRepository;
import org.example.otomotoclon.serivce.VoivodeshipService;
import org.example.otomotoclon.translator.VoivodeshipMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoivodeshipServiceImpl implements VoivodeshipService {

    private final VoivodeshipRepository voivodeshipRepository;
    private final VoivodeshipMapper voivodeshipMapper;

    public VoivodeshipServiceImpl(VoivodeshipRepository voivodeshipRepository,
                                  VoivodeshipMapper voivodeshipMapper) {
        this.voivodeshipRepository = voivodeshipRepository;
        this.voivodeshipMapper = voivodeshipMapper;
    }

    @Override
    public void create(VoivodeshipDTO voivodeshipDTO) throws ObjectExistInDBException {
        voivodeshipRepository.findVoivodeshipByName(voivodeshipDTO.getName())
                .ifPresent(value -> {
                    throw new ObjectExistInDBException("Voivodeship " + voivodeshipDTO.getName() + " exists in database");
                });
        Voivodeship voivodeship = new Voivodeship();
        voivodeship.setName(voivodeshipDTO.getName());
        voivodeshipRepository.save(voivodeship);
    }

    @Override
    public ResponseEntity<List<VoivodeshipDTO>> getAllVoivodeships() {
        List<Voivodeship> voivodeships = voivodeshipRepository.findAll();
        return ResponseEntity.ok(
                voivodeships.stream()
                        .map(voivodeship -> voivodeshipMapper.toDto(voivodeship))
                        .collect(Collectors.toList()));
    }

    @Override
    public Voivodeship getVoivodeshipByName(String name) throws ObjectDontExistInDBException {
        return voivodeshipRepository.findVoivodeshipByName(name).orElseThrow(() ->
                new ObjectDontExistInDBException("Voivodeship " + name + " does not exists in database"));
    }
}

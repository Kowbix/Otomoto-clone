package org.example.otomotoclon.serivce.implementation;

import org.example.otomotoclon.dto.TransmissionDTO;
import org.example.otomotoclon.entity.Transmission;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.example.otomotoclon.repository.TransmissionRepository;
import org.example.otomotoclon.serivce.TransmissionService;
import org.example.otomotoclon.translator.TransmissionMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransmissionServiceImpl implements TransmissionService {

    private final TransmissionRepository transmissionRepository;
    private final TransmissionMapper transmissionMapper;

    public TransmissionServiceImpl(TransmissionRepository transmissionRepository,
                                   TransmissionMapper transmissionMapper) {
        this.transmissionRepository = transmissionRepository;
        this.transmissionMapper = transmissionMapper;
    }

    @Override
    public void create(TransmissionDTO transmissionDTO) throws ObjectExistInDBException {
        transmissionRepository.findTransmissionByName(transmissionDTO.getName()).ifPresent(value -> {
            throw new ObjectExistInDBException("Transmission exists in database with given name");
        });
        Transmission transmission = transmissionMapper.toEntity(transmissionDTO);
        transmissionRepository.save(transmission);
    }

    @Override
    public ResponseEntity<List<TransmissionDTO>> getAllTransition() {
        List<Transmission> transmissions = transmissionRepository.findAll();
        return ResponseEntity.ok(
                transmissions.stream()
                    .map(transmissionMapper::toDto)
                    .collect(Collectors.toList()));
    }

    @Override
    public Transmission getTransmissionByName(String name) throws ObjectDontExistInDBException {
        return transmissionRepository.findTransmissionByName(name)
                .orElseThrow(() -> new ObjectDontExistInDBException("Transmission dose not exists in database with given name"));
    }
}

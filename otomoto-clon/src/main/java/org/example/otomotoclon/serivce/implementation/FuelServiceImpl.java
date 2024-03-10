package org.example.otomotoclon.serivce.implementation;

import org.example.otomotoclon.dto.FuelDTO;
import org.example.otomotoclon.entity.Fuel;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.example.otomotoclon.repository.FuelRepository;
import org.example.otomotoclon.serivce.FuelService;
import org.example.otomotoclon.translator.FuelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuelServiceImpl implements FuelService {

    private final FuelRepository fuelRepository;
    private final FuelMapper fuelMapper;

    public FuelServiceImpl(FuelRepository fuelRepository, FuelMapper fuelMapper) {
        this.fuelRepository = fuelRepository;
        this.fuelMapper = fuelMapper;
    }


    @Override
    public void create(FuelDTO fuelDTO) throws ObjectExistInDBException {
        fuelRepository.findFuelByName(fuelDTO.getName()).ifPresent(value -> {
            throw new ObjectExistInDBException("Fuel exists in database with given name");
        });
        Fuel fuel = fuelMapper.toEntity(fuelDTO);
        fuelRepository.save(fuel);
    }

    @Override
    public ResponseEntity<List<FuelDTO>> getAllFuels() {
        List<Fuel> fuels = fuelRepository.findAll();
        return ResponseEntity.ok(
                fuels.stream()
                    .map(fuelMapper::toDto)
                    .collect(Collectors.toList()));
    }

    @Override
    public Fuel getFuelByName(String name) throws ObjectDontExistInDBException {
        return fuelRepository.findFuelByName(name)
                .orElseThrow(() -> new ObjectDontExistInDBException("Fuel does not exist in the database"));
    }
}

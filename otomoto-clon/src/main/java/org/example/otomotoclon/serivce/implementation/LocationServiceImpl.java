package org.example.otomotoclon.serivce.implementation;

import org.example.otomotoclon.dto.LocationDTO;
import org.example.otomotoclon.entity.Location;
import org.example.otomotoclon.repository.LocationRepository;
import org.example.otomotoclon.serivce.LocationService;
import org.example.otomotoclon.serivce.VoivodeshipService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final VoivodeshipService voivodeshipService;

    public LocationServiceImpl(LocationRepository locationRepository,
                               VoivodeshipService voivodeshipService) {
        this.locationRepository = locationRepository;
        this.voivodeshipService = voivodeshipService;
    }

    @Override
    public Location getOrCreateLocationForAnnouncement(LocationDTO locationDTO) {
        String cityName = locationDTO.getCityName();
        String voivodeshipName = locationDTO.getVoivodeshipName();
        Optional<Location> existingLocationOptional = locationRepository.findLocationByCityNameAndVoivodeshipName(
                cityName,
                voivodeshipName
        );
        if (existingLocationOptional.isPresent()) {
            return existingLocationOptional.get();
        }
        return create(cityName, voivodeshipName);
    }

    @Override
    public boolean compareLocationToLocationDTO(Location location, LocationDTO locationDTO) {
        if(location == null || locationDTO == null) {
            return false;
        }
        return location.getCityName() == locationDTO.getCityName() &&
                location.getVoivodeship().getName() == locationDTO.getVoivodeshipName();
    }

    private Location create(String cityName, String  voivodeshipName) {
        Location location = new Location();
        location.setCityName(cityName);
        location.setVoivodeship(voivodeshipService.getVoivodeshipByName(voivodeshipName));
        return locationRepository.save(location);
    }
}

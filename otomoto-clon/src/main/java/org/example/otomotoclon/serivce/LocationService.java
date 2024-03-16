package org.example.otomotoclon.serivce;

import org.example.otomotoclon.dto.LocationDTO;
import org.example.otomotoclon.entity.Location;

public interface LocationService {
    Location getOrCreateLocationForAnnouncement(LocationDTO locationDTO);
    boolean compareLocationToLocationDTO(Location location, LocationDTO locationDTO);
}

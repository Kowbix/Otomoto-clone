package org.example.otomotoclon.serivce;

import org.example.otomotoclon.dto.announcement.AnnouncementDTO;
import org.example.otomotoclon.dto.announcement.AnnouncementDTOExtended;
import org.example.otomotoclon.dto.announcement.AnnouncementToSaveDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AnnouncementService {

    AnnouncementDTOExtended createAnnouncement(AnnouncementToSaveDTO announcementToSave, List<MultipartFile> images) throws IOException;
    void activeAnnouncement(long announcementId);
    void deleteAnnouncement(long announcementId);
    AnnouncementDTOExtended updateAnnouncement(long announcementId);
    AnnouncementDTOExtended getAnnouncementById(long announcementId);
    List<AnnouncementDTO> getAnnouncements(int page, int limit,
                                           String brand,
                                           String model,
                                           String generation,
                                           int yearProduction_min,
                                           int yearProduction_max,
                                           String bodyworkType,
                                           String fuel,
                                           int millage_min,
                                           int millage_max,
                                           boolean isVin,
                                           int capacity_min,
                                           int capacity_max,
                                           int horsepower_min,
                                           int horsepower_max,
                                           int doors_min,
                                           int doors_max,
                                           int seats_min,
                                           int seats_max);
}

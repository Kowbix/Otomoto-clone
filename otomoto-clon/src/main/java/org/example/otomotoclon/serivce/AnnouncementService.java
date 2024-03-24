package org.example.otomotoclon.serivce;

import org.example.otomotoclon.dto.announcement.AnnouncementDTO;
import org.example.otomotoclon.dto.announcement.AnnouncementDTOExtended;
import org.example.otomotoclon.dto.announcement.AnnouncementToSaveDTO;
import org.example.otomotoclon.request.AnnouncementFilterRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AnnouncementService {

    AnnouncementDTOExtended createAnnouncement(AnnouncementToSaveDTO announcementToSave, List<MultipartFile> images) throws IOException;
    void activeAnnouncement(long announcementId);
    void deleteAnnouncement(long announcementId);
    AnnouncementDTOExtended updateAnnouncement(AnnouncementDTOExtended announcementDTOExtended, List<MultipartFile> newImages) throws IOException;
    AnnouncementDTOExtended getAnnouncementById(long announcementId);
    List<AnnouncementDTO> getAnnouncementsByAdvanceFilters(AnnouncementFilterRequest request);
}

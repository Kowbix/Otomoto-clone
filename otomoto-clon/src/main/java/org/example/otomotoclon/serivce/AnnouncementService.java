package org.example.otomotoclon.serivce;

import org.example.otomotoclon.dto.announcement.AnnouncementDTO;
import org.example.otomotoclon.dto.announcement.AnnouncementDTOExtended;
import org.example.otomotoclon.dto.announcement.AnnouncementToSaveDTO;
import org.example.otomotoclon.request.AnnouncementFilterRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.List;

public interface AnnouncementService {

    AnnouncementDTOExtended createAnnouncement(AnnouncementToSaveDTO announcementToSave, List<MultipartFile> images, String username) throws IOException;
    void activeAnnouncement(long announcementId, String username) throws AuthenticationException;
    void deleteAnnouncement(long announcementId, String username) throws AuthenticationException;
    AnnouncementDTOExtended updateAnnouncement(AnnouncementDTOExtended announcementDTOExtended, List<MultipartFile> newImages, String username) throws IOException, AuthenticationException;
    AnnouncementDTOExtended getAnnouncementById(long announcementId);
    List<AnnouncementDTO> getAnnouncementsByAdvanceFilters(AnnouncementFilterRequest request);
    List<AnnouncementDTO> getAnnouncementsByBasicFilters(String brand, String model, String generation, int page, int limit, String sort, String order);
    List<AnnouncementDTO> getAnnouncementByUsername(String username);
}

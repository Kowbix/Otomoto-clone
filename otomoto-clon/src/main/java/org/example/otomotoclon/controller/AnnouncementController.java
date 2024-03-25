package org.example.otomotoclon.controller;

import lombok.RequiredArgsConstructor;
import org.example.otomotoclon.dto.announcement.AnnouncementDTO;
import org.example.otomotoclon.dto.announcement.AnnouncementDTOExtended;
import org.example.otomotoclon.dto.announcement.AnnouncementToSaveDTO;
import org.example.otomotoclon.entity.Response;
import org.example.otomotoclon.exception.InvalidFileExtension;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.request.AnnouncementFilterRequest;
import org.example.otomotoclon.serivce.AnnouncementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping
    public ResponseEntity<?> createAnnouncement(@ModelAttribute AnnouncementToSaveDTO announcementToSaveDTO,
                                                @RequestParam("images") List<MultipartFile> images) {
        AnnouncementDTOExtended savedAnnouncement;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            savedAnnouncement =  announcementService.createAnnouncement(
                    announcementToSaveDTO,
                    images,
                    username
            );
        } catch (InvalidFileExtension | ObjectDontExistInDBException e) {
            return ResponseEntity.status(400).body(new Response(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new Response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }

        return ResponseEntity.ok(savedAnnouncement);
    }

    @PutMapping("/active/{announcementId}")
    public ResponseEntity<Response> activeAnnouncementById(@PathVariable long announcementId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            announcementService.activeAnnouncement(announcementId, username);
        } catch (ObjectDontExistInDBException | AuthenticationException e) {
            return ResponseEntity.status(400).body(new Response(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok(new Response("Announcement activated", HttpStatus.OK.value()));
    }

    @DeleteMapping("/delete/{announcementId}")
    public ResponseEntity<Response> deleteAnnouncementById(@PathVariable long announcementId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            announcementService.deleteAnnouncement(announcementId, username);
        } catch (ObjectDontExistInDBException | AuthenticationException e) {
            return ResponseEntity.status(400).body(new Response(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok(new Response("Announcement deleted", HttpStatus.OK.value()));
    }

    @GetMapping("/{announcementId}")
    public ResponseEntity<?> getAnnouncementById(@PathVariable long announcementId) {
        AnnouncementDTOExtended announcementDTOExtended;
        try {
            announcementDTOExtended = announcementService.getAnnouncementById(announcementId);
        } catch (ObjectDontExistInDBException e) {
            return ResponseEntity.status(400).body(new Response(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok(announcementDTOExtended);
    }

    @PutMapping()
    public ResponseEntity<?> updateAnnouncement(@ModelAttribute AnnouncementDTOExtended announcementDTOExtended,
                                                @RequestParam(value = "images",  required = false) List<MultipartFile> images) {
        AnnouncementDTOExtended updatedAnnouncement;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            updatedAnnouncement =  announcementService.updateAnnouncement(
                    announcementDTOExtended,
                    images,
                    username
            );
        } catch (InvalidFileExtension | ObjectDontExistInDBException | AuthenticationException e) {
            return ResponseEntity.status(400).body(new Response(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new Response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }

        return ResponseEntity.ok(updatedAnnouncement);
    }

    @GetMapping("/advance")
    public ResponseEntity<List<AnnouncementDTO>> getByAdvanceFilters (@RequestBody AnnouncementFilterRequest request) {
        List<AnnouncementDTO> announcements = announcementService.getAnnouncementsByAdvanceFilters(request);
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/")
    public ResponseEntity<List<AnnouncementDTO>> getByBasicFilters (@RequestParam(required = false) String brand,
                                                                    @RequestParam(required = false) String model,
                                                                    @RequestParam(required = false) String generation,
                                                                    @RequestParam(required = false,defaultValue = "1") int _page,
                                                                    @RequestParam(required = false,defaultValue = "10") int _limit,
                                                                    @RequestParam(required = false,defaultValue = "date") String _sort,
                                                                    @RequestParam(required = false,defaultValue = "asc") String _order
    ) {
        List<AnnouncementDTO> announcements = announcementService.getAnnouncementsByBasicFilters(brand, model, generation, _page, _limit, _sort, _order);
        return ResponseEntity.ok(announcements);
    }
}

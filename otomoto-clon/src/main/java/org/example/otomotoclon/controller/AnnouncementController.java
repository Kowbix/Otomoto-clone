package org.example.otomotoclon.controller;

import lombok.RequiredArgsConstructor;
import org.example.otomotoclon.dto.announcement.AnnouncementDTOExtended;
import org.example.otomotoclon.dto.announcement.AnnouncementToSaveDTO;
import org.example.otomotoclon.entity.Response;
import org.example.otomotoclon.exception.InvalidFileExtension;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.serivce.AnnouncementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        try {
            savedAnnouncement =  announcementService.createAnnouncement(
                    announcementToSaveDTO,
                    images
            );
        } catch (InvalidFileExtension e) {
            return ResponseEntity.status(400).body(new Response(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        catch (ObjectDontExistInDBException e) {
            return ResponseEntity.status(400).body(new Response(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.ok(savedAnnouncement);
    }

    @PutMapping("/active/{announcementId}")
    public ResponseEntity<Response> activeAnnouncementById(@PathVariable long announcementId) {
        try {
            announcementService.activeAnnouncement(announcementId);
        } catch (ObjectDontExistInDBException e) {
            return ResponseEntity.status(400).body(new Response(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok(new Response("Announcement activated", HttpStatus.OK.value()));
    }
}

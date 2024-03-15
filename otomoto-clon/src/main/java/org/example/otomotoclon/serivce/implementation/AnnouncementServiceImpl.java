package org.example.otomotoclon.serivce.implementation;

import org.example.otomotoclon.dto.announcement.AnnouncementDTO;
import org.example.otomotoclon.dto.announcement.AnnouncementDTOExtended;
import org.example.otomotoclon.dto.announcement.AnnouncementToSaveDTO;
import org.example.otomotoclon.entity.Announcement;
import org.example.otomotoclon.entity.Car;
import org.example.otomotoclon.entity.Role;
import org.example.otomotoclon.entity.User;
import org.example.otomotoclon.exception.InvalidFileExtension;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.repository.AnnouncementRepository;
import org.example.otomotoclon.serivce.AnnouncementService;
import org.example.otomotoclon.serivce.CarService;
import org.example.otomotoclon.serivce.DescriptionFileService;
import org.example.otomotoclon.serivce.LocationService;
import org.example.otomotoclon.translator.AnnouncementToAnnouncementDToExtendedMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final CarService carService;
    private final LocationService locationService;
    private final DescriptionFileService descriptionFileService;
    private final AnnouncementToAnnouncementDToExtendedMapper announcementToAnnouncementDToExtendedMapper;

    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository,
                                   CarService carService,
                                   LocationService locationService,
                                   DescriptionFileService descriptionFileService,
                                   AnnouncementToAnnouncementDToExtendedMapper announcementToAnnouncementDToExtendedMapper) {
        this.announcementRepository = announcementRepository;
        this.carService = carService;
        this.locationService = locationService;
        this.descriptionFileService = descriptionFileService;
        this.announcementToAnnouncementDToExtendedMapper = announcementToAnnouncementDToExtendedMapper;
    }

    @Transactional
    @Override
    public AnnouncementDTOExtended createAnnouncement(AnnouncementToSaveDTO announcementToSaveDTO,
                                                      List<MultipartFile> images) throws ObjectDontExistInDBException, InvalidFileExtension, IOException {
        return saveAnnouncement(announcementToSaveDTO, images);
    }

    private AnnouncementDTOExtended saveAnnouncement(AnnouncementToSaveDTO announcementToSaveDTO,
                                                     List<MultipartFile> images) throws ObjectDontExistInDBException, InvalidFileExtension, IOException {
        Announcement announcementToSave = mapAnnouncementToSaveToAnnouncementEntity(announcementToSaveDTO, images);
        Announcement announcementSaved =  announcementRepository.save(announcementToSave);
        return announcementToAnnouncementDToExtendedMapper.toAnnouncementDTOExtended(announcementSaved);
    }
    private Announcement mapAnnouncementToSaveToAnnouncementEntity(AnnouncementToSaveDTO announcementToSaveDTO,
                                                                   List<MultipartFile> images) throws ObjectDontExistInDBException, InvalidFileExtension, IOException {
        Car car = carService.createCar(announcementToSaveDTO.getCarToSaveDTO(), images);
        Announcement announcement = new Announcement();
        announcement.setCar(car);
        announcement.setMainImage(car.getImages().get(0));
        String descriptionUrl = descriptionFileService.createAndUploadDescriptionFile(announcementToSaveDTO.getDescription(), car.getId());
        announcement.setDescriptionUrl(descriptionUrl);
        announcement.setPrice(announcementToSaveDTO.getPrice());
        announcement.setLocation(locationService.getOrCreateLocationForAnnouncement(announcementToSaveDTO.getLocationDTO()));
//        TODO: add user to announcement from JWT and delete function to add test user
        announcement.setUser(setTestUser());
        Date currentDate = new Date();
        announcement.setAddedDate(currentDate);
        announcement.setViews(0l);
        announcement.setActive(false);

        return announcement;
    }

    @Override
    public void activeAnnouncement(long announcementId) throws ObjectDontExistInDBException{
        Optional<Announcement> announcement = announcementRepository.findById(announcementId);
        if(announcement.isEmpty()) {
            throw new ObjectDontExistInDBException("Announcement with id: " + announcementId + " does not exists");
        }
        Announcement announcementToUpdate = announcement.get();
        announcementToUpdate.setActive(true);
        announcementRepository.save(announcementToUpdate);
    }

    @Transactional
    @Override
    public void deleteAnnouncement(long announcementId) {
//        TODO: delete announcement -> car and files
    }

    @Transactional
    @Override
    public AnnouncementDTOExtended updateAnnouncement(long announcementId) {
//        TODO: update announcement
        return null;
    }

    @Override
    public AnnouncementDTOExtended getAnnouncementById(long announcementId) {
//        TODO: get announcement by id
        return null;
    }

    @Override
    public List<AnnouncementDTO> getAnnouncements(int page,
                                                  int limit,
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
                                                  int seats_max) {

//       TODO: get announcements by filters
        return null;
    }

    private User setTestUser() {
        User user = new User();
        Role role = new Role();
        Set<Role> roles = new HashSet<>();
        role.setId(2l);
        role.setName("user");
        roles.add(role);

        user.setId(1l);
        user.setName("test");
        user.setUsername("test");
        user.setEmail("test@op.pl");
        user.setPassword("123");
        user.setRoles(roles);

        return user;
    }
}

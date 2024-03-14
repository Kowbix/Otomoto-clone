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
import org.example.otomotoclon.serivce.LocationService;
import org.example.otomotoclon.translator.AnnouncementToAnnouncementDToExtendedMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final CarService carService;
    private final LocationService locationService;
    private final AnnouncementToAnnouncementDToExtendedMapper announcementToAnnouncementDToExtendedMapper;

    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository,
                                   CarService carService,
                                   LocationService locationService,
                                   AnnouncementToAnnouncementDToExtendedMapper announcementToAnnouncementDToExtendedMapper) {
        this.announcementRepository = announcementRepository;
        this.carService = carService;
        this.locationService = locationService;
        this.announcementToAnnouncementDToExtendedMapper = announcementToAnnouncementDToExtendedMapper;
    }

    @Transactional
    @Override
    public AnnouncementDTOExtended createAnnouncement(AnnouncementToSaveDTO announcementToSaveDTO,
                                                      List<MultipartFile> images) throws ObjectDontExistInDBException, InvalidFileExtension {
        return saveAnnouncement(announcementToSaveDTO, images);
    }

    private AnnouncementDTOExtended saveAnnouncement(AnnouncementToSaveDTO announcementToSaveDTO,
                                                     List<MultipartFile> images) throws ObjectDontExistInDBException, InvalidFileExtension {
        Announcement announcementToSave = mapAnnouncementToSaveToAnnouncementEntity(announcementToSaveDTO, images);
        Announcement announcementSaved =  announcementRepository.save(announcementToSave);
        return announcementToAnnouncementDToExtendedMapper.toAnnouncementDTOExtended(announcementSaved);
    }
    private Announcement mapAnnouncementToSaveToAnnouncementEntity(AnnouncementToSaveDTO announcementToSaveDTO,
                                                                   List<MultipartFile> images) throws ObjectDontExistInDBException, InvalidFileExtension {
        Car car = carService.createCar(announcementToSaveDTO.getCarToSaveDTO(), images);
        Announcement announcement = new Announcement();
        announcement.setCar(car);
        announcement.setMainImage(car.getImages().get(0));
//        TODO: add description file service
        announcement.setDescriptionUrl(announcementToSaveDTO.getDescription());
        announcement.setPrice(announcementToSaveDTO.getPrice());
        announcement.setLocation(locationService.getOrCreateLocationForAnnouncement(announcementToSaveDTO.getLocationDTO()));
//        TODO: add user to announcement from JWT
        announcement.setUser(setTestUser());
        Date currentDate = new Date();
        announcement.setAddedDate(currentDate);
        announcement.setViews(0l);
        announcement.setActive(false);

        return announcement;
    }

    @Transactional
    @Override
    public void activeAnnouncement(long announcementId) {

    }

    @Transactional
    @Override
    public void deleteAnnouncement(long announcementId) {

    }

    @Transactional
    @Override
    public AnnouncementDTOExtended updateAnnouncement(long announcementId) {
        return null;
    }

    @Override
    public AnnouncementDTOExtended getAnnouncementById(long announcementId) {
        return null;
    }

    @Override
    public List<AnnouncementDTO> getAnnouncements(int page, int limit, String brand, String model, String generation, int yearProduction_min, int yearProduction_max, String bodyworkType, String fuel, int millage_min, int millage_max, boolean isVin, int capacity_min, int capacity_max, int horsepower_min, int horsepower_max, int doors_min, int doors_max, int seats_min, int seats_max) {
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

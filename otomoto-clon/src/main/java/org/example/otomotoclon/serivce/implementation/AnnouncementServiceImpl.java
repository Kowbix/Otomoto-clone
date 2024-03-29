package org.example.otomotoclon.serivce.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.example.otomotoclon.dto.announcement.AnnouncementDTO;
import org.example.otomotoclon.dto.announcement.AnnouncementDTOExtended;
import org.example.otomotoclon.dto.announcement.AnnouncementToSaveDTO;
import org.example.otomotoclon.dto.car.CarDTOExtended;
import org.example.otomotoclon.entity.*;
import org.example.otomotoclon.exception.InvalidFileExtension;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.repository.AnnouncementRepository;
import org.example.otomotoclon.request.AnnouncementFilterRequest;
import org.example.otomotoclon.serivce.AnnouncementService;
import org.example.otomotoclon.serivce.CarService;
import org.example.otomotoclon.serivce.DescriptionFileService;
import org.example.otomotoclon.serivce.LocationService;
import org.example.otomotoclon.translator.AnnouncementToAnnouncementDTOExtendedMapper;
import org.example.otomotoclon.translator.AnnouncementToDTOMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @PersistenceContext
    private EntityManager entityManager;
    private final AnnouncementRepository announcementRepository;
    private final CarService carService;
    private final LocationService locationService;
    private final DescriptionFileService descriptionFileService;
    private final AnnouncementToAnnouncementDTOExtendedMapper announcementToAnnouncementDToExtendedMapper;
    private final AnnouncementToDTOMapper announcementToDTOMapper;

    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository,
                                   CarService carService,
                                   LocationService locationService,
                                   DescriptionFileService descriptionFileService,
                                   AnnouncementToAnnouncementDTOExtendedMapper announcementToAnnouncementDToExtendedMapper,
                                   AnnouncementToDTOMapper announcementToDTOMapper) {
        this.announcementRepository = announcementRepository;
        this.carService = carService;
        this.locationService = locationService;
        this.descriptionFileService = descriptionFileService;
        this.announcementToAnnouncementDToExtendedMapper = announcementToAnnouncementDToExtendedMapper;
        this.announcementToDTOMapper = announcementToDTOMapper;
    }

    @Transactional
    @Override
    public AnnouncementDTOExtended createAnnouncement(AnnouncementToSaveDTO announcementToSaveDTO,
                                                      List<MultipartFile> images) throws ObjectDontExistInDBException, InvalidFileExtension, IOException {

        Announcement savedAnnouncement = saveAnnouncement(announcementToSaveDTO, images);
        return announcementToAnnouncementDToExtendedMapper.toDTOExtended(savedAnnouncement);
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
    public void deleteAnnouncement(long announcementId) throws ObjectDontExistInDBException{
//        TODO: add check function to is the announcement belong to user
        Announcement announcementToDelete = announcementRepository.findById(announcementId).orElseThrow(
                () -> new ObjectDontExistInDBException("Announcement with id: " + announcementId + " does not exists")
        );
        announcementRepository.delete(announcementToDelete);
        descriptionFileService.deleteDescriptionFile(announcementToDelete.getDescriptionUrl());
        carService.deleteCar(announcementToDelete.getCar());
    }

    @Override
    @Transactional
    public AnnouncementDTOExtended updateAnnouncement(AnnouncementDTOExtended announcementDTOExtended, List<MultipartFile> newImages) throws ObjectDontExistInDBException, InvalidFileExtension, IOException {
//        TODO: add check function to is the announcement belong to user
        Announcement announcementToUpdate = announcementRepository.findById(announcementDTOExtended.getId()).orElseThrow(
                () -> new ObjectDontExistInDBException("Announcement with id: " + announcementDTOExtended.getId() + " does not exists")
        );
        Announcement updatedAnnouncement = updateAnnouncementFromDTO(announcementToUpdate, announcementDTOExtended, newImages);

        return announcementToAnnouncementDToExtendedMapper.toDTOExtended(updatedAnnouncement);
    }

    @Override
    public AnnouncementDTOExtended getAnnouncementById(long announcementId) throws ObjectDontExistInDBException {
        Announcement announcement = announcementRepository.findById(announcementId).orElseThrow(
                () -> new ObjectDontExistInDBException("Announcement with id: " + announcementId + " does not exists")
        );
        return announcementToAnnouncementDToExtendedMapper.toDTOExtended(announcement);
    }

    @Override
    public List<AnnouncementDTO> getAnnouncementsByAdvanceFilters(AnnouncementFilterRequest request) {
        List<Announcement> filteredAnnouncements = getAnnouncementsWithFilters(request);

        return filteredAnnouncements.stream().map(announcementToDTOMapper::toDTO).collect(Collectors.toList());
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
//TODO: delete this soon
        return user;
    }

    private Announcement saveAnnouncement(AnnouncementToSaveDTO announcementToSaveDTO,
                                                     List<MultipartFile> images) throws ObjectDontExistInDBException, InvalidFileExtension, IOException {
        Announcement announcementToSave = mapAnnouncementToSaveToAnnouncementEntity(announcementToSaveDTO, images);
        return announcementRepository.save(announcementToSave);
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
        announcement.setViews(0L);
        announcement.setActive(false);

        return announcement;
    }

    private Announcement updateAnnouncementFromDTO(Announcement announcementToUpdate, AnnouncementDTOExtended announcementDTOExtended, List<MultipartFile> newImages) throws ObjectDontExistInDBException, InvalidFileExtension, IOException{
        Car carToUpdate = announcementToUpdate.getCar();
        CarDTOExtended carDTOExtended = announcementDTOExtended.getCar();
        Car updatedCar = carService.updateCar(carToUpdate, carDTOExtended, newImages);
        announcementToUpdate.setCar(updatedCar);
        if (!updatedCar.getImages().isEmpty()) {
            announcementToUpdate.setMainImage(updatedCar.getImages().get(0));
        }
        String newDescriptionUrl = descriptionFileService.updateDescriptionFile(
                announcementToUpdate.getDescriptionUrl(),
                announcementDTOExtended.getDescriptionUrl(),
                announcementToUpdate.getCar().getId()
        );
        announcementToUpdate.setDescriptionUrl(newDescriptionUrl);
        if (!locationService.compareLocationToLocationDTO(announcementToUpdate.getLocation(),
                announcementDTOExtended.getLocationDTO())) {
            Location newLocation = locationService.getOrCreateLocationForAnnouncement(announcementDTOExtended.getLocationDTO());
            announcementToUpdate.setLocation(newLocation);
        }
        return announcementToUpdate;
    }

    private List<Announcement> getAnnouncementsWithFilters(AnnouncementFilterRequest request) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Announcement> query = criteriaBuilder.createQuery(Announcement.class);
        Root<Announcement> root = query.from(Announcement.class);

        if(request.getPage() <= 0) request.setPage(1);
        List<Predicate> predicates = prepareQuery(request, criteriaBuilder, root);

        if(!request.getOrder().isEmpty() && !request.getSort().isEmpty()) {
            String column = switch (request.getSort()) {
                case "price" -> "price";
                case "date" -> "addedDate";
                default -> "views";
            };
            Order orderQuery;
            if(request.getOrder().equals("desc")) {
                orderQuery = criteriaBuilder.desc(root.get(column));
            } else {
                orderQuery = criteriaBuilder.asc(root.get(column));
            }
            query.orderBy(orderQuery);
        }
        query.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query)
                .setFirstResult((request.getPage() - 1) * request.getLimit())
                .setMaxResults(request.getLimit())
                .getResultList();
    }

    private List<Predicate> prepareQuery(AnnouncementFilterRequest request, CriteriaBuilder criteriaBuilder, Root<Announcement> root) {
        List<Predicate> predicates = new ArrayList<>();

        addBrandPredicate(request.getBrand(), criteriaBuilder, root, predicates);
        addModelPredicate(request.getModel(), request.getBrand(), criteriaBuilder, root, predicates);
        addGenerationPredicate(request.getGeneration(), request.getModel(), criteriaBuilder, root, predicates);
        addPricePredicates(request.getPriceMin(), request.getPriceMax(), criteriaBuilder, root, predicates);
        addYearProductionPredicates(request.getYearProductionMin(), request.getYearProductionMax(), criteriaBuilder, root, predicates);
        addVinPredicate(request.isVin(), criteriaBuilder, root, predicates);
        addBodyworkTypePredicate(request.getBodyworkType(), criteriaBuilder, root, predicates);
        addFuelPredicate(request.getFuel(), criteriaBuilder, root, predicates);
        addMillagePredicate(request.getMillageMin(), request.getMillageMax(), criteriaBuilder, root, predicates);
        addCapacityPredicate(request.getCapacityMin(), request.getCapacityMax(), criteriaBuilder, root, predicates);
        addHorsepowerPredicate(request.getHorsepowerMin(), request.getHorsepowerMax(), criteriaBuilder, root, predicates);
        addDoorsPredicate(request.getDoorsMin(), request.getDoorsMax(), criteriaBuilder, root, predicates);
        addSeatsPredicate(request.getSeatsMin(), request.getSeatsMax(), criteriaBuilder, root, predicates);
        addTransmissionPredicate(request.getTransmission(), criteriaBuilder, root, predicates);
        addIsActivePredicate(criteriaBuilder, root, predicates);

        return predicates;
    }

    private void addBrandPredicate(String brand, CriteriaBuilder criteriaBuilder, Root<Announcement> root, List<Predicate> predicates) {
        if (brand != null && !brand.trim().isEmpty()) {
            Predicate brandNamePredicate = criteriaBuilder.equal(root.get("car").get("brand").get("name"), brand);
            predicates.add(brandNamePredicate);
        }
    }

    private void addModelPredicate(String model, String brand, CriteriaBuilder criteriaBuilder, Root<Announcement> root, List<Predicate> predicates) {
        if (model != null && !model.trim().isEmpty() && brand != null && !brand.trim().isEmpty()) {
            Predicate modelNamePredicate = criteriaBuilder.equal(root.get("car").get("model").get("name"), model);
            predicates.add(modelNamePredicate);
        }
    }

    private void addGenerationPredicate(String generation, String model, CriteriaBuilder criteriaBuilder, Root<Announcement> root, List<Predicate> predicates) {
        if (generation != null && !generation.trim().isEmpty() && model != null && !model.trim().isEmpty()) {
            Predicate generationNamePredicate = criteriaBuilder.equal(root.get("car").get("generation").get("name"), generation);
            predicates.add(generationNamePredicate);
        }
    }

    private void addPricePredicates(Float priceMin, Float priceMax, CriteriaBuilder criteriaBuilder, Root<Announcement> root, List<Predicate> predicates) {
        if (priceMin != null) {
            predicates.add(criteriaBuilder.greaterThan(root.get("price"), priceMin));
        }
        if (priceMax != null) {
            predicates.add(criteriaBuilder.lessThan(root.get("price"), priceMax));
        }
    }

    private void addYearProductionPredicates(Integer yearProductionMin, Integer yearProductionMax, CriteriaBuilder criteriaBuilder, Root<Announcement> root, List<Predicate> predicates) {
        if (yearProductionMin != null) {
            predicates.add(criteriaBuilder.greaterThan(root.get("car").get("yearProduction"), yearProductionMin));
        }
        if (yearProductionMax != null) {
            predicates.add(criteriaBuilder.lessThan(root.get("car").get("yearProduction"), yearProductionMax));
        }
    }

    private void addVinPredicate(boolean isVin, CriteriaBuilder criteriaBuilder, Root<Announcement> root, List<Predicate> predicates) {
        if (isVin) {
            predicates.add(criteriaBuilder.isNotNull(root.get("car").get("vin")));
        }
    }

    private void addBodyworkTypePredicate(String bodyworkType, CriteriaBuilder criteriaBuilder, Root<Announcement> root, List<Predicate> predicates) {
        if (bodyworkType != null && !bodyworkType.trim().isEmpty()) {
            Predicate bodyworkTypeNamePredicate = criteriaBuilder.equal(root.get("car").get("bodyworkType").get("name"), bodyworkType);
            predicates.add(bodyworkTypeNamePredicate);
        }
    }

    private void addFuelPredicate(String fuel, CriteriaBuilder criteriaBuilder, Root<Announcement> root, List<Predicate> predicates) {
        if (fuel != null && !fuel.trim().isEmpty()) {
            Predicate fuelNamePredicate = criteriaBuilder.equal(root.get("car").get("fuel").get("name"), fuel);
            predicates.add(fuelNamePredicate);
        }
    }

    private void addMillagePredicate(Integer millageMin, Integer millageMax, CriteriaBuilder criteriaBuilder, Root<Announcement> root, List<Predicate> predicates) {
        if (millageMin != null) {
            predicates.add(criteriaBuilder.greaterThan(root.get("car").get("millage"), millageMin));
        }
        if (millageMax != null) {
            predicates.add(criteriaBuilder.lessThan(root.get("car").get("millage"), millageMax));
        }
    }

    private void addCapacityPredicate(Integer capacityMin, Integer capacityMax, CriteriaBuilder criteriaBuilder, Root<Announcement> root, List<Predicate> predicates) {
        if (capacityMin != null) {
            predicates.add(criteriaBuilder.greaterThan(root.get("car").get("engine").get("capacity"), capacityMin));
        }
        if (capacityMax != null) {
            predicates.add(criteriaBuilder.lessThan(root.get("car").get("engine").get("capacity"), capacityMax));
        }
    }

    private void addHorsepowerPredicate(Integer horsepowerMin, Integer horsepowerMax, CriteriaBuilder criteriaBuilder, Root<Announcement> root, List<Predicate> predicates) {
        if (horsepowerMin != null) {
            predicates.add(criteriaBuilder.greaterThan(root.get("car").get("engine").get("horsepower"), horsepowerMin));
        }
        if (horsepowerMax != null) {
            predicates.add(criteriaBuilder.lessThan(root.get("car").get("engine").get("horsepower"), horsepowerMax));
        }
    }

    private void addDoorsPredicate(Integer doorsMin, Integer doorsMax, CriteriaBuilder criteriaBuilder, Root<Announcement> root, List<Predicate> predicates) {
        if (doorsMin != null) {
            predicates.add(criteriaBuilder.greaterThan(root.get("car").get("doorCount"), doorsMin));
        }
        if (doorsMax != null) {
            predicates.add(criteriaBuilder.lessThan(root.get("car").get("doorCount"), doorsMax));
        }
    }

    private void addSeatsPredicate(Integer seatsMin, Integer seatsMax, CriteriaBuilder criteriaBuilder, Root<Announcement> root, List<Predicate> predicates) {
        if (seatsMin != null) {
            predicates.add(criteriaBuilder.greaterThan(root.get("car").get("seatCount"), seatsMin));
        }
        if (seatsMax != null) {
            predicates.add(criteriaBuilder.lessThan(root.get("car").get("seatCount"), seatsMax));
        }
    }

    private void addTransmissionPredicate(String transmission, CriteriaBuilder criteriaBuilder, Root<Announcement> root, List<Predicate> predicates) {
        if (transmission != null && !transmission.trim().isEmpty()) {
            Predicate bodyworkTypeNamePredicate = criteriaBuilder.equal(root.get("car").get("transmission").get("name"), transmission);
            predicates.add(bodyworkTypeNamePredicate);
        }
    }

    private void addIsActivePredicate(CriteriaBuilder criteriaBuilder, Root<Announcement> root, List<Predicate> predicates) {
        predicates.add(criteriaBuilder.isTrue(root.get("isActive")));
    }
}

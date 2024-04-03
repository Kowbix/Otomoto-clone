package org.example.otomotoclon.serivce.implementation;

import org.example.otomotoclon.dto.SubscribedCarDTO;
import org.example.otomotoclon.dto.SubscribedCarDTOExtended;
import org.example.otomotoclon.entity.SubscribedCar;
import org.example.otomotoclon.entity.User;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.repository.SubscribedCarRepository;
import org.example.otomotoclon.serivce.*;
import org.example.otomotoclon.translator.SubscribedCarMapper;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscribedCarServiceImpl implements SubscribedCarService {

    private final SubscribedCarRepository subscribedCarRepository;
    private final SubscribedCarMapper subscribedCarMapper;
    private final AuthenticationService authenticationService;
    private final BrandService brandService;
    private final ModelService modelService;
    private final GenerationService generationService;

    public SubscribedCarServiceImpl(SubscribedCarRepository subscribedCarRepository,
                                    SubscribedCarMapper subscribedCarMapper,
                                    AuthenticationService authenticationService,
                                    BrandService brandService,
                                    ModelService modelService,
                                    GenerationService generationService) {
        this.subscribedCarRepository = subscribedCarRepository;
        this.subscribedCarMapper = subscribedCarMapper;
        this.authenticationService = authenticationService;
        this.brandService = brandService;
        this.modelService = modelService;
        this.generationService = generationService;
    }

    @Override
    public void subscribeCar(String username, SubscribedCarDTO subscribedCarDTO) throws ObjectDontExistInDBException {
        if (!isExistsSubscription(username, subscribedCarDTO)) {
            SubscribedCar subscribedCarToSave = mapToSubscribeCar(username, subscribedCarDTO);
            subscribedCarRepository.save(subscribedCarToSave);
        }
    }

    @Override
    public void unsubscribeCar(long id, String username) throws ObjectDontExistInDBException, AuthenticationException {
        deleteSubscription(id, username);
    }

    @Override
    public List<SubscribedCarDTOExtended> getSubscriptionsByUsername(String username) {
        List<SubscribedCar> subscribedCars = subscribedCarRepository.findByUserUsername(username);
        return subscribedCars.stream()
                .map(
                        subscribedCarMapper::toDTOExtended
                ).collect(Collectors.toList());
    }

    @Override
    public List<SubscribedCar> getSubscriptionsBySubscribedCarDTO(SubscribedCarDTO subscribedCarDTO) {
        return null;
    }

    private boolean isExistsSubscription(String username, SubscribedCarDTO subscribedCarDTO) {
        String brand = subscribedCarDTO.getBrand();
        String model = subscribedCarDTO.getModel();
        String generation = subscribedCarDTO.getGeneration();

        return subscribedCarRepository
                .existsByBrandNameAndModelNameAndGenerationNameAndUserUsername(
                        brand,
                        model,
                        generation,
                        username
                );
    }

    private SubscribedCar mapToSubscribeCar(String username, SubscribedCarDTO subscribedCarDTO) throws ObjectDontExistInDBException {
        User user = authenticationService.getUserByUsername(username);
        SubscribedCar subscribedCar = new SubscribedCar();
        subscribedCar.setBrand(brandService.getBrandByName(subscribedCarDTO.getBrand()));
        subscribedCar.setModel(modelService.getModelByNameAndBrand(subscribedCarDTO.getModel(), subscribedCarDTO.getBrand()));
        if (subscribedCarDTO.getGeneration() != null) {
            subscribedCar.setGeneration(generationService.getGenerationByNameAndModelName(
                    subscribedCarDTO.getGeneration(),
                    subscribedCarDTO.getModel()));
        }
        subscribedCar.setUser(user);

        return subscribedCar;
    }

    private void deleteSubscription(long id, String username) throws ObjectDontExistInDBException, AuthenticationException {
        SubscribedCar subscriptionToDelete = subscribedCarRepository.findById(id).orElseThrow(
                () -> new ObjectDontExistInDBException("Subscription with id " + id + " does not exists")
        );
        if(!isSubscriptionBelongToUser(username, subscriptionToDelete)) {
            throw new AuthenticationException("Subscription does not belong to user");
        }
        subscribedCarRepository.delete(subscriptionToDelete);
    }

    private boolean isSubscriptionBelongToUser(String username, SubscribedCar subscribedCar) {
        return subscribedCar.getUser().getUsername().equals(username);
    }


}

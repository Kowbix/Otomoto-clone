package org.example.otomotoclon.serivce;

import org.example.otomotoclon.dto.SubscribedCarDTOExtended;
import org.example.otomotoclon.dto.SubscribedCarDTO;
import org.example.otomotoclon.entity.SubscribedCar;

import javax.naming.AuthenticationException;
import java.util.List;

public interface SubscribedCarService {

    void subscribeCar(String username, SubscribedCarDTO subscribedCarDTO);
    void unsubscribeCar(long id, String username) throws AuthenticationException;
    List<SubscribedCarDTOExtended> getSubscriptionsByUsername(String username);
    List<SubscribedCar> getAllSubscribedCar();

}

package org.example.otomotoclon.serivce;

import org.example.otomotoclon.dto.user.UserLoginDTO;
import org.example.otomotoclon.dto.user.UserRegisterDTO;

public interface AuthenticationService {

    void registerUser(UserRegisterDTO userRegisterDTO);
    String login(UserLoginDTO userLoginDTO);
}

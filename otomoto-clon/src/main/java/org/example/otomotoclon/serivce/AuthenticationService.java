package org.example.otomotoclon.serivce;

import org.example.otomotoclon.dto.user.UserLoginDTO;
import org.example.otomotoclon.dto.user.UserRegisterDTO;
import org.example.otomotoclon.entity.User;

public interface AuthenticationService {

    void registerUser(UserRegisterDTO userRegisterDTO);
    String login(UserLoginDTO userLoginDTO);
    User getUserByUsername(String username);
}

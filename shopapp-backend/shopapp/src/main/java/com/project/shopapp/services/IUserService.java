package com.project.shopapp.services;

import com.project.shopapp.dtos.users.request.UserRegistrationRequestDTO;
import com.project.shopapp.dtos.users.request.UserLoginDTO;
import com.project.shopapp.models.User;

public interface IUserService {
    Boolean createUser(UserRegistrationRequestDTO user);
    String login(UserLoginDTO userLoginDTO);
    User getUserById(int id);
    User updateUser(int id, UserRegistrationRequestDTO user);
    void deleteUser(int id);
}

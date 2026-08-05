package com.project.shopapp.services.impl;

import com.project.shopapp.exception.ResourceExistsException;
import org.springframework.stereotype.Service;

import com.project.shopapp.dtos.users.request.UserRegistrationRequestDTO;
import com.project.shopapp.dtos.users.request.UserLoginDTO;
import com.project.shopapp.exception.ResourceNotFoundException;
import com.project.shopapp.models.Role;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.IUserService;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public Boolean createUser(UserRegistrationRequestDTO userRegistrationRequestDTO) {
        String phoneNumber = userRegistrationRequestDTO.getPhoneNumber();
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new ResourceExistsException(String.format("Phone number already exists (%s)." , phoneNumber));
        }
        int roleId = userRegistrationRequestDTO.getRoleId();
        Role existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Role not found (ID: %d).", roleId)));
        User user = User.builder()
                .fullName(userRegistrationRequestDTO.getFullName())
                .phoneNumber(userRegistrationRequestDTO.getPhoneNumber())
                .address(userRegistrationRequestDTO.getAddress())
                .password(userRegistrationRequestDTO.getPassword())
                .dateOfBirth(userRegistrationRequestDTO.getDateOfBirth())
                .facebookAccountId(userRegistrationRequestDTO.getFacebookAccountId())
                .googleAccountId(userRegistrationRequestDTO.getGoogleAccountId())
                .role(existingRole)
                .build();
        userRepository.save(user);
        return true;
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {
        return null;
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User not found (ID: %d)", id)));
    }

    @Override
    @Transactional
    public User updateUser(int id, UserRegistrationRequestDTO user) {
        User existingUser = getUserById(id);

        int roleId = user.getRoleId();
        Role existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Role (ID: %d) not found.", roleId)));

        existingUser.setAddress(user.getAddress());
        existingUser.setDateOfBirth(user.getDateOfBirth());
        existingUser.setFacebookAccountId(user.getFacebookAccountId());
        existingUser.setFullName(user.getFullName());
        existingUser.setGoogleAccountId(user.getGoogleAccountId());
        existingUser.setPassword(user.getPassword());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setRole(existingRole);

        return userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

}

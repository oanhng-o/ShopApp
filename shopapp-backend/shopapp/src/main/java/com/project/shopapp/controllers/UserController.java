package com.project.shopapp.controllers;

import com.project.shopapp.dtos.ApiResponse;
import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.dtos.UserLoginDTO;
import com.project.shopapp.models.User;
import com.project.shopapp.services.impl.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody UserDTO userDTO) {
        if (!Objects.equals(userDTO.getPassword(), userDTO.getRetypePassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.badRequest("Password does not match."));
        }
        User user = userService.createUser(userDTO);
        return ResponseEntity.ok(ApiResponse.success("Register successfully", user));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok(ApiResponse.success("Login successfully", userService.login(userLoginDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable("id") int id, @RequestBody UserDTO userDTO) {
        User updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(ApiResponse.success(String.format("User updated (ID: %d)", id), updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success(String.format("User deleted (ID: %d)", id), null));
    }
}

package com.codebyarif.whatHappened.controllers;

import com.codebyarif.whatHappened.dto.requests.UserRequest;
import com.codebyarif.whatHappened.dto.responses.UserResponse;
import com.codebyarif.whatHappened.models.User;
import com.codebyarif.whatHappened.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser (@Valid @RequestBody User user) {
        userService.save(user);
        return new ResponseEntity("User created.", HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers () {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{userId}")
    public UserResponse getOneUser (@PathVariable Long userId) {
        return new UserResponse(userService.getById(userId));
    }

    @PutMapping("/users/{userId}")
    public UserResponse updateUser(@Valid @RequestBody UserRequest updatedUser, @PathVariable Long userId) {
        return new UserResponse(userService.updateUser(updatedUser, userId));
    }

    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }



}

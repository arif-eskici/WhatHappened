package com.codebyarif.whatHappened.services;

import com.codebyarif.whatHappened.dto.requests.UserRequest;
import com.codebyarif.whatHappened.dto.responses.UserResponse;
import com.codebyarif.whatHappened.errors.NotFoundException;
import com.codebyarif.whatHappened.models.User;
import com.codebyarif.whatHappened.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final FileService fileService;

    public UserService(UserRepository userRepository, FileService fileService) {
        this.userRepository = userRepository;
        this.fileService = fileService;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<UserResponse> getAllUsers() {
        List <User> list = userRepository.findAll();
       return list.stream().map(UserResponse:: new).collect(Collectors.toList());
    }

    public User getById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new NotFoundException(userId));
    }

    public User updateUser(UserRequest updatedUser, Long userId) {
        User userDB = getById(userId);
        userDB.setUsername(updatedUser.getUsername());
        userDB.setEmail(updatedUser.getEmail());
        userDB.setPassword(updatedUser.getPassword());
        return userRepository.save(userDB);
    }

    public String deleteUser(Long userId) {
        User userDB = getById(userId);
        fileService.deleteAllStoredFilesForUser(userDB);
        userRepository.delete(userDB);
        return "User with id " + userId + " has been deleted success.";
    }

}

package com.codebyarif.whatHappened.constraint;

import com.codebyarif.whatHappened.models.User;
import com.codebyarif.whatHappened.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserRepository userRepository;

    public UniqueUsernameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            return false;
        }
        return true;
    }
}

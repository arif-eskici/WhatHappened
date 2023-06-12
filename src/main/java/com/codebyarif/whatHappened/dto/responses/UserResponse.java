package com.codebyarif.whatHappened.dto.responses;

import com.codebyarif.whatHappened.models.User;
import lombok.Data;

@Data
public class UserResponse {

    private long userId;

    private String username;

    private String email;

    public UserResponse(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}

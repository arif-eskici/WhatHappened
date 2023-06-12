package com.codebyarif.whatHappened.dto.requests;

import com.codebyarif.whatHappened.constraint.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequest {

    @NotNull
    @UniqueUsername
    private String username;

    @NotNull
    @Email(message = "Email should be valid")
    @Pattern(regexp = "^(.+)@(.+)$", message = "{whatHappen.constraints.email.Pattern.message}")
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must have at least 1 uppercase, 1 lowercase letter and 1 number")
    private String password;

}

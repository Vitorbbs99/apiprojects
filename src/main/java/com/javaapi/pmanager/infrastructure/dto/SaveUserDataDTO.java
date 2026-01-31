package com.javaapi.pmanager.infrastructure.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SaveUserDataDTO(
        @NotNull(message = "E-mail cannot be empty")
        @Email(message ="E-mail is not valid")
        String email,
        @NotNull(message = "Name cannot be empty")
        @Size(min = 1, max = 80, message = "Invalid user name")
        String name,
        String password
) {}

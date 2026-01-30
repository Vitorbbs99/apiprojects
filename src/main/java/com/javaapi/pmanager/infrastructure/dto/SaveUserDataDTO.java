package com.javaapi.pmanager.infrastructure.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record SaveUserDataDTO(
        @NotNull(message = "E-mail cannot be empty")
        @Email(message ="E-mail is not valid")
        String email,
        String password
) {}

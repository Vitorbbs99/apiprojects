package com.javaapi.pmanager.infrastructure.dto;

import com.javaapi.pmanager.domain.entity.User;
import com.javaapi.pmanager.domain.model.UserRole;

public record UserDTO(String name, String email, UserRole roles) {
    public static UserDTO create(User user) {
        return new UserDTO(
                user.getName(),
                user.getEmail(),
                user.getRoles()
        );
    }
}

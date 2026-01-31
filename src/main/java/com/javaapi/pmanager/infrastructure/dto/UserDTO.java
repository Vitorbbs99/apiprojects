package com.javaapi.pmanager.infrastructure.dto;

import com.javaapi.pmanager.domain.entity.User;

public record UserDTO(String name, String email) {
    public static UserDTO create(User user) {
        return new UserDTO(
                user.getName(),
                user.getEmail()
        );
    }
}

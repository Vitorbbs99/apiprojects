package com.javaapi.pmanager.domain.applicationservice;

import com.javaapi.pmanager.domain.entity.Member;
import com.javaapi.pmanager.domain.entity.User;
import com.javaapi.pmanager.domain.exception.*;
import com.javaapi.pmanager.domain.model.ProjectStatus;
import com.javaapi.pmanager.domain.model.UserRole;
import com.javaapi.pmanager.domain.repository.UserRepository;
import com.javaapi.pmanager.infrastructure.dto.SaveUserDataDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(SaveUserDataDTO saveUserDataDTO) {
        if (existsUserWithEmail(saveUserDataDTO.email(), null)) {
            throw new DuplicateUserException(saveUserDataDTO.email());
        }
        String encryptedPassword = passwordEncoder.encode(saveUserDataDTO.password());

        User user = User
                .builder()
                .email(saveUserDataDTO.email())
                .name(saveUserDataDTO.name())
                .password(encryptedPassword)
                .roles(convertToUserRoles(saveUserDataDTO.role()))
                .build();

        userRepository.save(user);
        return user;
    }

    private UserRole convertToUserRoles (String rolesStr) {
        try {
            return UserRole.valueOf(rolesStr);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidUserRolesException(rolesStr);
        }
    }

    public User loadUserById(String userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional
    public User updateUser(String userId, SaveUserDataDTO saveUserDataDTO) {
        if (existsUserWithEmail(saveUserDataDTO.email(), userId)) {
            throw new DuplicateUserException(saveUserDataDTO.email());
        }
        String encryptedPassword = passwordEncoder.encode(saveUserDataDTO.password());

        User user = loadUserById(userId);

        user.setEmail(saveUserDataDTO.email());
        user.setName(saveUserDataDTO.name());
        user.setPassword(encryptedPassword);
        user.setRoles(convertToUserRoles(saveUserDataDTO.role()));

        return user;

    }

    private boolean existsUserWithEmail(String email, String idToExclude) {
        return userRepository
                .findByEmail(email)
                .filter(u -> !Objects.equals(u.getId(), idToExclude))
                .isPresent();
    }
}

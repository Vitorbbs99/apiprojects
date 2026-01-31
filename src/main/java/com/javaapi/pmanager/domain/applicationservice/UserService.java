package com.javaapi.pmanager.domain.applicationservice;

import com.javaapi.pmanager.domain.entity.Member;
import com.javaapi.pmanager.domain.entity.User;
import com.javaapi.pmanager.domain.exception.MemberNotFoundException;
import com.javaapi.pmanager.domain.exception.UserNotFoundException;
import com.javaapi.pmanager.domain.repository.UserRepository;
import com.javaapi.pmanager.infrastructure.dto.SaveUserDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(SaveUserDataDTO saveUserDataDTO) {
        String encryptedPassword = passwordEncoder.encode(saveUserDataDTO.password());

        User user = User
                .builder()
                .email(saveUserDataDTO.email())
                .name(saveUserDataDTO.name())
                .password(encryptedPassword)
                .build();

        userRepository.save(user);
        return user;
    }

    public User loadUserById(String userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}

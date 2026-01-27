package com.javaapi.pmanager.domain.applicationservice;

import com.javaapi.pmanager.domain.entity.User;
import com.javaapi.pmanager.domain.repository.UserRepository;
import com.javaapi.pmanager.infrastructure.dto.SaveUserDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public User createUser(SaveUserDataDTO saveUserDataDTO) {

        User user = User
                .builder()
                .email(saveUserDataDTO.email())
                .password(saveUserDataDTO.password())
                .build();

        userRepository.save(user);
        return user;
    }
}

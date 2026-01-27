package com.javaapi.pmanager.infrastructure.controller;

import com.javaapi.pmanager.domain.applicationservice.UserService;
import com.javaapi.pmanager.domain.entity.User;
import com.javaapi.pmanager.infrastructure.dto.SaveUserDataDTO;
import com.javaapi.pmanager.infrastructure.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static com.javaapi.pmanager.infrastructure.controller.RestConstants.PATH_REGISTER_USER;

@Controller
@RequestMapping(PATH_REGISTER_USER)
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class UserRegisterRestResource {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid SaveUserDataDTO saveUserDataDTO) {
        User user = userService.createUser(saveUserDataDTO);

        return ResponseEntity
                .created(URI.create(PATH_REGISTER_USER + "/" + user.getId()))
                .body(UserDTO.create(user));
    }
}

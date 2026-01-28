package com.javaapi.pmanager.infrastructure.controller;


import com.javaapi.pmanager.infrastructure.dto.LoginDTO;
import com.javaapi.pmanager.infrastructure.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.javaapi.pmanager.infrastructure.controller.RestConstants.PATH_LOGIN;

@Controller
@RequestMapping(PATH_LOGIN)
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class UserLoginRestResource {
    @Autowired
    private AuthenticationManager manager;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid LoginDTO data) {
        // 1. Cria o objeto com as credenciais
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());

        // 2. O manager tenta autenticar (vai no banco via UserDetailsService)
        // Se a senha estiver errada, ele lança uma Exception aqui automaticamente
        manager.authenticate(authenticationToken);

        // 3. Se chegou aqui, deu certo. O Spring mantém a sessão ativa.
        return ResponseEntity.ok("Usuário logado com sucesso!");
    }
}

package com.javaapi.pmanager.infrastructure.controller;


import com.javaapi.pmanager.infrastructure.dto.LoginDTO;
import com.javaapi.pmanager.infrastructure.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
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

    private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid LoginDTO data, HttpServletRequest request, HttpServletResponse response) {
        // Cria o objeto com as credenciais
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var authentication = manager.authenticate(authenticationToken);

        // Cria o contexto com a autenticação
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        // SALVA o contexto explicitamente na sessão (ESSENCIAL)
        securityContextRepository.saveContext(context, request, response);

        // O Spring mantém a sessão ativa.
        return ResponseEntity.ok("Usuário logado com sucesso!");
    }
}

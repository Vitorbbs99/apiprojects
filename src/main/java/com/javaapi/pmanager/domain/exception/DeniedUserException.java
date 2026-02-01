package com.javaapi.pmanager.domain.exception;

import com.javaapi.pmanager.infrastructure.exception.RequestException;

public class DeniedUserException extends RequestException {
    public DeniedUserException(String RoleStr) {
        super("InvalidUserRole", "Acesso negado: somente " + RoleStr + " podem deletar projetos");
    }
}

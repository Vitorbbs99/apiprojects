package com.javaapi.pmanager.domain.exception;

import com.javaapi.pmanager.infrastructure.exception.RequestException;

public class ApiKeyNotFoundException extends RequestException {
    public ApiKeyNotFoundException(String apiKeyId) {
        super("ApiKeyNotFound", "The api key was not found: " + apiKeyId);
    }
}

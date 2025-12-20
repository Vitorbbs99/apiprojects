package com.javaapi.pmanager.domain.exception;

import com.javaapi.pmanager.infrastructure.exception.RequestException;

public class ApiKeyExpiredException extends RequestException {
    public ApiKeyExpiredException(String apiKeyId) {
        super("ApiKeyExpired", "The api key expired: " + apiKeyId);
    }
}

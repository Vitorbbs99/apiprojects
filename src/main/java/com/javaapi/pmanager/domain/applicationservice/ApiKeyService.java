package com.javaapi.pmanager.domain.applicationservice;

import com.javaapi.pmanager.domain.document.ApiKey;
import com.javaapi.pmanager.domain.exception.ApiKeyExpiredException;
import com.javaapi.pmanager.domain.exception.ApiKeyNotFoundException;
import com.javaapi.pmanager.domain.repository.ApiKeyRepository;
import com.javaapi.pmanager.infrastructure.config.AppConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApiKeyService {
    private final ApiKeyRepository apiKeyRepository;
    private final AppConfigProperties props;

    public ApiKey createApiKey() {
        ApiKey apiKey = ApiKey
                .builder()
                .value(UUID.randomUUID().toString())
                .expiresWhen(
                        OffsetDateTime
                                .now()
                                .plusDays(props.getSecurity().getExpirationDays())
                                .toInstant()
                )
                .build();

        apiKeyRepository.save(apiKey);
        return apiKey;
    }

    public void revokeApiKey(String id) {
       ApiKey apiKey = loadApiKeyById(id);
       apiKey.setExpiresWhen(Instant.EPOCH);
       apiKeyRepository.save(apiKey);
    }

    public void validadeApiKey(String apiKey) {
        ApiKey apiKeyObj = loadApiKeyByValue(apiKey);

        if (apiKeyObj.isExpired(Instant.now())) {
            throw new ApiKeyExpiredException(apiKeyObj.getId());
        }
    }

    private ApiKey loadApiKeyById(String id) {
        return apiKeyRepository
                .findById(id)
                .orElseThrow(() -> new ApiKeyNotFoundException(id));
    }

    private ApiKey loadApiKeyByValue(String apiKey) {
        return apiKeyRepository
                .findByValue(apiKey)
                .orElseThrow(() -> new ApiKeyNotFoundException(apiKey));
    }

}

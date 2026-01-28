package com.javaapi.pmanager.infrastructure.security;

import com.javaapi.pmanager.domain.applicationservice.ApiKeyService;
import com.javaapi.pmanager.domain.exception.ApiKeyExpiredException;
import com.javaapi.pmanager.domain.exception.ApiKeyNotFoundException;
import com.javaapi.pmanager.domain.repository.UserRepository;
import com.javaapi.pmanager.infrastructure.config.AppConfigProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    private final ApiKeyService apiKeyService;

    private final AppConfigProperties props;

    private final static String AUTH_TOKEN_HEADER_NAME = "x-api-key";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = repository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return user;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);

        if(!Objects.equals(apiKey, props.getSecurity().getMasterApiKey())) {

            try {
                apiKeyService.validadeApiKey(apiKey);
            } catch (ApiKeyNotFoundException | ApiKeyExpiredException e) {
                throw new BadCredentialsException("API key is not valid: " + apiKey, e);
            }
        }

        return new ApiKeyAuthenticationToken(apiKey);
    }
}

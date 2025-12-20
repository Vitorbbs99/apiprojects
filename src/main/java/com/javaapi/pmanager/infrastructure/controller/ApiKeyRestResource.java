package com.javaapi.pmanager.infrastructure.controller;

import com.javaapi.pmanager.domain.applicationservice.ApiKeyService;
import com.javaapi.pmanager.domain.applicationservice.MemberService;
import com.javaapi.pmanager.domain.document.ApiKey;
import com.javaapi.pmanager.domain.entity.Member;
import com.javaapi.pmanager.infrastructure.dto.ApiKeyDTO;
import com.javaapi.pmanager.infrastructure.dto.MemberDTO;
import com.javaapi.pmanager.infrastructure.dto.SaveMemberDataDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.javaapi.pmanager.infrastructure.controller.RestConstants.PATH_API_KEYS;
import static com.javaapi.pmanager.infrastructure.controller.RestConstants.PATH_MEMBERS;

@RestController
@RequestMapping(PATH_API_KEYS)
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class ApiKeyRestResource {

    private final ApiKeyService apiKeyService;

    @PostMapping
    public ResponseEntity<ApiKeyDTO> createApiKey() {
        ApiKey apiKey = apiKeyService.createApiKey();

        return ResponseEntity
                .created(URI.create(PATH_API_KEYS + "/" + apiKey.getId()))
                .body(ApiKeyDTO.create(apiKey));
    }

    @PutMapping("{id}/revoke")
    public ResponseEntity<Void> revokeApiKey(@PathVariable("id") String id) {
        apiKeyService.revokeApiKey(id);
        return ResponseEntity.noContent().build();
    }
}

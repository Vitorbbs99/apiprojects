package com.javaapi.pmanager.infrastructure.dto;

public record FileResponseDTO(
        String fileName,
        String fileType,
        long size
) {
}

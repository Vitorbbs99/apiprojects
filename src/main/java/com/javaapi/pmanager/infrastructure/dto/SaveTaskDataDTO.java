package com.javaapi.pmanager.infrastructure.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SaveTaskDataDTO {

    @NotNull(message = "Title cannot be empty")
    private final String title;

    @NotNull(message = "Description cannot be empty")
    @Size(min = 1, max = 150, message = "Invalid description")
    private final String description;

    @NotNull(message = "Number of days cannot be empty")
    @Positive
    private final Integer numberOfDays;

    private final String status;

    private final String projectId;

    private final String memberId;
}

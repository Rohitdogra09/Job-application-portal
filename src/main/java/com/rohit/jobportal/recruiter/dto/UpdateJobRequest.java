package com.rohit.jobportal.recruiter.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateJobRequest(
        @NotBlank String title,
        @NotBlank String description,
        @NotBlank String location,
        @NotBlank String status //eg-> OPEN, CLOSED
) {
}

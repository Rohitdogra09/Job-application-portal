package com.rohit.jobportal.recruiter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateJobRequest (
        @NotBlank @Size(min=3, max=120) String title,
        @NotBlank @Size(min = 10, max=5000) String description,
        String location
){}



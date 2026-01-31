package com.rohit.jobportal.recruiter.dto;

import java.time.Instant;

public record JobResponse(
        Long id,
        String title,
        String description,
        String location,
        String status,
        Instant createdAt,
        RecruiterSummary createdBy
) {
}

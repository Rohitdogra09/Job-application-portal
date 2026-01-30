package com.rohit.jobportal.recruiter.dto;

import com.rohit.jobportal.entity.Job;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recruiter/jobs")
@RequiredArgsConstructor
public class RecruiterJobController {

    private final RecruiterJobService recruiterJobService;

    @PreAuthorize("hasAuthority('SCOPE_ROLE_RECRUITER')")
    @PostMapping
    public Job create(@Valid @RequestBody CreateJobRequest req,@AuthenticationPrincipal Jwt jwt){
        return recruiterJobService.createJob(req, jwt);
    }
}

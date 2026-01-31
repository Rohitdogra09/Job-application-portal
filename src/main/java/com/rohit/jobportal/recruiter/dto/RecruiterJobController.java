package com.rohit.jobportal.recruiter.dto;

import com.rohit.jobportal.entity.Job;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //List of jobs created by this recruiter
    @PreAuthorize("hasAuthority('SCOPE_ROLE_RECRUITER')")
    @GetMapping
    public List<Job> myJobs(@AuthenticationPrincipal Jwt jwt){
        return recruiterJobService.getMyJobs(jwt);
    }
}

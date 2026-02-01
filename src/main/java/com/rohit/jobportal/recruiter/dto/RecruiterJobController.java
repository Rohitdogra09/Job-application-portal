package com.rohit.jobportal.recruiter.dto;

import com.rohit.jobportal.entity.Job;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public List<JobResponse> myJobs(@AuthenticationPrincipal Jwt jwt){
        return recruiterJobService.getMyJobs(jwt);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_RECRUITER')")
    public JobResponse getOne(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt){
        return recruiterJobService.getMyJobById(id, jwt);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_RECRUITER')")
    public JobResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateJobRequest req,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return recruiterJobService.updateMyJob(id, req, jwt);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_RECRUITER')")
    public Map<String, Object> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt
    ){
        recruiterJobService.deleteMyJob(id, jwt);
        return Map.of("message", "Job deleted", "id", id);
    }
}

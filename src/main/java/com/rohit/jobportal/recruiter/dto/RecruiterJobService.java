package com.rohit.jobportal.recruiter.dto;

import com.rohit.jobportal.entity.Job;
import com.rohit.jobportal.entity.User;
import com.rohit.jobportal.repository.JobRepository;
import com.rohit.jobportal.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruiterJobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Transactional
    public Job createJob(CreateJobRequest req, Jwt jwt){

        String email = jwt.getSubject();  //sub== email in my token

        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(()->new IllegalArgumentException("User not found"+email));


        Job job= Job.builder()
                .title(req.title())
                .description(req.description())
                .location(req.location())
                .status("OPEN")
                .createdBy(recruiter)
                .build();

        return jobRepository.save(job);
    }

    // Get all the jobs created by logged in recruiter

    public List<JobResponse> getMyJobs(Jwt jwt) {
        String email = jwt.getSubject();

        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found " + email));

        return jobRepository.findByCreatedBy_IdOrderByIdDesc(recruiter.getId())
                .stream()
                .map(job -> new JobResponse(
                        job.getId(),
                        job.getTitle(),
                        job.getDescription(),
                        job.getLocation(),
                        job.getStatus(),
                        job.getCreatedAt(),
                        new RecruiterSummary(
                                recruiter.getId(),
                                recruiter.getEmail(),
                                recruiter.getFullName()
                        )
                ))
                .toList();
    }
}

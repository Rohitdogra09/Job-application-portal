package com.rohit.jobportal.recruiter.dto;

import com.rohit.jobportal.entity.Job;
import com.rohit.jobportal.entity.User;
import com.rohit.jobportal.repository.JobRepository;
import com.rohit.jobportal.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruiterJobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    //@Transactional used to manage database transcations automatically
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

    public JobResponse getMyJobById(Long jobId, Jwt jwt){
        String email = jwt.getSubject();

        Job job = jobRepository.findByIdAndCreatedBy_Email(jobId, email)
                .orElseThrow(()-> new IllegalArgumentException("Job not found or not owned by you"));

        User recruiter = job.getCreatedBy();

        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getLocation(),
                job.getStatus(),
                job.getCreatedAt(),
                new RecruiterSummary(recruiter.getId(), recruiter.getEmail(), recruiter.getFullName())
        );
    }

    @Transactional
    public JobResponse updateMyJob(Long jobId, UpdateJobRequest req, Jwt jwt) {
        String email = jwt.getSubject();

        Job job = jobRepository.findByIdAndCreatedBy_Email(jobId, email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));

        job.setTitle(req.title());
        job.setDescription(req.description());
        job.setLocation(req.location());
        job.setStatus(req.status());

        Job saved = jobRepository.save(job);

        User recruiter = saved.getCreatedBy();
        return new JobResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getLocation(),
                saved.getStatus(),
                saved.getCreatedAt(),
                new RecruiterSummary(recruiter.getId(), recruiter.getEmail(), recruiter.getFullName())
        );
    }

    @Transactional
    public void deleteMyJob(Long jobId, Jwt jwt){
        String email= jwt.getSubject();

        boolean owned = jobRepository.existsByIdAndCreatedBy_Email(jobId, email);
        if(!owned){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found");
        }
        jobRepository.deleteById(jobId); // it is safe because we just checked the ownership
        //Or we can also use : jobRepository.deleteByIdAndCreatedBy_Email(jobId, email);
    }

}

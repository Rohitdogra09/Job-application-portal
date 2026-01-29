package com.rohit.jobportal.repository;

import com.rohit.jobportal.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    Optional<JobApplication> findByJobIdAndUserId(Long jobId, Long userId);
    List<JobApplication> findAllByUserId(Long userId);
}

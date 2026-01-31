package com.rohit.jobportal.repository;
import com.rohit.jobportal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByCreatedBy_IdOrderByIdDesc(Long userId);
}


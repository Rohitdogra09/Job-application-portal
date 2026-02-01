package com.rohit.jobportal.repository;
import com.rohit.jobportal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByCreatedBy_IdOrderByIdDesc(Long userId);
    Optional<Job> findByIdAndCreatedBy_Email(Long id, String email);

    boolean existsByIdAndCreatedBy_Email(Long id, String email);

    void deleteByIdAndCreatedBy_Email(Long id, String email);
}


package com.rohit.jobportal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;



@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Builder
@Table(name="jobs")


public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    private String location;

    @Column(nullable = false, length=30)
    private String status; // open, Closed, Draft, in consideration tc

    @ManyToOne(optional = false)
    @JoinColumn(name ="created_by", nullable = false)
    private User createdBy;

    @Column(name ="created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate(){
        if(createdAt==null) createdAt=Instant.now();
    }
}

package com.resume.resume_builder.repository;

import com.resume.resume_builder.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository
        extends JpaRepository<Experience, Long> {
}
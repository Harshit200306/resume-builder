package com.resume.resume_builder.controller;

import com.resume.resume_builder.dto.ExperienceResponse;
import com.resume.resume_builder.entity.Experience;
import com.resume.resume_builder.service.ExperienceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ExperienceController {

    private final ExperienceService experienceService;

    public ExperienceController(
            ExperienceService experienceService) {

        this.experienceService = experienceService;
    }


    private ExperienceResponse mapToResponse(
            Experience experience) {

        return new ExperienceResponse(
                experience.getId(),
                experience.getJobTitle(),
                experience.getCompany(),
                experience.getLocation(),
                experience.getStartDate(),
                experience.getEndDate(),
                experience.getDescription()
        );
    }

    @PostMapping("/{resumeId}/experience")
    public ResponseEntity<ExperienceResponse> addExperience(
            @PathVariable Long resumeId,
           @Valid @RequestBody Experience experience) {

        Experience savedExperience =
                experienceService.addExperience(
                        resumeId,
                        experience
                );

        return ResponseEntity.ok(
                mapToResponse(savedExperience)
        );
    }


    @GetMapping("/{resumeId}/experience")
    public ResponseEntity<List<ExperienceResponse>> getExperience(
            @PathVariable Long resumeId) {

        List<ExperienceResponse> response =
                experienceService
                        .getExperienceByResumeId(resumeId)
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }


    @PutMapping("/experience/{experienceId}")
    public ResponseEntity<ExperienceResponse> updateExperience(
            @PathVariable Long experienceId,
           @Valid @RequestBody Experience experience) {

        Experience updatedExperience =
                experienceService.updateExperience(
                        experienceId,
                        experience
                );

        return ResponseEntity.ok(
                mapToResponse(updatedExperience)
        );
    }


    @DeleteMapping("/{resumeId}/experience/{experienceId}")
    public ResponseEntity<String> deleteExperience(
            @PathVariable Long resumeId,
            @PathVariable Long experienceId) {

        experienceService.deleteExperience(
                resumeId,
                experienceId
        );

        return ResponseEntity.ok(
                "Experience deleted successfully"
        );
    }
}
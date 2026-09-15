package com.resume.resume_builder.controller;

import com.resume.resume_builder.dto.EducationResponse;
import com.resume.resume_builder.entity.Education;
import com.resume.resume_builder.service.EducationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class EducationController {

    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    private EducationResponse mapToResponse(Education education) {

        return new EducationResponse(
                education.getId(),
                education.getDegree(),
                education.getInstitution(),
                education.getStartYear(),
                education.getEndYear(),
                education.getPercentage()
        );
    }

    @PostMapping("/{resumeId}/education")
    public ResponseEntity<EducationResponse> addEducation(
            @PathVariable Long resumeId,
            @Valid   @RequestBody Education education) {

        Education savedEducation =
                educationService.addEducation(
                        resumeId,
                        education
                );

        return ResponseEntity.ok(
                mapToResponse(savedEducation)
        );
    }

    @GetMapping("/{resumeId}/education")
    public ResponseEntity<List<EducationResponse>> getEducation(
            @PathVariable Long resumeId) {

        List<EducationResponse> response =
                educationService
                        .getEducationByResumeId(resumeId)
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/education/{educationId}")
    public ResponseEntity<EducationResponse> updateEducation(
            @PathVariable Long educationId,
            @Valid   @RequestBody Education education) {

        Education updatedEducation =
                educationService.updateEducation(
                        educationId,
                        education
                );

        return ResponseEntity.ok(
                mapToResponse(updatedEducation)
        );
    }

    @DeleteMapping("/{resumeId}/education/{educationId}")
    public ResponseEntity<String> deleteEducation(
            @PathVariable Long resumeId,
            @PathVariable Long educationId) {

        educationService.deleteEducation(
                resumeId,
                educationId
        );

        return ResponseEntity.ok(
                "Education deleted successfully"
        );
    }
}
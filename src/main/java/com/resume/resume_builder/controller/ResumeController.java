package com.resume.resume_builder.controller;

import com.resume.resume_builder.dto.ResumeResponse;
import com.resume.resume_builder.entity.Resume;
import com.resume.resume_builder.service.ResumeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    private ResumeResponse mapToResponse(Resume resume) {

        return new ResumeResponse(
                resume.getId(),
                resume.getTitle(),
                resume.getFullName(),
                resume.getEmail(),
                resume.getPhone(),
                resume.getAddress(),
                resume.getLinkedin(),
                resume.getGithub(),
                resume.getSummary()
        );
    }

    @PostMapping
    public ResponseEntity<ResumeResponse> createResume(
            @Valid @RequestBody Resume resume) {

        Resume savedResume =
                resumeService.createResume(resume);

        return ResponseEntity.ok(
                mapToResponse(savedResume)
        );
    }

    @GetMapping
    public ResponseEntity<List<ResumeResponse>> getAllResumes() {

        List<ResumeResponse> response =
                resumeService.getAllResumes()
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeResponse> getResume(
            @PathVariable Long id) {

        Resume resume =
                resumeService.getResumeById(id);

        return ResponseEntity.ok(
                mapToResponse(resume)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResumeResponse> updateResume(
            @PathVariable Long id,
            @Valid @RequestBody Resume resume) {

        Resume updatedResume =
                resumeService.updateResume(id, resume);

        return ResponseEntity.ok(
                mapToResponse(updatedResume)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResume(
            @PathVariable Long id) {

        resumeService.deleteResume(id);

        return ResponseEntity.ok(
                "Resume deleted successfully"
        );
    }
}


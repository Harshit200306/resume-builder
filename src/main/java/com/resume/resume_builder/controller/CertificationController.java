package com.resume.resume_builder.controller;

import com.resume.resume_builder.dto.CertificationResponse;
import com.resume.resume_builder.entity.Certification;
import com.resume.resume_builder.service.CertificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class CertificationController {

    private final CertificationService certificationService;

    public CertificationController(
            CertificationService certificationService) {

        this.certificationService = certificationService;
    }

    private CertificationResponse mapToResponse(
            Certification certification) {

        return new CertificationResponse(
                certification.getId(),
                certification.getCertificateName(),
                certification.getIssuingOrganization(),
                certification.getIssueDate(),
                certification.getCertificateUrl()
        );
    }

    @PostMapping("/{resumeId}/certification")
    public ResponseEntity<CertificationResponse> addCertification(
            @PathVariable Long resumeId,
            @Valid @RequestBody Certification certification) {

        Certification savedCertification =
                certificationService.addCertification(
                        resumeId,
                        certification
                );

        return ResponseEntity.ok(
                mapToResponse(savedCertification)
        );
    }

    @GetMapping("/{resumeId}/certification")
    public ResponseEntity<List<CertificationResponse>> getCertifications(
            @PathVariable Long resumeId) {

        List<CertificationResponse> response =
                certificationService
                        .getCertificationsByResumeId(resumeId)
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/certifications/{certificationId}")
    public ResponseEntity<CertificationResponse> updateCertification(
            @PathVariable Long certificationId,
            @Valid   @RequestBody Certification certification) {

        Certification updatedCertification =
                certificationService.updateCertification(
                        certificationId,
                        certification
                );

        return ResponseEntity.ok(
                mapToResponse(updatedCertification)
        );
    }

    @DeleteMapping("/{resumeId}/certification/{certificationId}")
    public ResponseEntity<String> deleteCertification(
            @PathVariable Long resumeId,
            @PathVariable Long certificationId) {

        certificationService.deleteCertification(
                resumeId,
                certificationId
        );

        return ResponseEntity.ok(
                "Certification deleted successfully"
        );
    }
}
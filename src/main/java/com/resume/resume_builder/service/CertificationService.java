package com.resume.resume_builder.service;

import com.resume.resume_builder.entity.Certification;
import com.resume.resume_builder.entity.Resume;
import com.resume.resume_builder.repository.CertificationRepository;
import org.springframework.stereotype.Service;
import com.resume.resume_builder.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class CertificationService {

    private final CertificationRepository certificationRepository;
    private final ResumeService resumeService;

    public CertificationService(
            CertificationRepository certificationRepository,
            ResumeService resumeService) {

        this.certificationRepository = certificationRepository;
        this.resumeService = resumeService;
    }

    public Certification addCertification(
            Long resumeId,
            Certification certification) {

        Resume resume =
                resumeService.getResumeById(resumeId);

        certification.setResume(resume);

        return certificationRepository.save(certification);
    }

    public List<Certification> getCertificationsByResumeId(
            Long resumeId) {

        Resume resume =
                resumeService.getResumeById(resumeId);

        return resume.getCertifications();
    }

    public Certification updateCertification(
            Long certificationId,
            Certification updatedCertification) {

        Certification existingCertification =
                certificationRepository.findById(certificationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Certification not found"
                                )
                        );

        resumeService.getResumeById(
                existingCertification.getResume().getId()
        );

        existingCertification.setCertificateName(
                updatedCertification.getCertificateName()
        );

        existingCertification.setIssuingOrganization(
                updatedCertification.getIssuingOrganization()
        );

        existingCertification.setIssueDate(
                updatedCertification.getIssueDate()
        );

        existingCertification.setCertificateUrl(
                updatedCertification.getCertificateUrl()
        );

        return certificationRepository.save(
                existingCertification
        );
    }

    public void deleteCertification(
            Long resumeId,
            Long certificationId) {

        Certification certification =
                certificationRepository.findById(certificationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Certification not found"
                                )
                        );

        if (!certification.getResume().getId().equals(resumeId)) {
            throw new ResourceNotFoundException(
                    "Certification not found for this resume"
            );
        }

        resumeService.getResumeById(resumeId);

        certificationRepository.delete(certification);
    }
}
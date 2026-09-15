package com.resume.resume_builder.service;

import com.resume.resume_builder.entity.Education;
import com.resume.resume_builder.entity.Resume;
import com.resume.resume_builder.repository.EducationRepository;
import org.springframework.stereotype.Service;
import com.resume.resume_builder.exception.ResourceNotFoundException;
import java.util.List;

@Service
public class EducationService {

    private final EducationRepository educationRepository;
    private final ResumeService resumeService;

    public EducationService(
            EducationRepository educationRepository,
            ResumeService resumeService) {

        this.educationRepository = educationRepository;
        this.resumeService = resumeService;
    }

    public Education addEducation(
            Long resumeId,
            Education education) {

        Resume resume =
                resumeService.getResumeById(resumeId);

        education.setResume(resume);

        return educationRepository.save(education);
    }

    public List<Education> getEducationByResumeId(
            Long resumeId) {

        Resume resume =
                resumeService.getResumeById(resumeId);

        return resume.getEducations();
    }

    public Education updateEducation(
            Long educationId,
            Education updatedEducation) {

        Education existingEducation =
                educationRepository.findById(educationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Education not found"
                                )
                        );

        // Security check
        resumeService.getResumeById(
                existingEducation.getResume().getId()
        );

        existingEducation.setDegree(
                updatedEducation.getDegree()
        );

        existingEducation.setInstitution(
                updatedEducation.getInstitution()
        );

        existingEducation.setStartYear(
                updatedEducation.getStartYear()
        );

        existingEducation.setEndYear(
                updatedEducation.getEndYear()
        );

        existingEducation.setPercentage(
                updatedEducation.getPercentage()
        );

        return educationRepository.save(existingEducation);
    }

    public void deleteEducation(
            Long resumeId,
            Long educationId) {

        Education education =
                educationRepository.findById(educationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Education not found"
                                )
                        );

        if (!education.getResume().getId().equals(resumeId)) {
            throw new ResourceNotFoundException(
                    "Education not found for this resume"
            );
        }

        resumeService.getResumeById(resumeId);

        educationRepository.delete(education);
    }
}
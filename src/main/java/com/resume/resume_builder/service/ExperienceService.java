package com.resume.resume_builder.service;

import com.resume.resume_builder.entity.Experience;
import com.resume.resume_builder.entity.Resume;
import com.resume.resume_builder.repository.ExperienceRepository;
import org.springframework.stereotype.Service;
import com.resume.resume_builder.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ResumeService resumeService;

    public ExperienceService(
            ExperienceRepository experienceRepository,
            ResumeService resumeService) {

        this.experienceRepository = experienceRepository;
        this.resumeService = resumeService;
    }


    public Experience addExperience(
            Long resumeId,
            Experience experience) {

        Resume resume =
                resumeService.getResumeById(resumeId);

        experience.setResume(resume);

        return experienceRepository.save(experience);
    }


    public List<Experience> getExperienceByResumeId(
            Long resumeId) {

        Resume resume =
                resumeService.getResumeById(resumeId);

        return resume.getExperiences();
    }


    public Experience updateExperience(
            Long experienceId,
            Experience updatedExperience) {

        Experience existingExperience =
                experienceRepository.findById(experienceId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Experience not found"
                                )
                        );


        resumeService.getResumeById(
                existingExperience.getResume().getId()
        );

        existingExperience.setJobTitle(
                updatedExperience.getJobTitle()
        );

        existingExperience.setCompany(
                updatedExperience.getCompany()
        );

        existingExperience.setLocation(
                updatedExperience.getLocation()
        );

        existingExperience.setStartDate(
                updatedExperience.getStartDate()
        );

        existingExperience.setEndDate(
                updatedExperience.getEndDate()
        );

        existingExperience.setDescription(
                updatedExperience.getDescription()
        );

        return experienceRepository.save(existingExperience);
    }


    public void deleteExperience(
            Long resumeId,
            Long experienceId) {

        Experience experience =
                experienceRepository.findById(experienceId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Experience not found"
                                )
                        );

        if (!experience.getResume().getId().equals(resumeId)) {
            throw new ResourceNotFoundException(
                    "Experience not found for this resume"
            );
        }

        resumeService.getResumeById(resumeId);

        experienceRepository.delete(experience);
    }
}
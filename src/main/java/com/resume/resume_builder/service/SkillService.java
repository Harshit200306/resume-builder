package com.resume.resume_builder.service;

import com.resume.resume_builder.entity.Resume;
import com.resume.resume_builder.entity.Skill;
import com.resume.resume_builder.repository.SkillRepository;
import org.springframework.stereotype.Service;
import com.resume.resume_builder.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;
    private final ResumeService resumeService;

    public SkillService(
            SkillRepository skillRepository,
            ResumeService resumeService) {

        this.skillRepository = skillRepository;
        this.resumeService = resumeService;
    }

    public Skill addSkill(
            Long resumeId,
            Skill skill) {

        Resume resume =
                resumeService.getResumeById(resumeId);

        skill.setResume(resume);

        return skillRepository.save(skill);
    }

    public List<Skill> getSkillsByResumeId(
            Long resumeId) {

        Resume resume =
                resumeService.getResumeById(resumeId);

        return resume.getSkills();
    }

    public Skill updateSkill(
            Long skillId,
            Skill updatedSkill) {

        Skill existingSkill =
                skillRepository.findById(skillId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Skill not found"
                                )
                        );

        resumeService.getResumeById(
                existingSkill.getResume().getId()
        );

        existingSkill.setSkillName(
                updatedSkill.getSkillName()
        );

        existingSkill.setSkillLevel(
                updatedSkill.getSkillLevel()
        );

        return skillRepository.save(existingSkill);
    }

    public void deleteSkill(
            Long resumeId,
            Long skillId) {

        Skill skill =
                skillRepository.findById(skillId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Skill not found"
                                )
                        );

        if (!skill.getResume().getId().equals(resumeId)) {
            throw new ResourceNotFoundException(
                    "Skill not found for this resume"
            );
        }

        resumeService.getResumeById(resumeId);

        skillRepository.delete(skill);
    }
}
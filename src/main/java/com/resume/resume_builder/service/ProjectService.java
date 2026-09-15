package com.resume.resume_builder.service;

import com.resume.resume_builder.entity.Project;
import com.resume.resume_builder.entity.Resume;
import com.resume.resume_builder.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import com.resume.resume_builder.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ResumeService resumeService;

    public ProjectService(
            ProjectRepository projectRepository,
            ResumeService resumeService) {

        this.projectRepository = projectRepository;
        this.resumeService = resumeService;
    }


    public Project addProject(
            Long resumeId,
            Project project) {

        Resume resume =
                resumeService.getResumeById(resumeId);

        project.setResume(resume);

        return projectRepository.save(project);
    }


    public List<Project> getProjectsByResumeId(
            Long resumeId) {

        Resume resume =
                resumeService.getResumeById(resumeId);

        return resume.getProjects();
    }


    public Project updateProject(
            Long projectId,
            Project updatedProject) {

        Project existingProject =
                projectRepository.findById(projectId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Project not found"
                                )
                        );


        resumeService.getResumeById(
                existingProject.getResume().getId()
        );

        existingProject.setProjectName(
                updatedProject.getProjectName()
        );

        existingProject.setTechnologies(
                updatedProject.getTechnologies()
        );

        existingProject.setProjectUrl(
                updatedProject.getProjectUrl()
        );

        existingProject.setDescription(
                updatedProject.getDescription()
        );

        return projectRepository.save(existingProject);
    }


    public void deleteProject(
            Long resumeId,
            Long projectId) {

        Project project =
                projectRepository.findById(projectId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Project not found"
                                )
                        );

        if (!project.getResume().getId().equals(resumeId)) {
            throw new ResourceNotFoundException(
                    "Project not found for this resume"
            );
        }

        resumeService.getResumeById(resumeId);

        projectRepository.delete(project);
    }
}
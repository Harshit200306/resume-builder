package com.resume.resume_builder.controller;

import com.resume.resume_builder.dto.ProjectResponse;
import com.resume.resume_builder.entity.Project;
import com.resume.resume_builder.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(
            ProjectService projectService) {

        this.projectService = projectService;
    }


    private ProjectResponse mapToResponse(
            Project project) {

        return new ProjectResponse(
                project.getId(),
                project.getProjectName(),
                project.getTechnologies(),
                project.getProjectUrl(),
                project.getDescription()
        );
    }


    @PostMapping("/{resumeId}/project")
    public ResponseEntity<ProjectResponse> addProject(
            @PathVariable Long resumeId,
            @Valid  @RequestBody Project project) {

        Project savedProject =
                projectService.addProject(
                        resumeId,
                        project
                );

        return ResponseEntity.ok(
                mapToResponse(savedProject)
        );
    }


    @GetMapping("/{resumeId}/project")
    public ResponseEntity<List<ProjectResponse>> getProjects(
            @PathVariable Long resumeId) {

        List<ProjectResponse> response =
                projectService
                        .getProjectsByResumeId(resumeId)
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }


    @PutMapping("/projects/{projectId}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable Long projectId,
            @Valid  @RequestBody Project project) {

        Project updatedProject =
                projectService.updateProject(
                        projectId,
                        project
                );

        return ResponseEntity.ok(
                mapToResponse(updatedProject)
        );
    }


    @DeleteMapping("/{resumeId}/project/{projectId}")
    public ResponseEntity<String> deleteProject(
            @PathVariable Long resumeId,
            @PathVariable Long projectId) {

        projectService.deleteProject(
                resumeId,
                projectId
        );

        return ResponseEntity.ok(
                "Project deleted successfully"
        );
    }
}
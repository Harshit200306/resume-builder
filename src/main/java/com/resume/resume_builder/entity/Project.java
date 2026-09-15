package com.resume.resume_builder.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Project name is required")
    private String projectName;

    @NotBlank(message = "Technologies are required")
    private String technologies;

    @NotBlank(message = "Project URL is required")
    private String projectUrl;

    @Column(length = 2000)
    @NotBlank(message = "Description is required")
    private String description;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
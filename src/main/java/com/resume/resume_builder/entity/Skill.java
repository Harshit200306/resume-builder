package com.resume.resume_builder.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Skill name is required")
    private String skillName;

    @NotBlank(message = "Skill level is required")
    private String skillLevel;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
package com.resume.resume_builder.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "educations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Degree is required")
    private String degree;

    @NotBlank(message = "Institution is required")
    private String institution;

    @NotBlank(message = "Start year is required")
    private String startYear;

    @NotBlank(message = "End year is required")
    private String endYear;

    @NotBlank(message = "Percentage is required")
    private String percentage;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
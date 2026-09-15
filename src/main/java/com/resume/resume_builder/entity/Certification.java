package com.resume.resume_builder.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "certifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Certificate name is required")
    private String certificateName;

    @NotBlank(message = "Issuing organization is required")
    private String issuingOrganization;

    @NotBlank(message = "Issue date is required")
    private String issueDate;

    @NotBlank(message = "Certificate URL is required")
    private String certificateUrl;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
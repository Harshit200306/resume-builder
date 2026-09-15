package com.resume.resume_builder.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "resumes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;

    private String address;

    private String linkedin;

    private String github;

    @Column(length = 2000)
    private String summary;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "resume")
    private List<Education> educations;

    @OneToMany(mappedBy = "resume")
    private List<Experience> experiences;

    @OneToMany(mappedBy = "resume")
    private List<Project> projects;

    @OneToMany(mappedBy = "resume")
    private List<Skill> skills;

    @OneToMany(mappedBy = "resume")
    private List<Certification> certifications;
}

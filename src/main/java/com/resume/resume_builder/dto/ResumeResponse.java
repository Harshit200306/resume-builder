package com.resume.resume_builder.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumeResponse {

    private Long id;

    private String title;

    private String fullName;

    private String email;

    private String phone;

    private String address;

    private String linkedin;

    private String github;

    private String summary;
}
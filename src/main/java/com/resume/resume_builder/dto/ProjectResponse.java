package com.resume.resume_builder.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {

    private Long id;
    private String projectName;
    private String technologies;
    private String projectUrl;
    private String description;
}
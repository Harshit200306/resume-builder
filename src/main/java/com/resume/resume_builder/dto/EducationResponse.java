package com.resume.resume_builder.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EducationResponse {

    private Long id;
    private String degree;
    private String institution;
    private String startYear;
    private String endYear;
    private String percentage;
}
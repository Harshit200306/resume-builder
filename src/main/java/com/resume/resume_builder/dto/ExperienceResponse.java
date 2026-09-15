package com.resume.resume_builder.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceResponse {

    private Long id;
    private String jobTitle;
    private String company;
    private String location;
    private String startDate;
    private String endDate;
    private String description;
}
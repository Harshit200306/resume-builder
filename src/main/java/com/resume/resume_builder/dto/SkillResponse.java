package com.resume.resume_builder.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkillResponse {

    private Long id;
    private String skillName;
    private String skillLevel;
}
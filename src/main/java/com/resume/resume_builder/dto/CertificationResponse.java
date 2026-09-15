package com.resume.resume_builder.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificationResponse {

    private Long id;
    private String certificateName;
    private String issuingOrganization;
    private String issueDate;
    private String certificateUrl;
}
package com.resume.resume_builder.exception;

public class ResumeAccessDeniedException extends RuntimeException {

    public ResumeAccessDeniedException(String message) {
        super(message);
    }
}
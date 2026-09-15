package com.resume.resume_builder.exception;

public class ResumeNotFoundException
        extends RuntimeException {

    public ResumeNotFoundException(String message) {
        super(message);
    }
}
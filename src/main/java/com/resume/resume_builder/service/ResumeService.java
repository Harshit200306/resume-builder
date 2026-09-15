package com.resume.resume_builder.service;

import com.resume.resume_builder.entity.Resume;
import com.resume.resume_builder.exception.ResumeNotFoundException;
import com.resume.resume_builder.repository.ResumeRepository;
import org.springframework.stereotype.Service;
import com.resume.resume_builder.repository.UserRepository;
import com.resume.resume_builder.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.resume.resume_builder.exception.ResumeAccessDeniedException;
import com.resume.resume_builder.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public ResumeService(
            ResumeRepository resumeRepository,
            UserRepository userRepository) {

        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
    }
    public Resume createResume(Resume resume) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        resume.setUser(user);

        return resumeRepository.save(resume);
    }

    public List<Resume> getAllResumes() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
        new ResourceNotFoundException("User not found"));

        return resumeRepository.findByUserId(user.getId());
    }

    public Resume getResumeById(Long id) {

        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() ->
                        new ResumeNotFoundException(
                                "Resume not found with id: " + id
                        )
                );

        User currentUser = getCurrentUser();

        if (!resume.getUser().getId().equals(currentUser.getId())) {
            throw new ResumeAccessDeniedException(
                    "You are not allowed to access this resume"
            );
        }

        return resume;
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
         new ResourceNotFoundException("User not found"));
    }

    public Resume updateResume(
            Long id,
            Resume updatedResume) {

        Resume existingResume = getResumeById(id);

        existingResume.setTitle(updatedResume.getTitle());
        existingResume.setFullName(updatedResume.getFullName());
        existingResume.setEmail(updatedResume.getEmail());
        existingResume.setPhone(updatedResume.getPhone());
        existingResume.setAddress(updatedResume.getAddress());
        existingResume.setLinkedin(updatedResume.getLinkedin());
        existingResume.setGithub(updatedResume.getGithub());
        existingResume.setSummary(updatedResume.getSummary());

        return resumeRepository.save(existingResume);
    }

    public void deleteResume(Long id) {

        Resume resume = getResumeById(id);

        resumeRepository.delete(resume);
    }
}
package com.resume.resume_builder.controller;

import com.resume.resume_builder.dto.SkillResponse;
import com.resume.resume_builder.entity.Skill;
import com.resume.resume_builder.service.SkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    private SkillResponse mapToResponse(Skill skill) {

        return new SkillResponse(
                skill.getId(),
                skill.getSkillName(),
                skill.getSkillLevel()
        );
    }

    @PostMapping("/{resumeId}/skill")
    public ResponseEntity<SkillResponse> addSkill(
            @PathVariable Long resumeId,
            @Valid @RequestBody Skill skill) {

        Skill savedSkill =
                skillService.addSkill(
                        resumeId,
                        skill
                );

        return ResponseEntity.ok(
                mapToResponse(savedSkill)
        );
    }

    @GetMapping("/{resumeId}/skill")
    public ResponseEntity<List<SkillResponse>> getSkills(
            @PathVariable Long resumeId) {

        List<SkillResponse> response =
                skillService
                        .getSkillsByResumeId(resumeId)
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/skills/{skillId}")
    public ResponseEntity<SkillResponse> updateSkill(
            @PathVariable Long skillId,
            @Valid  @RequestBody Skill skill) {

        Skill updatedSkill =
                skillService.updateSkill(
                        skillId,
                        skill
                );

        return ResponseEntity.ok(
                mapToResponse(updatedSkill)
        );
    }

    @DeleteMapping("/{resumeId}/skill/{skillId}")
    public ResponseEntity<String> deleteSkill(
            @PathVariable Long resumeId,
            @PathVariable Long skillId) {

        skillService.deleteSkill(
                resumeId,
                skillId
        );

        return ResponseEntity.ok(
                "Skill deleted successfully"
        );
    }
}
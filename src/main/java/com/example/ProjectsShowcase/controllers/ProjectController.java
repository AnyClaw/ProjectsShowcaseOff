package com.example.ProjectsShowcase.controllers;

import com.example.ProjectsShowcase.models.ProjectFullInfo;
import com.example.ProjectsShowcase.repositories.ProjectRepository;
import com.example.ProjectsShowcase.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Secured({"ROLE_USER", "ROLE_ADMIN"})
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectRepository projectRepository;

    // заявка на добавление нового проекта
    @PostMapping("/request")
    public ResponseEntity<ProjectFullInfo> createProject(@RequestBody ProjectFullInfo project) {
        projectRepository.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    // удаление проекта
    @PostMapping("/delete/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
        projectRepository.delete(projectRepository.findById(projectId).get());
        return ResponseEntity.status(HttpStatus.OK).body("project delete");
    }
}

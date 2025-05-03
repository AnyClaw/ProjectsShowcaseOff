package com.example.ProjectsShowcase.controllers;

import com.example.ProjectsShowcase.models.ProjectFullInfo;
import com.example.ProjectsShowcase.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectRepository projectRepository;

    // активные проекты
    @GetMapping("/active")
    public ResponseEntity<Iterable<ProjectFullInfo>> findActiveProject() {
        return ResponseEntity.status(HttpStatus.OK).body(projectRepository.findActiveProjects());
    }

    // информация о проекте
    @GetMapping("/info/{projectId}")
    public ResponseEntity<ProjectFullInfo> getProjectInfo(@PathVariable Long projectId) {
        return ResponseEntity.status(HttpStatus.OK).body(projectRepository.findById(projectId).get());
    }

    // заявка на добавление нового проекта
    @Secured("ROLE_ADMIN")
    @PostMapping("/request")
    public ResponseEntity<ResponseForm> createProject(@RequestBody ProjectFullInfo project) {
        projectRepository.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseForm("project created"));
    }

    // удаление проекта
    @Secured("ROLE_ADMIN")
    @PostMapping("/delete/{projectId}")
    public ResponseEntity<ResponseForm> deleteProject(@PathVariable Long projectId) {
        projectRepository.delete(projectRepository.findById(projectId).get());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseForm("project delete"));
    }
}

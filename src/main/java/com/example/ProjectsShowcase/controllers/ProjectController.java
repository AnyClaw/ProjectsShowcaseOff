package com.example.ProjectsShowcase.controllers;

import com.example.ProjectsShowcase.models.ProjectFullInfo;
import com.example.ProjectsShowcase.repositories.ProjectRepository;
import com.example.ProjectsShowcase.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    // заявка на добавление нового проекта
    @PostMapping("/project/request")
    public String createProject(@PathVariable ProjectFullInfo project) {
        projectRepository.save(project);
        return "saved new project";
    }

    // удаление проекта
    @PostMapping("/project/delete/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectRepository.delete(projectRepository.findById(id).get());
        return "delete project";
    }
}

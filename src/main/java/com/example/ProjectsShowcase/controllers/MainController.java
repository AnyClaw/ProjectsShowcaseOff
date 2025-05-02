package com.example.ProjectsShowcase.controllers;

import com.example.ProjectsShowcase.models.ProjectFullInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.ProjectsShowcase.repositories.UserRepository;
import com.example.ProjectsShowcase.services.Parser;
import com.example.ProjectsShowcase.repositories.ProjectRepository;
import com.example.ProjectsShowcase.repositories.TeamRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final PasswordEncoder encoder;
    private final Parser parser;

    @PostMapping("/load")
    public String loadDada() {
        parser.loadData();
        return "saved";
    }

    // бронирование проекта (current project)
    @PostMapping("/api/book/project/{projectId}/{teamlidId}")
    public ResponseEntity<?> addCurrentProject(@PathVariable Long projectId, @PathVariable Long teamlidId) {

        if (teamRepository.findByTeammates_id(teamlidId).getCurrentProject() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message: you have current project");
        }

        teamRepository.findByTeammates_id(teamlidId)
                .setCurrentProject(projectRepository.findById(projectId).get());

        teamRepository.save(teamRepository.findByTeammates_id(teamlidId));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Message: project booked");

    }

}
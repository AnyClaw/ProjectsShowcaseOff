package com.example.ProjectsShowcase.controllers;

import com.example.ProjectsShowcase.models.Team;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.ProjectsShowcase.repositories.UserRepository;
import com.example.ProjectsShowcase.services.Parser;
import com.example.ProjectsShowcase.repositories.ProjectRepository;
import com.example.ProjectsShowcase.repositories.TeamRepository;

import lombok.RequiredArgsConstructor;

import static com.example.ProjectsShowcase.services.MyUserDetailsService.getCurrentUserInfo;

@RestController
@RequiredArgsConstructor
@Secured({"ROLE_USER", "ROLE_ADMIN"})
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
    @PostMapping("/api/book/project/{projectId}")
    public ResponseEntity<ResponseForm> addCurrentProject(@PathVariable Long projectId) {
        Team team = teamRepository.findByTeammates_id(getCurrentUserInfo().getId());

        if (team.getCurrentProject() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseForm("you have current project"));
        }

        team.setCurrentProject(projectRepository.findById(projectId).get());

        teamRepository.save(team);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseForm("project booked"));
    }

}
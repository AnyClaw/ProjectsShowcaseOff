package com.example.ProjectsShowcase.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
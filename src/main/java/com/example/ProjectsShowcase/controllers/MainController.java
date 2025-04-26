package com.example.ProjectsShowcase.controllers;

import com.example.ProjectsShowcase.models.MyUser;
import com.example.ProjectsShowcase.models.ProjectFullInfo;
import com.example.ProjectsShowcase.models.Team;
import jakarta.websocket.server.PathParam;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.ProjectsShowcase.repositories.UserRepository;
import com.example.ProjectsShowcase.services.Parser;
import com.example.ProjectsShowcase.repositories.ProjectRepository;
import com.example.ProjectsShowcase.repositories.TeamRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

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


    // все проекты
    @GetMapping("/projects")
    public Iterable<ProjectFullInfo> findAllProject() {
        return projectRepository.findAll();
    }

    // информация о проекте
    @GetMapping("/projects/{id}")
    public ProjectFullInfo getProjectInfo(@PathVariable Long id) {
        return projectRepository.findById(id).get();
    }


    // все пользователи
    @GetMapping("/users")
    public Iterable<MyUser> getAllUser() {
        return userRepository.findAll();
    }

    // информация о пользователе
    @GetMapping("/users/{id}")
    public MyUser getUserInfo(@PathVariable Long id) {
        return userRepository.findById(id).get();
    }


    // все команды
    @GetMapping("/teams")
    public Iterable<Team> getAllTeam() {
        return teamRepository.findAll();
    }

    // информация о команде - по айди пользователя
    @GetMapping("/teams/{id}")
    public Team getTeamInfo(@PathVariable Long id) {
        return teamRepository.findByTeammates_id(id);
    }

    // создание команды
    @PostMapping("/teams/add/{id}/{name}")
    public String createTeam(@PathVariable Long id, @PathVariable String name,
                             @RequestParam(required = false) List<Long> teammatesId) {

        MyUser teamlid = userRepository.findById(id).get();
        Team team = new Team(null, name, teamlid, List.of(teamlid),
                null, null, null);

        teamRepository.save(team);
        return "saved";
    }

    // бронирование проекта (current project)
    @PostMapping("/projects/add/{id}/{teamlidId}")
    public String addCurrentProject(@PathVariable Long id, @PathVariable Long teamlidId) {

        teamRepository.findByTeammates_id(teamlidId)
                .setCurrentProject(projectRepository.findById(id).get());

        teamRepository.save(teamRepository.findByTeammates_id(teamlidId));
        return "saved project";
    }

    // удаление проекта из current project
    @PostMapping("/projects/refuse/{teamlidId}")
    public String delCurrentProject(@PathVariable Long teamlidId) {

        teamRepository.findByTeammates_id(teamlidId)
                .refuseProject("конец");

        teamRepository.save(teamRepository.findByTeammates_id(teamlidId));
        return "delete current project";
    }


}
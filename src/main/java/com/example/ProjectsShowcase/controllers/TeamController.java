package com.example.ProjectsShowcase.controllers;

import com.example.ProjectsShowcase.models.MyUser;
import com.example.ProjectsShowcase.models.Team;
import com.example.ProjectsShowcase.repositories.ProjectRepository;
import com.example.ProjectsShowcase.repositories.TeamRepository;
import com.example.ProjectsShowcase.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Secured({"ROLE_USER", "ROLE_ADMIN"})
@RequestMapping("/api/team")
public class TeamController {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    // все команды - позже убрать
    @GetMapping("/all")
    public Iterable<Team> getAllTeam() {
        return teamRepository.findAll();
    }

    // информация о команде по айди пользователя
    @GetMapping("/info/{id}")
    public Team getTeamInfo(@PathVariable Long id) {
        return teamRepository.findByTeammates_id(id);
    }

    // создание команды
    @PostMapping("/create/{id}/{name}")
    public ResponseEntity<Team> createTeam(@PathVariable Long id, @PathVariable String name,
                                     @RequestParam(required = false) ArrayList<Long> teammatesId) {

        MyUser teamlid = userRepository.findById(id).get();
        Team team = new Team(null, name, teamlid, new ArrayList<>(),
                null, null, null);
        team.addTeammate(teamlid);

        if (teammatesId != null) {
            for (Long teammateId : teammatesId) {
                team.addTeammate(userRepository.findById(teammateId).get());
            }
        }

        teamRepository.save(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(team);
    }

    // добавление участника в команду
    @PostMapping("/add/{teamlidId}/{teammateId}")
    public ResponseEntity<Team> addTeammates(@PathVariable Long teamlidId,
                             @PathVariable Long teammateId) {

        Team team = teamRepository.findByTeammates_id(teamlidId);
        team.addTeammate(userRepository.findById(teammateId).get());

        teamRepository.save(team);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(team);
    }

    // todo удаление команды - не работает
    @PostMapping("/delete/{teamlidId}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long teamlidId) {
        teamRepository.delete(teamRepository.findByTeammates_id(teamlidId));
        return ResponseEntity.status(HttpStatus.OK).body("team deleted");
    }

    // бронирование проекта (current project)
    @PostMapping("/current/{id}/{teamlidId}")
    public ResponseEntity<?> addCurrentProject(@PathVariable Long id, @PathVariable Long teamlidId) {

        if (teamRepository.findByTeammates_id(teamlidId).getCurrentProject() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("У вас уже есть текущий проект");
        }

        teamRepository.findByTeammates_id(teamlidId)
                .setCurrentProject(projectRepository.findById(id).get());

        teamRepository.save(teamRepository.findByTeammates_id(teamlidId));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(teamRepository.findByTeammates_id(teamlidId));

    }

    // завершение проекта (finish project) - не сохраняет без save
    @PostMapping("/finish/{teamlidId}")
    public ResponseEntity<Team> endCurrentProject(@PathVariable Long teamlidId) {

        teamRepository.findByTeammates_id(teamlidId).finishProject();

        teamRepository.save(teamRepository.findByTeammates_id(teamlidId));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(teamRepository.findByTeammates_id(teamlidId));
    }

    // отказ от проекта (из current project в refuse project) - не сохраняет без save
    @PostMapping("/refuse/{teamlidId}")
    public ResponseEntity<Team> delCurrentProject(@PathVariable Long teamlidId) {

        teamRepository.findByTeammates_id(teamlidId)
                .refuseProject("конец");

        teamRepository.save(teamRepository.findByTeammates_id(teamlidId));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(teamRepository.findByTeammates_id(teamlidId));
    }
}

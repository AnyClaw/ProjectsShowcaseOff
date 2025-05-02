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

    // все команды -  todo позже убрать
    @GetMapping("/all")
    public Iterable<Team> getAllTeam() {
        return teamRepository.findAll();
    }

    // информация о команде по айди пользователя
    @GetMapping("/info/{id}")
    public ResponseEntity<Team> getTeamInfo(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(teamRepository.findByTeammates_id(id));
    }

    // создание команды
    @PostMapping("/create/{id}/{name}")
    public ResponseEntity<?> createTeam(@PathVariable Long id, @PathVariable String name,
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
        return ResponseEntity.status(HttpStatus.CREATED).body("Message: team created");
    }

    // добавление участника в команду
    @PostMapping("/add/{teamlidId}/{teammateId}")
    public ResponseEntity<?> addTeammates(@PathVariable Long teamlidId,
                             @PathVariable Long teammateId) {

        Team team = teamRepository.findByTeammates_id(teamlidId);
        team.addTeammate(userRepository.findById(teammateId).get());

        teamRepository.save(team);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Message: teammate add");
    }

    // todo удаление команды - не работает
    @PostMapping("/delete/{teamlidId}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long teamlidId) {
        teamRepository.delete(teamRepository.findByTeammates_id(teamlidId));
        return ResponseEntity.status(HttpStatus.OK).body("Message: team deleted");
    }

    // завершение проекта (finish project)
    @PostMapping("/finish/{teamlidId}")
    public ResponseEntity<Team> endCurrentProject(@PathVariable Long teamlidId) {

        teamRepository.findByTeammates_id(teamlidId).finishProject();

        teamRepository.save(teamRepository.findByTeammates_id(teamlidId));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(teamRepository.findByTeammates_id(teamlidId));
    }

    // отказ от проекта (из current project в refuse project)
    @PostMapping("/refuse/{teamlidId}")
    public ResponseEntity<Team> delCurrentProject(@PathVariable Long teamlidId) {

        teamRepository.findByTeammates_id(teamlidId)
                .refuseProject("конец");

        teamRepository.save(teamRepository.findByTeammates_id(teamlidId));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(teamRepository.findByTeammates_id(teamlidId));
    }
}

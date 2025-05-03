package com.example.ProjectsShowcase.controllers;

import com.example.ProjectsShowcase.models.ForTeamCreated;
import com.example.ProjectsShowcase.models.MyUser;
import com.example.ProjectsShowcase.models.Team;
import com.example.ProjectsShowcase.repositories.TeamRepository;
import com.example.ProjectsShowcase.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static com.example.ProjectsShowcase.services.MyUserDetailsService.getCurrentUserInfo;

@RestController
@RequiredArgsConstructor
@Secured({"ROLE_USER", "ROLE_ADMIN"})
@RequestMapping("/api/team")
public class TeamController {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    // информация о команде по айди пользователя
    @GetMapping("/info")
    public ResponseEntity<Team> getTeamInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(teamRepository.findByTeammates_id(getCurrentUserInfo().getId()));
    }

    // создание команды
    @PostMapping("/create")
    public ResponseEntity<ResponseForm> createTeam(@RequestBody ForTeamCreated forTeamCreated) {
        MyUser teamlid = userRepository.findById(getCurrentUserInfo().getId()).get();

        Team team = new Team(null, forTeamCreated.getName(), teamlid, new ArrayList<>(),
                null, null, null);
        team.addTeammate(teamlid);

        ArrayList<Long> teammatesId = forTeamCreated.getTeammatesId();
        if (teammatesId != null) {
            for (Long teammateId : teammatesId) {
                team.addTeammate(userRepository.findById(teammateId).get());
            }
        }

        teamRepository.save(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseForm("team created"));
    }

    // добавление участника в команду
    @PostMapping("/add/{teammateId}")
    public ResponseEntity<ResponseForm> addTeammates(@PathVariable Long teammateId) {

        Team team = teamRepository.findByTeammates_id(getCurrentUserInfo().getId());
        team.addTeammate(userRepository.findById(teammateId).get());

        teamRepository.save(team);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseForm("teammate add"));
    }

    // todo удаление команды - не работает
    @PostMapping("/delete")
    public ResponseEntity<ResponseForm> deleteTeam() {
        teamRepository.delete(teamRepository.findByTeammates_id(getCurrentUserInfo().getId()));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseForm("team deleted"));
    }

    // завершение проекта (finish project)
    @PostMapping("/finish")
    public ResponseEntity<ResponseForm> endCurrentProject() {
        Team team = teamRepository.findByTeammates_id(getCurrentUserInfo().getId());
        team.finishProject();
        teamRepository.save(team);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseForm("project completed"));
    }

    // отказ от проекта (из current project в refuse project)
    @PostMapping("/refuse")
    public ResponseEntity<ResponseForm> delCurrentProject() {
        Team team = teamRepository.findByTeammates_id(getCurrentUserInfo().getId());
        team.refuseProject("причина отказа");
        teamRepository.save(team);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseForm("project refused"));
    }
}

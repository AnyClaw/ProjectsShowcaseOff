package com.example.ProjectsShowcase.controllers;

import com.example.ProjectsShowcase.models.MyUser;
import com.example.ProjectsShowcase.models.Team;
import com.example.ProjectsShowcase.repositories.ProjectRepository;
import com.example.ProjectsShowcase.repositories.TeamRepository;
import com.example.ProjectsShowcase.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class TeamController {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    // все команды - позже убрать
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

        if (teammatesId != null) {
            for (Long teammateId : teammatesId) {
                team.addTeammate(userRepository.findById(teammateId).get());
            }
        }

        teamRepository.save(team);
        return "new team create";
    }

    // добавление в команду
    @PostMapping("/teams/add/teammate/{teamlidId}/{teammateId}")
    public String addTeammates(@PathVariable Long teamlidId,
                             @PathVariable Long teammateId) {

        Team team = teamRepository.findByTeammates_id(teamlidId);
        team.addTeammate(userRepository.findById(teammateId).get());

        teamRepository.save(team);
        return "saved";
    }

    // удаление команды - не работает todo
    @PostMapping("/teams/delete/{teamlidId}")
    public String deleteTeam(@PathVariable Long teamlidId) {
        teamRepository.delete(teamRepository.findByTeammates_id(teamlidId));
        return "team deleted";
    }

    // бронирование проекта (current project)
    @PostMapping("/project/add/{id}/{teamlidId}")
    public String addCurrentProject(@PathVariable Long id, @PathVariable Long teamlidId) {

        teamRepository.findByTeammates_id(teamlidId)
                .setCurrentProject(projectRepository.findById(id).get());

        teamRepository.save(teamRepository.findByTeammates_id(teamlidId));
        return "saved project";
    }

    // завершение проекта (finish project)
    @PostMapping("/project/end/{id}/{teamlidId}")
    public String endCurrentProject(@PathVariable Long teamlidId) {

        teamRepository.findByTeammates_id(teamlidId).finishProject();

        teamRepository.save(teamRepository.findByTeammates_id(teamlidId));
        return "finished project";
    }

    // отказ от проекта (из current project в refuse project)
    @PostMapping("/project/refuse/{teamlidId}")
    public String delCurrentProject(@PathVariable Long teamlidId) {

        teamRepository.findByTeammates_id(teamlidId)
                .refuseProject("конец");

        teamRepository.save(teamRepository.findByTeammates_id(teamlidId));
        return "refuse of current project";
    }
}

package com.example.ProjectsShowcase.controllers;

import com.example.ProjectsShowcase.models.MyUser;
import com.example.ProjectsShowcase.models.ProjectFullInfo;
import com.example.ProjectsShowcase.models.Team;
import com.example.ProjectsShowcase.repositories.ProjectRepository;
import com.example.ProjectsShowcase.repositories.TeamRepository;
import com.example.ProjectsShowcase.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.ProjectsShowcase.services.MyUserDetailsService.getCurrentUserInfo;

@RestController
@RequiredArgsConstructor
@Secured({"ROLE_USER", "ROLE_ADMIN"})
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;

    // информация о пользователе
    @GetMapping("/user/info")
    public ResponseEntity<MyUser> getUserInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findById(getCurrentUserInfo().getId()).get());
    }

    // роль пользователя
    @GetMapping("/role/info")
    public ResponseEntity<String> getRoleInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(getCurrentUserInfo().getRole());
    }

    // проекты заказчика
    @GetMapping("/customer/project")
    public ResponseEntity<Iterable<ProjectFullInfo>> getCustomerProject() {
        Iterable<ProjectFullInfo> projects = projectRepository.findByCustomerId(getCurrentUserInfo().getId());
        return ResponseEntity.status(HttpStatus.OK).body(projects);
    }

    // принадлежность проекта пользователю todo (если проверка по заказчику)
    @GetMapping("/affiliation/{projectId}")
    public ResponseEntity<Boolean> getAffiliationProject(@PathVariable Long projectId) {
        ProjectFullInfo project = projectRepository.findById(projectId).get();
        MyUser user = getCurrentUserInfo();
        return ResponseEntity.status(HttpStatus.OK).body(user == project.getCustomer());
    }

    // принадлежность проекта пользователю todo (если проверка по команде) - ЕСЛИ ПО ЭТОМУ УБРАТЬ 1 в пути и в названии
    @GetMapping("/affiliation1/{projectId}")
    public ResponseEntity<Boolean> getAffiliationProject1(@PathVariable Long projectId) {
        ProjectFullInfo project = projectRepository.findById(projectId).get();
        Team user_team = teamRepository.findByTeammates_id(getCurrentUserInfo().getId());
        return ResponseEntity.status(HttpStatus.OK).body(project == user_team.getCurrentProject());
    }
}

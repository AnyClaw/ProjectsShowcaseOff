package com.example.ProjectsShowcase.controllers;

import com.example.ProjectsShowcase.models.MyUser;
import com.example.ProjectsShowcase.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class UserController {

    private final UserRepository userRepository;

    // все пользователи - позже убрать
    @GetMapping("/users")
    public Iterable<MyUser> getAllUser() {
        return userRepository.findAll();
    }

    // информация о пользователе
    @GetMapping("/users/{id}")
    public MyUser getUserInfo(@PathVariable Long id) {
        return userRepository.findById(id).get();
    }

}

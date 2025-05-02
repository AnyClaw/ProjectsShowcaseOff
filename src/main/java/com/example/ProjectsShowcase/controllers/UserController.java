package com.example.ProjectsShowcase.controllers;

import com.example.ProjectsShowcase.models.MyUser;
import com.example.ProjectsShowcase.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Secured({"ROLE_USER", "ROLE_ADMIN"})
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    // все пользователи - todo позже убрать
    @GetMapping("/all")
    public Iterable<MyUser> getAllUser() {
        return userRepository.findAll();
    }

    // информация о пользователе
    @GetMapping("/info/{id}")
    public ResponseEntity<MyUser> getUserInfo(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findById(id).get());
    }


}

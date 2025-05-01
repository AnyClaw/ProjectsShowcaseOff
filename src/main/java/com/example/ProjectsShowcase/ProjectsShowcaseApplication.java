package com.example.ProjectsShowcase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ProjectsShowcaseApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(ProjectsShowcaseApplication.class, args);
	}

	// запросы представлений
	@Override
	public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {
		viewControllerRegistry.addViewController("/").setViewName("index.html"); // все проекты
		viewControllerRegistry.addViewController("/login").setViewName("login.html");
		viewControllerRegistry.addViewController("/project/request").setViewName("project_request.html");
		viewControllerRegistry.addViewController("/user/profile").setViewName("user_profile.html");
		viewControllerRegistry.addViewController("/team/profile").setViewName("team_profile.html");
		viewControllerRegistry.addViewController("/team/request").setViewName("team_request.html");
  }
}

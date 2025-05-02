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
		viewControllerRegistry.addViewController("/").setViewName("index.html"); // для всех
		viewControllerRegistry.addViewController("/request").setViewName("project_request.html");
		viewControllerRegistry.addViewController("/profile").setViewName("user_profile.html");
		viewControllerRegistry.addViewController("/team").setViewName("team_profile.html");
		viewControllerRegistry.addViewController("/project/info/{id}").setViewName("project_info.html"); // для всех
		viewControllerRegistry.addViewController("/team/create").setViewName("team_create.html");
  }
}

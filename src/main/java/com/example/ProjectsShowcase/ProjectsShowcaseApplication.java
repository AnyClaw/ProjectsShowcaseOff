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
		viewControllerRegistry.addViewController("/").setViewName("index.html");
  }
}

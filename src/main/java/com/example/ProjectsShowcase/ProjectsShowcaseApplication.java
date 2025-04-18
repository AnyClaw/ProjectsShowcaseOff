package com.example.ProjectsShowcase;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.ProjectsShowcase.models.MyUser;
import com.example.ProjectsShowcase.models.ProjectFullInfo;
import com.example.ProjectsShowcase.models.ProjectFullInfo.Status;
import com.example.ProjectsShowcase.repositories.ProjectRepository;
import com.example.ProjectsShowcase.repositories.UserRepository;

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

	@Bean
    CommandLineRunner dataLoader(ProjectRepository projectRepository, UserRepository userRepository) {
    return new CommandLineRunner() {
      @Override
      public void run(String... args) throws Exception {
        MyUser customer = new MyUser(null, "Егор", "Москаленко", "Александрович", 
        "null", "1", "1234", "ROLE_USER, ROLE_ADMIN", "null");
        userRepository.save(customer);

        MyUser customer1 = new MyUser(null, "Влад", "Москаленко", "Александрович", 
        "null", "12", "1234", "ROLE_USER", "null");
        userRepository.save(customer1);

        MyUser customer2 = new MyUser(null, "Гриша", "Москаленко", "Александрович", 
        "null", "13", "1234", "ROLE_USER", "null");
        userRepository.save(customer2);

        MyUser customer3 = new MyUser(null, "Артём", "Москаленко", "Александрович", 
        "null", "14", "1234", "ROLE_USER", "null");
        userRepository.save(customer3);

        projectRepository.save(new ProjectFullInfo(
          null, "Витрина проектной деятельности ИУЦТ, УТБиИС", "УТБиИС", Status.FREE, 
          "Создать более удобный инструмент, реализующий необходимый функционал витрины проектной деятельности, для студентов, преподавателей и администрации ИУЦТ",
          "Нет единой автоматизированной системы для создания и обработки заявок, брони тем проектов с витрины, создания команд",
          "Существующее решение, созданное в рамках ИУЦТ, не является удобным инструментом ни для студентов, ни для администрации по причине полного отсутствия автоматизации. Аналоги из других ВУЗов используются в рамках их внутренней экосистемы и не предназначены для внедрения в другие университеты",
          "Прикладной проект", null, userRepository.findByName("Егор").get())
        );
        projectRepository.save(new ProjectFullInfo(
          null, "Витрина проектной деятельности ИУЦТ, УТБиИС", "УТБиИС", Status.FREE, 
          "Создать более удобный инструмент, реализующий необходимый функционал витрины проектной деятельности, для студентов, преподавателей и администрации ИУЦТ",
          "Нет единой автоматизированной системы для создания и обработки заявок, брони тем проектов с витрины, создания команд",
          "Существующее решение, созданное в рамках ИУЦТ, не является удобным инструментом ни для студентов, ни для администрации по причине полного отсутствия автоматизации. Аналоги из других ВУЗов используются в рамках их внутренней экосистемы и не предназначены для внедрения в другие университеты",
          "Прикладной проект", null, userRepository.findByName("Влад").get())
        );
        projectRepository.save(new ProjectFullInfo(
          null, "Витрина проектной деятельности ИУЦТ, УТБиИС", "УТБиИС", Status.FREE, 
          "Создать более удобный инструмент, реализующий необходимый функционал витрины проектной деятельности, для студентов, преподавателей и администрации ИУЦТ",
          "Нет единой автоматизированной системы для создания и обработки заявок, брони тем проектов с витрины, создания команд",
          "Существующее решение, созданное в рамках ИУЦТ, не является удобным инструментом ни для студентов, ни для администрации по причине полного отсутствия автоматизации. Аналоги из других ВУЗов используются в рамках их внутренней экосистемы и не предназначены для внедрения в другие университеты",
          "Прикладной проект", null, userRepository.findByName("Гриша").get())
        );
        projectRepository.save(new ProjectFullInfo(
          null, "Витрина проектной деятельности ИУЦТ, УТБиИС", "УТБиИС", Status.FREE, 
          "Создать более удобный инструмент, реализующий необходимый функционал витрины проектной деятельности, для студентов, преподавателей и администрации ИУЦТ",
          "Нет единой автоматизированной системы для создания и обработки заявок, брони тем проектов с витрины, создания команд",

          "Существующее решение, созданное в рамках ИУЦТ, не является удобным инструментом ни для студентов, ни для администрации по причине полного отсутствия автоматизации. Аналоги из других ВУЗов используются в рамках их внутренней экосистемы и не предназначены для внедрения в другие университеты",
          "Прикладной проект", null, userRepository.findByName("Артём").get())
        );
      }
      };
  }

}

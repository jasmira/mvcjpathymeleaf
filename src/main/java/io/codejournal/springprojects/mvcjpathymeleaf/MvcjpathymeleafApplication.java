package io.codejournal.springprojects.mvcjpathymeleaf;

import io.codejournal.springprojects.mvcjpathymeleaf.entity.Student;
import io.codejournal.springprojects.mvcjpathymeleaf.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.Arrays;
import java.util.UUID;

@SpringBootApplication
@EnableWebSecurity
public class MvcjpathymeleafApplication {

	@Autowired
	private StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(MvcjpathymeleafApplication.class, args);
	}

	@Bean
	public ApplicationRunner initializeStudents() {
		final Student defaultStudent1 = new Student(UUID.randomUUID(), "John", "Doe", 34);
		final Student defaultStudent2 = new Student(UUID.randomUUID(), "Linda", "Rostam", 26);
		final Student defaultStudent3 = new Student(UUID.randomUUID(), "Rafael", "Nadal", 44);

		return args -> studentRepository.saveAll(Arrays.asList(defaultStudent1, defaultStudent2, defaultStudent3));
	}
}

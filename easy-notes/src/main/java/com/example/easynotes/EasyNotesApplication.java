package com.example.easynotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EasyNotesApplication {

	public static void main(String[] args) {
		ApplicationContext acontext= SpringApplication.run(EasyNotesApplication.class, args);
	
	System.out.println("************");
	System.out.println(acontext.getBeanDefinitionNames().toString());

	}
}

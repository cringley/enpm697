package com.umd.sdlc.example.sdlc_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@PropertySource("classpath:application.properties")
@EntityScan("com.umd.sdlc.example.sdlc_project.models")
@EnableJpaRepositories("com.umd.sdlc.example.sdlc_project.query")
public class SdlcProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SdlcProjectApplication.class, args);
	}

}

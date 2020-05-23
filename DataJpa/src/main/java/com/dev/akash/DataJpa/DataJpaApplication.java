package com.dev.akash.DataJpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication

public class DataJpaApplication {


	@Bean public WebMvcConfigurer corsConfigurer() {

		return new WebMvcConfigurer() { 
			public void addCorsMapping(CorsRegistry registry) {
				registry.addMapping("/*").allowedHeaders("*").allowedMethods("*")
				.allowedOrigins("http://localhost:4200").allowCredentials(true); } };

	}


	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);



	}


	/*
	 * @Bean public WebMvcConfigurer corsConfigurer() { return new
	 * WebMvcConfigurer() {
	 * 
	 * @Override public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/*").allowedHeaders("*").allowedMethods("*").
	 * allowedOrigins("http://localhost:4200"); } }; }
	 */
}

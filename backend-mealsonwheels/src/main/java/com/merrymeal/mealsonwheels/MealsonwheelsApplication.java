package com.merrymeal.mealsonwheels;

import com.merrymeal.mealsonwheels.config.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
public class MealsonwheelsApplication {

	public static void main(String[] args) {

		SpringApplication.run(MealsonwheelsApplication.class, args);
	}

}

package com.springboot.login;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoginUserApplication {

	Dotenv dotenv = Dotenv.configure().load();


	public static void main(String[] args) {
		SpringApplication.run(LoginUserApplication.class, args);
	}


}

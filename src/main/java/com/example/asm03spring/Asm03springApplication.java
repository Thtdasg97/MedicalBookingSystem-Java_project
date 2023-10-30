package com.example.asm03spring;

import com.example.asm03spring.entity.Role;
import com.example.asm03spring.entity.User;
import com.example.asm03spring.service.RoleService;
import com.example.asm03spring.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Asm03springApplication {

	public static void main(String[] args) {
		SpringApplication.run(Asm03springApplication.class, args);
	}

}

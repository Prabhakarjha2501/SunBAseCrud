package com.jwtSecuritywithcrud;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jwtSecuritywithcrud.UserRepository.UserRepository;
import com.jwtSecuritywithcrud.model.Role;
import com.jwtSecuritywithcrud.model.User;

import ch.qos.logback.classic.Logger;

@SpringBootApplication
public class JwtwithcrudApplication implements CommandLineRunner {

	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(JwtwithcrudApplication.class);

	@Autowired
	private UserRepository  UserRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(JwtwithcrudApplication.class, args);
	}
	
	public void run (String ...args) {
		User adminAccount=UserRepository.findByRole(Role.ADMIN);
		
		if(null==adminAccount) {
			User user= new User();
			
			user.setEmail("admin@gmail.com");
			user.setFirstname("admin");
			user.setSecondname("admin");
			user.setRole(Role.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			
			UserRepository.save(user);
			logger.info("added data manually");
		}
		else {
			logger.info("this is not added bydefault");
		}
	}

}

package com.iaschowrai.JWT;

import com.iaschowrai.JWT.entities.Role;
import com.iaschowrai.JWT.entities.User;
import com.iaschowrai.JWT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// Spring Boot annotation to declare this class as the main application class
@SpringBootApplication
// Implementing CommandLineRunner allows running code after the application context has been loaded
public class SpringBootJwtApplication implements CommandLineRunner {

	// Autowired UserRepository for accessing user data in the database
	@Autowired
	private UserRepository userRepository;

	// Main method to run the Spring Boot application
	public static void main(String[] args) {
		SpringApplication.run(SpringBootJwtApplication.class, args);
	}

	// Implementation of the CommandLineRunner interface to run code after application context loading
	@Override
	public void run(String... args) {
		// Check if an admin account already exists in the database
		User adminAccount = userRepository.findByRole(Role.ADMIN);

		// If admin account doesn't exist, create one and save it to the database
		if (null == adminAccount) {
			User user = new User();

			// Set admin user details
			user.setEmail("admin@gmail.com");
			user.setFirstname("admin");
			user.setLastname("admin");
			user.setRole(Role.ADMIN);
			// Use BCryptPasswordEncoder to encode the password before storing it
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));

			// Save the admin user to the database
			userRepository.save(user);
		}
	}
}

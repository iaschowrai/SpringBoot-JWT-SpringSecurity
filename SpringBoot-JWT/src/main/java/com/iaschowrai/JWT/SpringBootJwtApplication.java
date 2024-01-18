package com.iaschowrai.JWT;

import com.iaschowrai.JWT.entities.Role;
import com.iaschowrai.JWT.entities.User;
import com.iaschowrai.JWT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

// Spring Boot annotation to declare this class as the main application class
@SpringBootApplication
public class SpringBootJwtApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJwtApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// Check if an admin account already exists in the database
		User adminAccount = userRepository.findByRole(Role.ADMIN);

		// If admin account doesn't exist, create one and save it to the database
		if (null == adminAccount) {
			// Check if the admin email already exists in the database
			Optional<User> existingAdminOptional = userRepository.findByEmail("admin@gmail.com");

			if (existingAdminOptional.isPresent()) {
				System.out.println("Admin account already exists with the email 'admin@gmail.com'.");
			} else {
				// Admin account doesn't exist, proceed to create and save it
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
}

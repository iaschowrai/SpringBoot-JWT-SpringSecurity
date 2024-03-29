# Spring Boot Application for JWT (Json Web Token) authentication and authorization with Spring Security 6

This is a simple Spring Boot application demonstrating JWT (JSON Web Token) authentication.

Project Overview:
1. Overview:
   This is a simple Spring Boot application demonstrating JWT (JSON Web Token) authentication.
2. Technologies Used:
   1. 
      Spring Boot: A Java-based framework for building stand-alone, production-grade Spring-based applications.
   2. 
      Spring Security: Provides authentication and authorization support for Spring applications.
      
   3. JWT (JSON Web Token): A compact, URL-safe means of representing claims to be transferred between two parties.
      
   4. MySQL Database: Used for storing user information.
3. Key Features:
   
   1. User Signup: Allows users to register by providing their firstname, lastname, email, and password.
      
   2. User Signin: Authenticates users based on their email and password, generating a JWT for subsequent requests.
      
   3. Token Generation: Utilizes JWT to generate an access token upon successful authentication.
      
   4. Token Refresh: Provides an endpoint to refresh the JWT access token.
4. Project Structure:
   
   1. Entities: Defines the User entity with details like id, firstname, lastname, email, password, and role.
      
   2. Repositories: Handles database operations for user entities.
      
   3. Services: Contains service classes for user authentication and JWT operations.
      
   4. Controllers: Implements REST endpoints for user authentication and JWT functionalities.
      
   5. Configurations: Configures security settings using Spring Security.
      
   6. DTOs (Data Transfer Objects): Contains classes representing data exchanged between the client and the server.
      
   7. Application Class: SpringBootJwtApplication.java serves as the entry point of the application.
      
5. Database Setup:

      The project assumes the use of a MySQL database.
      The application automatically creates the necessary tables upon startup.
6. Security:
   
   Passwords are stored securely using BCrypt encoding.
      JWT is employed for secure user authentication and authorization.
7. Sample Requests:
   The README includes sample cURL requests demonstrating the usage of signup, signin, and token refresh endpoints.
8. Prerequisites:
   The project requires Java 17 or higher, Maven, and a MySQL database.

# Database Configuration
Configure the application by updating the application.properties file with your database settings.

spring.datasource.url=jdbc:mysql://localhost:3306/your_database

spring.datasource.username=your_username

spring.datasource.password=your_password



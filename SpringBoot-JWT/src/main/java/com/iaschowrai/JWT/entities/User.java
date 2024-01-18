package com.iaschowrai.JWT.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
// Lombok annotation to generate getter, setter, equals, hashCode, and toString methods
@Data
// JPA Entity annotation to indicate that this class is a JPA entity
@Entity
// JPA Table annotation to specify the table name in the database
@Table(name = "user")
public class User implements UserDetails {

    // JPA annotation to specify the primary key
    @Id
    // JPA annotation to specify the generation strategy for the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Fields representing user details
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    // Role of the user (ADMIN or USER)
    private Role role;

    // Method required by UserDetails interface to get the authorities (roles) of the user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return a list of GrantedAuthority, in this case, a SimpleGrantedAuthority with the user's role
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    // Method required by UserDetails interface to get the username of the user
    @Override
    public String getUsername() {
        return email;
    }

    // Methods required by UserDetails interface for account expiration, locking, credentials expiration, and enabled status
    @Override
    public boolean isAccountNonExpired() {
        return true; // Assuming the account never expires
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Assuming the account is never locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Assuming the credentials never expire
    }

    @Override
    public boolean isEnabled() {
        return true; // Assuming the account is always enabled
    }
}

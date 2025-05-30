package com.training.annotation.logging;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class with logging annotations
 */
public class UserService {
    
    @LogMethod(level = "INFO", logParameters = true, logReturnValue = true)
    public User createUser(String username, String email) {
        System.out.println("Creating user: " + username);
        return new User(username, email);
    }
    
    @LogMethod(level = "DEBUG", logParameters = false)
    public void deleteUser(Long id) {
        System.out.println("Deleting user with ID: " + id);
    }
    
    @LogMethod
    public List<User> getAllUsers() {
        System.out.println("Getting all users");
        return new ArrayList<>();
    }
    
    // Simple User class for this example
    public static class User {
        private String username;
        private String email;
        
        public User(String username, String email) {
            this.username = username;
            this.email = email;
        }
        
        public String getUsername() { return username; }
        public String getEmail() { return email; }
        
        @Override
        public String toString() {
            return "User{username='" + username + "', email='" + email + "'}";
        }
    }
} 
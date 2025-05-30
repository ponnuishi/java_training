package com.training.annotation.validation;

/**
 * User class with validation annotations
 */
public class User {
    @NotNull(message = "Name is required")
    private String name;
    
    @MinLength(value = 6, message = "Password must be at least 6 characters")
    private String password;
    
    @Email(message = "Please provide a valid email address")
    private String email;
    
    // Constructor
    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
    
    // Getters
    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    
    // Setters
    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
} 
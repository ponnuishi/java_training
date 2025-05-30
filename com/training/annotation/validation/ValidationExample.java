package com.training.annotation.validation;

import java.util.List;

/**
 * Demonstrates validation usage
 */
public class ValidationExample {
    
    public static void main(String[] args) {
        // Valid user
        User user1 = new User("John", "password123", "john@example.com");

        List<String> errors1 = Validator.validate(user1);
        System.out.println("Errors for user1: " + errors1);
        // Output: [] (no errors)
        
        // Invalid user - short password
        User user2 = new User("John", "123", "john@example.com");
        List<String> errors2 = Validator.validate(user2);
        System.out.println("Errors for user2: " + errors2);
        // Output: [Password must be at least 6 characters]
        
        // Invalid user - null name and invalid email
        User user3 = new User(null, "password123", "invalid-email");
        List<String> errors3 = Validator.validate(user3);
        System.out.println("Errors for user3: " + errors3);
        // Output: [Name is required, Please provide a valid email address]
    }
} 
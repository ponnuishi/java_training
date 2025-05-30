package com.training.debug.inspection;

import com.training.debug.util.Person;

import java.util.Arrays;
import java.util.List;

/**
 * Variable Inspection Example
 * Demonstrates inspecting variables during debugging
 */
public class VariableInspectionExample {
    public static void main(String[] args) {
        System.out.println("=== Variable Inspection Example ===");
        
        String name = "Alice";
        int age = 30;
        List<String> hobbies = Arrays.asList("reading", "swimming", "coding");
        
        Person person = new Person(name, age, hobbies);

        // ← Set breakpoint here to inspect variables
        System.out.println("Person created: " + person.getName());
        
        // More variables to inspect
        String message = "Hello, " + name + "!";
        int yearOfBirth = 2024 - age;
        boolean isAdult = age >= 18;
        
        // ← Set another breakpoint here to inspect all variables
        System.out.println("Message: " + message);
        System.out.println("Year of birth: " + yearOfBirth);
        System.out.println("Is adult: " + isAdult);
        
        System.out.println("Variable inspection example completed.");
    }
} 
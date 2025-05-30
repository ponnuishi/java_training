package com.training.optional.basics;

import java.util.Optional;
import java.util.NoSuchElementException;

/**
 * Basic Optional Examples demonstrating creation, checking, and retrieving values
 */
public class OptionalBasicsExample {
    public static void main(String[] args) {
        System.out.println("=== Optional Basics Examples ===");
        
        creatingOptionals();
        checkingForValues();
        retrievingValues();
        handlingEmptyOptionals();
    }
    
    private static void creatingOptionals() {
        System.out.println("\n--- Creating Optionals ---");
        
        // 1. Optional.of() - for non-null values
        Optional<String> nonNullOptional = Optional.of("Hello World");
        System.out.println("Optional.of(): " + nonNullOptional);
        
        // 2. Optional.ofNullable() - for potentially null values
        String nullableValue = "Java Optional";
        Optional<String> nullableOptional = Optional.ofNullable(nullableValue);
        System.out.println("Optional.ofNullable() with value: " + nullableOptional);
        
        String nullValue = null;
        Optional<String> nullOptional = Optional.ofNullable(nullValue);
        System.out.println("Optional.ofNullable() with null: " + nullOptional);
        
        // 3. Optional.empty() - for explicitly empty Optional
        Optional<String> emptyOptional = Optional.empty();
        System.out.println("Optional.empty(): " + emptyOptional);
        
        // 4. Demonstrating Optional.of() with null (this will throw NPE)
        try {
            Optional<String> npeOptional = Optional.of(null);
        } catch (NullPointerException e) {
            System.out.println("Optional.of(null) throws NPE: " + e.getMessage());
        }
    }
    
    private static void checkingForValues() {
        System.out.println("\n--- Checking for Values ---");
        
        Optional<String> presentOptional = Optional.of("Present Value");
        Optional<String> emptyOptional = Optional.empty();
        
        // 1. isPresent() - check if value exists
        System.out.println("presentOptional.isPresent(): " + presentOptional.isPresent());
        System.out.println("emptyOptional.isPresent(): " + emptyOptional.isPresent());
        
        // 2. isEmpty() - Java 11+ feature
        System.out.println("presentOptional.isEmpty(): " + presentOptional.isEmpty());
        System.out.println("emptyOptional.isEmpty(): " + emptyOptional.isEmpty());
        
        // 3. ifPresent() - execute action if value is present
        System.out.println("Using ifPresent():");
        presentOptional.ifPresent(value -> System.out.println("  Found value: " + value));
        emptyOptional.ifPresent(value -> System.out.println("  This won't print"));
        
        // 4. ifPresentOrElse() - Java 9+ feature
        System.out.println("Using ifPresentOrElse():");
        presentOptional.ifPresentOrElse(
            value -> System.out.println("  Present: " + value),
            () -> System.out.println("  No value found")
        );
        
        emptyOptional.ifPresentOrElse(
            value -> System.out.println("  Present: " + value),
            () -> System.out.println("  No value found")
        );
    }
    
    private static void retrievingValues() {
        System.out.println("\n--- Retrieving Values ---");
        
        Optional<String> presentOptional = Optional.of("Retrieved Value");
        Optional<String> emptyOptional = Optional.empty();
        
        // 1. get() - dangerous method, use with caution
        System.out.println("Using get() (dangerous):");
        String value1 = presentOptional.get();
        System.out.println("  Present value: " + value1);
        
        try {
            String value2 = emptyOptional.get();
        } catch (NoSuchElementException e) {
            System.out.println("  Empty optional throws: " + e.getClass().getSimpleName());
        }
        
        // 2. orElse() - provide default value
        System.out.println("Using orElse():");
        String value3 = presentOptional.orElse("Default Value");
        String value4 = emptyOptional.orElse("Default Value");
        System.out.println("  Present optional: " + value3);
        System.out.println("  Empty optional: " + value4);
        
        // 3. orElseGet() - provide default value via supplier
        System.out.println("Using orElseGet():");
        String value5 = presentOptional.orElseGet(() -> "Generated Default");
        String value6 = emptyOptional.orElseGet(() -> "Generated Default");
        System.out.println("  Present optional: " + value5);
        System.out.println("  Empty optional: " + value6);
        
        // 4. orElseThrow() - throw exception if empty
        System.out.println("Using orElseThrow():");
        String value7 = presentOptional.orElseThrow();
        System.out.println("  Present value: " + value7);
        
        try {
            String value8 = emptyOptional.orElseThrow();
        } catch (NoSuchElementException e) {
            System.out.println("  Empty optional throws: " + e.getClass().getSimpleName());
        }
        
        // 5. orElseThrow() with custom exception
        try {
            String value9 = emptyOptional.orElseThrow(() -> 
                new IllegalStateException("Custom exception: No value present"));
        } catch (IllegalStateException e) {
            System.out.println("  Custom exception: " + e.getMessage());
        }
    }
    
    private static void handlingEmptyOptionals() {
        System.out.println("\n--- Handling Empty Optionals ---");
        
        // Simulate a method that might return empty Optional
        Optional<String> result1 = findUserName(1);
        Optional<String> result2 = findUserName(999);
        
        // Different ways to handle empty optionals
        System.out.println("Method 1 - Using orElse():");
        System.out.println("  User 1: " + result1.orElse("Unknown User"));
        System.out.println("  User 999: " + result2.orElse("Unknown User"));
        
        System.out.println("Method 2 - Using ifPresentOrElse():");
        result1.ifPresentOrElse(
            name -> System.out.println("  Found user: " + name),
            () -> System.out.println("  User not found")
        );
        
        result2.ifPresentOrElse(
            name -> System.out.println("  Found user: " + name),
            () -> System.out.println("  User not found")
        );
        
        System.out.println("Method 3 - Using isPresent() check:");
        if (result1.isPresent()) {
            System.out.println("  User found: " + result1.get());
        } else {
            System.out.println("  User not found");
        }
        
        if (result2.isPresent()) {
            System.out.println("  User found: " + result2.get());
        } else {
            System.out.println("  User not found");
        }
    }
    
    // Helper method that returns Optional
    private static Optional<String> findUserName(int userId) {
        // Simulate database lookup
        if (userId == 1) {
            return Optional.of("Alice");
        } else if (userId == 2) {
            return Optional.of("Bob");
        } else {
            return Optional.empty();
        }
    }
} 
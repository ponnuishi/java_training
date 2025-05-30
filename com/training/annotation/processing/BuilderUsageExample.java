package com.training.annotation.processing;

/**
 * Demonstrates how to use the generated builder
 */
public class BuilderUsageExample {
    
    public static void main(String[] args) {
        System.out.println("=== Builder Usage Examples ===\n");
        
        // Example 1: Basic builder usage
        System.out.println("1. Basic Builder Usage:");
        Person person1 = new GeneratedPersonBuilder()
            .name("John Doe")
            .age(30)
            .email("john@example.com")
            .build();
        System.out.println("Created: " + person1);
        
        // Example 2: Partial builder usage (some fields)
        System.out.println("\n2. Partial Builder Usage:");
        Person person2 = new GeneratedPersonBuilder()
            .name("Jane Smith")
            .age(25)
            .build();
        System.out.println("Created: " + person2);
        
        // Example 3: Chaining multiple operations
        System.out.println("\n3. Chained Builder Usage:");
        Person person3 = new GeneratedPersonBuilder()
            .name("Bob Johnson")
            .age(35)
            .email("bob@example.com")
            .name("Robert Johnson") // Override previous value
            .build();
        System.out.println("Created: " + person3);
        
        // Example 4: Creating multiple objects with the same builder
        System.out.println("\n4. Multiple Objects with Same Builder:");
        GeneratedPersonBuilder builder = new GeneratedPersonBuilder();
        
        Person person4 = builder
            .name("Alice Brown")
            .age(28)
            .email("alice@example.com")
            .build();
        System.out.println("First person: " + person4);
        
        Person person5 = builder
            .name("Charlie Wilson")
            .age(42)
            .email("charlie@example.com")
            .build();
        System.out.println("Second person: " + person5);
        
        // Example 5: Builder with validation (if we had validation)
        System.out.println("\n5. Builder with Business Logic:");
        try {
            Person invalidPerson = new GeneratedPersonBuilder()
                .name("")  // Empty name
                .age(-5)   // Negative age
                .email("invalid-email")
                .build();
            System.out.println("Created: " + invalidPerson);
        } catch (Exception e) {
            System.out.println("Validation error: " + e.getMessage());
        }
        
        System.out.println("\n=== Builder Benefits ===");
        System.out.println("✓ Fluent API - easy to read and write");
        System.out.println("✓ Immutable objects - thread-safe");
        System.out.println("✓ Optional parameters - only set what you need");
        System.out.println("✓ Method chaining - concise syntax");
        System.out.println("✓ Type safety - compile-time checking");
    }
} 
package com.training.annotation.processing;

/**
 * Demonstrates annotation processing
 */
public class ProcessingExample {
    
    public static void main(String[] args) {
        // Demonstrate builder generation
        BuilderProcessor.demonstrateBuilderGeneration();
        
        System.out.println("\n=== Runtime Processing Example ===");
        
        // Check if Person class has the annotation
        if (Person.class.isAnnotationPresent(GenerateBuilder.class)) {
            System.out.println("Person class has @GenerateBuilder annotation");
            
            // Generate builder code
            String generatedCode = BuilderProcessor.generateBuilderCode(Person.class);
            System.out.println("Generated builder code length: " + generatedCode.length() + " characters");
            
            // Show a snippet of the generated code
            String[] lines = generatedCode.split("\n");
            System.out.println("First few lines of generated code:");
            for (int i = 0; i < Math.min(10, lines.length); i++) {
                System.out.println(lines[i]);
            }
            if (lines.length > 10) {
                System.out.println("... (truncated)");
            }
        } else {
            System.out.println("Person class does not have @GenerateBuilder annotation");
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("=== DEMONSTRATING BUILDER USAGE ===");
        System.out.println("=".repeat(50));
        
        // Demonstrate actual builder usage
        BuilderUsageExample.main(args);
    }
} 
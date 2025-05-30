package com.training.debug.advanced;

/**
 * Debug Console Example
 * Demonstrates debug console features
 */
public class DebugConsoleExample {
    public static void main(String[] args) {
        System.out.println("=== Debug Console Example ===");
        
        String message = "Debug console test";
        int number = 42;
        
        // ← Set breakpoint here
        // In debug console, you can:
        // - Print variables: message, number
        // - Execute expressions: message.length(), number * 2
        // - Call methods: message.toUpperCase()
        // - Create objects: new String("test")
        // - Test conditions: number > 40
        // - String operations: message + " " + number
        
        System.out.println("Message: " + message);
        System.out.println("Number: " + number);
        
        // More variables to test in console
        String[] words = message.split(" ");
        boolean isEven = number % 2 == 0;
        double sqrt = Math.sqrt(number);
        
        // ← Set another breakpoint here
        System.out.println("Words count: " + words.length);
        System.out.println("Is even: " + isEven);
        System.out.println("Square root: " + sqrt);
        
        System.out.println("Debug console example completed.");
    }
} 
package com.training.debug.stepping;

/**
 * Call Stack Example
 * Demonstrates call stack navigation
 */
public class CallStackExample {
    public static void main(String[] args) {
        System.out.println("=== Call Stack Example ===");
        
        processData();  // ← Call stack: main() → processData()
        
        System.out.println("Call stack example completed.");
    }
    
    public static void processData() {
        System.out.println("Processing data...");
        validateInput();  // ← Call stack: main() → processData() → validateInput()
    }
    
    public static void validateInput() {
        // ← Current execution point
        // Call stack shows the path: main() → processData() → validateInput()
        System.out.println("Validating input...");
        
        // Simulate some validation logic
        String input = "test";
        if (input == null || input.isEmpty()) {
            System.out.println("Input validation failed");
        } else {
            System.out.println("Input validation passed");
        }
    }
} 
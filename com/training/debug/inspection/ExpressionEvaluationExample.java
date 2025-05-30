package com.training.debug;

/**
 * Expression Evaluation Example
 * Demonstrates evaluating expressions during debugging
 */
public class ExpressionEvaluationExample {
    public static void main(String[] args) {
        System.out.println("=== Expression Evaluation Example ===");
        
        String text = "Hello, World!";
        int length = text.length();
        
        // ← Set breakpoint here
        System.out.println("Text: " + text);
        System.out.println("Length: " + length);
        
        // Use Evaluate Expression to test:
        // - text.toUpperCase()
        // - text.substring(0, 5)
        // - text.contains("World")
        // - length * 2
        // - text.replace("World", "Java")
        // - text.split(",")
        
        String upperText = text.toUpperCase();
        String subText = text.substring(0, 5);
        boolean containsWorld = text.contains("World");
        int doubleLength = length * 2;
        
        System.out.println("Upper case: " + upperText);
        System.out.println("Substring: " + subText);
        System.out.println("Contains 'World': " + containsWorld);
        System.out.println("Double length: " + doubleLength);
        
        System.out.println("Expression evaluation example completed.");
    }
} 
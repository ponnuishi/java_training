package com.training.debug.stepping;

/**
 * Drop Frame Example
 * Demonstrates drop frame functionality
 */
public class DropFrameExample {
    public static void main(String[] args) {
        System.out.println("=== Drop Frame Example ===");
        
        String result = processString("hello");  // ← Drop frame to restart this call
        System.out.println("Result: " + result);
        
        System.out.println("Drop frame example completed.");
    }
    
    public static String processString(String input) {
        String upper = input.toUpperCase();
        String reversed = reverseString(upper);  // ← Current execution point
        return reversed;
    }
    
    public static String reverseString(String str) {
        return new StringBuilder(str).reverse().toString();
    }
} 
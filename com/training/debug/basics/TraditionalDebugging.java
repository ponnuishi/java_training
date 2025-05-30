package com.training.debug.basics;

/**
 * Traditional Debugging Example
 * Demonstrates debugging using print statements
 */
public class TraditionalDebugging {
    public static void main(String[] args) {
        System.out.println("=== Traditional Debugging Example ===");
        
        int[] numbers = {1, 2, 3, 4, 5};
        int sum = 0;
        
        System.out.println("Starting sum calculation...");
        for (int i = 0; i < numbers.length; i++) {
            sum += numbers[i];
            System.out.println("i: " + i + ", numbers[i]: " + numbers[i] + ", sum: " + sum);
        }
        
        System.out.println("Final sum: " + sum);
        System.out.println("Traditional debugging completed.");
    }
} 
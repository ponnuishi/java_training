package com.training.debug.breakpoints;

/**
 * Breakpoint Example
 * Demonstrates setting line breakpoints
 */
public class BreakpointExample {
    public static void main(String[] args) {
        System.out.println("=== Breakpoint Example ===");
        
        int[] numbers = {1, 2, 3, 4, 5};
        int sum = 0;
        
        System.out.println("Starting sum calculation...");
        for (int i = 0; i < numbers.length; i++) {
            sum += numbers[i];  // ← Set breakpoint here
            System.out.println("Current sum: " + sum);
        }
        
        System.out.println("Final sum: " + sum);
        System.out.println("Breakpoint example completed.");
    }
} 
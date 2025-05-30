package com.training.debug.breakpoints;

/**
 * Conditional Breakpoint Example
 * Demonstrates setting conditional breakpoints
 */
public class ConditionalBreakpointExample {
    public static void main(String[] args) {
        System.out.println("=== Conditional Breakpoint Example ===");
        
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        
        System.out.println("Processing numbers...");
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] > 5) {  // ← Conditional breakpoint: i > 5
                System.out.println("Found number > 5: " + numbers[i]);
            }
        }
        
        System.out.println("Conditional breakpoint example completed.");
    }
} 
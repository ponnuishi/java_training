package com.training.debug.basics;

/**
 * Modern Debugging Example
 * Demonstrates debugging using IDE debugger with breakpoints
 */
public class ModernDebugging {
    public static void main(String[] args) {
        System.out.println("=== Modern Debugging Example ===");
        
        int[] numbers = {1, 2, 3, 4, 5};
        int sum = calculateSum(numbers);
        System.out.println("Final sum: " + sum);
        System.out.println("Modern debugging completed.");
    }
    
    public static int calculateSum(int[] numbers) {
        int sum = 0;
        for (int i = 0; i < numbers.length; i++) {
            sum += numbers[i];
            // Set breakpoint here to inspect variables
            // Use F8 to step over, F7 to step into
        }
        return sum;
    }
} 
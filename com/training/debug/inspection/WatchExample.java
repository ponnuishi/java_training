package com.training.debug.inspection;

/**
 * Watch Example
 * Demonstrates using watches during debugging
 */
public class WatchExample {
    public static void main(String[] args) {
        System.out.println("=== Watch Example ===");
        
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int sum = 0;
        int count = 0;
        
        System.out.println("Processing even numbers...");
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] % 2 == 0) {  // Even numbers
                sum += numbers[i];
                count++;
            }
            // ← Set breakpoint here and add watches:
            // - sum (current sum of even numbers)
            // - count (count of even numbers)
            // - numbers[i] (current number being processed)
            // - i (current index)
        }
        
        System.out.println("Sum of even numbers: " + sum);
        System.out.println("Count of even numbers: " + count);
        System.out.println("Watch example completed.");
    }
} 
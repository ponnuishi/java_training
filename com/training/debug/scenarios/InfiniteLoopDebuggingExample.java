package com.training.debug.scenarios;

/**
 * Infinite Loop Debugging Example
 * Demonstrates debugging infinite loops
 */
public class InfiniteLoopDebuggingExample {
    public static void main(String[] args) {
        System.out.println("=== Infinite Loop Debugging Example ===");
        
        try {
            int result = findNumber(5);
            System.out.println("Found: " + result);
        } catch (Exception e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
        
        // Another example with a different infinite loop
        try {
            int count = countDown(10);
            System.out.println("Count down completed: " + count);
        } catch (Exception e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
        
        System.out.println("Infinite loop debugging example completed.");
    }
    
    public static int findNumber(int target) {
        int i = 0;
        while (true) {  // ← Potential infinite loop
            if (i == target) {
                return i;
            }
            // ← Missing increment: i++
            // This will cause an infinite loop
        }
    }
    
    public static int countDown(int start) {
        int count = start;
        while (count > 0) {
            System.out.println("Count: " + count);
            // ← Missing decrement: count--
            // This will cause an infinite loop
        }
        return count;
    }
} 
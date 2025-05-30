package com.training.debug;

/**
 * Breakpoint Strategy Example
 * Demonstrates effective breakpoint strategies
 */
public class BreakpointStrategyExample {
    public static void main(String[] args) {
        System.out.println("=== Breakpoint Strategy Example ===");
        
        // Strategy 1: Entry points
        processUserInput("test input");  // ← Breakpoint 1: Entry point
        
        // Strategy 2: Decision points
        int result = calculateResult(10, 5);  // ← Breakpoint 2: Before calculation
        
        // Strategy 3: Exit points
        System.out.println("Final result: " + result);  // ← Breakpoint 3: Output
        
        System.out.println("Breakpoint strategy example completed.");
    }
    
    public static void processUserInput(String input) {
        if (input == null || input.isEmpty()) {  // ← Breakpoint 4: Decision point
            throw new IllegalArgumentException("Input cannot be null or empty");
        }
        
        String processed = input.trim().toLowerCase();
        // ← Breakpoint 5: After processing
        
        System.out.println("Processed input: " + processed);
    }
    
    public static int calculateResult(int a, int b) {
        if (b == 0) {  // ← Breakpoint 6: Error condition
            throw new ArithmeticException("Division by zero");
        }
        
        int result = a / b;  // ← Breakpoint 7: Calculation result
        return result;
    }
} 
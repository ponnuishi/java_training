package com.training.debug;

import com.training.debug.util.Calculator;

/**
 * Calculator Debug Example
 * Demonstrates debugging unexpected behavior
 */
public class CalculatorDebug {
    public static void main(String[] args) {
        System.out.println("=== Calculator Debug Example ===");
        
        Calculator calc = new Calculator();
        int result = calc.add(2, 3);
        
        // Debugging: Why is result not 5?
        // Set breakpoint here to inspect calc object and method execution
        System.out.println("Result: " + result);
        
        // Let's investigate further
        System.out.println("Debugging step by step:");
        int a = 2;
        int b = 3;
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        
        // Set breakpoint here to step into the add method
        int stepResult = calc.add(a, b);
        System.out.println("Step result: " + stepResult);
        
        System.out.println("Debugging completed.");
    }
} 
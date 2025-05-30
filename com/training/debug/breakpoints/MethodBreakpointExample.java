package com.training.debug.breakpoints;

import com.training.debug.util.Calculator;

/**
 * Method Breakpoint Example
 * Demonstrates setting method breakpoints
 */
public class MethodBreakpointExample {
    public static void main(String[] args) {
        System.out.println("=== Method Breakpoint Example ===");
        
        Calculator calc = new Calculator();
        int result = calc.add(5, 3);  // ← Method breakpoint on add()
        System.out.println("Result: " + result);
        
        double divisionResult = calc.divide(10, 2);  // ← Method breakpoint on divide()
        System.out.println("Division result: " + divisionResult);
        
        System.out.println("Method breakpoint example completed.");
    }
} 
package com.training.debug.breakpoints;

/**
 * Exception Breakpoint Example
 * Demonstrates setting exception breakpoints
 */
public class ExceptionBreakpointExample {
    public static void main(String[] args) {
        System.out.println("=== Exception Breakpoint Example ===");

        int result = divide(10, 0);  // ← Will throw ArithmeticException
        System.out.println("Result: " + result);

        try {
            int result2 = divide(10, 2);  // This should work
            System.out.println("Result2: " + result2);
        } catch (ArithmeticException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }

        System.out.println("Exception breakpoint example completed.");
    }

    public static int divide(int a, int b) {
        return a / b;  // ← Exception breakpoint on ArithmeticException
    }
} 
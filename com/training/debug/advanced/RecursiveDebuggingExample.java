package com.training.debug;

/**
 * Recursive Debugging Example
 * Demonstrates debugging recursive methods
 */
public class RecursiveDebuggingExample {
    public static void main(String[] args) {
        System.out.println("=== Recursive Debugging Example ===");
        
        int result = factorial(24);
        System.out.println("5! = " + result);
        
        int fibonacciResult = fibonacci(6);
        System.out.println("Fibonacci(6) = " + fibonacciResult);
        
        System.out.println("Recursive debugging example completed.");
    }
    
    public static int factorial(int n) {
        // ← Set breakpoint here to trace recursion
        if (n <= 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }
    
    public static int fibonacci(int n) {
        // ← Set breakpoint here to trace recursion
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
} 
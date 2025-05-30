package com.training.debug.basics;

import com.training.debug.util.Calculator;

/**
 * Calculator Test Example
 * Demonstrates testing vs debugging
 */
public class CalculatorTest {
    public static void main(String[] args) {
        System.out.println("=== Calculator Test Example ===");
        
        Calculator calc = new Calculator();
        
        // Testing - Verifies expected behavior
        testAddition(calc);
        testSubtraction(calc);
        testMultiplication(calc);
        testDivision(calc);
        
        System.out.println("All tests completed.");
    }
    
    private static void testAddition(Calculator calc) {
        int result = calc.add(2, 3);
        if (result == 5) {
            System.out.println("✓ Addition test passed: 2 + 3 = " + result);
        } else {
            System.out.println("✗ Addition test failed: expected 5, got " + result);
        }
    }
    
    private static void testSubtraction(Calculator calc) {
        int result = calc.subtract(5, 3);
        if (result == 2) {
            System.out.println("✓ Subtraction test passed: 5 - 3 = " + result);
        } else {
            System.out.println("✗ Subtraction test failed: expected 2, got " + result);
        }
    }
    
    private static void testMultiplication(Calculator calc) {
        int result = calc.multiply(4, 3);
        if (result == 12) {
            System.out.println("✓ Multiplication test passed: 4 * 3 = " + result);
        } else {
            System.out.println("✗ Multiplication test failed: expected 12, got " + result);
        }
    }
    
    private static void testDivision(Calculator calc) {
        try {
            double result = calc.divide(10, 2);
            if (result == 5.0) {
                System.out.println("✓ Division test passed: 10 / 2 = " + result);
            } else {
                System.out.println("✗ Division test failed: expected 5.0, got " + result);
            }
        } catch (ArithmeticException e) {
            System.out.println("✗ Division test failed with exception: " + e.getMessage());
        }
    }
} 
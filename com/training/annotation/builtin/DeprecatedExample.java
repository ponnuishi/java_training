package com.training.annotation.builtin;

/**
 * Demonstrates the usage of @Deprecated annotation
 */
public class DeprecatedExample {
    
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        
        // IDE shows warning and strikethrough
        calc.addOld(5, 3);      // Deprecated method
        calc.add(5, 3);         // Current method
        
        // This will be removed in future versions
        calc.multiplyOld(4, 5); // Deprecated with removal notice
    }
}

class Calculator {
    @Deprecated
    public int addOld(int a, int b) {
        return a + b;
    }
    
    public int add(int a, int b) {
        return a + b;
    }
    
    @Deprecated(since = "2.0", forRemoval = true)
    public int multiplyOld(int a, int b) {
        return a * b;
    }
    
    public int multiply(int a, int b) {
        return a * b;
    }
} 
package com.training.annotation.builtin;

/**
 * Demonstrates the usage of @FunctionalInterface annotation
 */
public class FunctionalInterfaceExample {
    
    public static void main(String[] args) {
//        MathOperation add = (a, b) -> a + b;
        MathOperation add = (int a, int b) -> {
            return a + b;
        };
        MathOperation multiply = (a, b) -> a * b;
        
        System.out.println(add.operate(5, 3));      // 8
        System.out.println(multiply.operate(4, 5)); // 20
        
        // Using default method
        System.out.println(add.add(10, 20));        // 30
        
        // Using static method
        MathOperation defaultOp = MathOperation.getDefault();
        System.out.println(defaultOp.operate(7, 8)); // 15
    }
}


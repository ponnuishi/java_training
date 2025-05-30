package com.training.debug.scenarios;

import java.util.ArrayList;
import java.util.List;

/**
 * Performance Debugging Example
 * Demonstrates performance debugging techniques
 */
public class PerformanceDebuggingExample {
    public static void main(String[] args) {
        System.out.println("=== Performance Debugging Example ===");
        
        List<Integer> numbers = generateNumbers(10000);
        
        // ← Profile this method to find bottlenecks
        long startTime = System.currentTimeMillis();
        int sum = calculateSum(numbers);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Sum: " + sum);
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
        
        // Another performance example
        startTime = System.currentTimeMillis();
        List<String> strings = generateStrings(1000);
        endTime = System.currentTimeMillis();
        
        System.out.println("Generated " + strings.size() + " strings");
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
        
        System.out.println("Performance debugging example completed.");
    }
    
    public static List<Integer> generateNumbers(int count) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            numbers.add(i);
        }
        return numbers;
    }
    
    public static int calculateSum(List<Integer> numbers) {
        int sum = 0;
        for (Integer number : numbers) {
            sum += number;
        }
        return sum;
    }
    
    public static List<String> generateStrings(int count) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            strings.add("String " + i);
        }
        return strings;
    }
} 
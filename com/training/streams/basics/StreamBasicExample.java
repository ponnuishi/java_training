package com.training.streams.basics;

import java.util.*;

/**
 * Basic Stream Example demonstrating the fundamental stream pipeline structure
 * Source → Intermediate Operation → Terminal Operation
 */
public class StreamBasicExample {
    public static void main(String[] args) {
        System.out.println("=== Basic Stream Example ===");
        
        // Example 1: Print all even numbers from a list
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        System.out.println("Original numbers: " + numbers);
        System.out.println("Even numbers:");
        numbers.stream()                  // Source
            .filter(n -> n % 2 == 0)     // Intermediate: keep even numbers
            .forEach(System.out::println); // Terminal: print each
        
        System.out.println("\n=== Stream vs Traditional Loop ===");
        
        // Traditional way (for comparison)
        System.out.println("Traditional loop approach:");
        for (Integer number : numbers) {
            if (number % 2 == 0) {
                System.out.println(number);
            }
        }
        
        // Stream way
        System.out.println("Stream approach:");
        numbers.stream()
            .filter(n -> n % 2 == 0)
            .forEach(System.out::println);
        
        System.out.println("\n=== Multiple Operations ===");
        
        // Multiple intermediate operations
        System.out.println("Even numbers doubled and sorted:");
        numbers.stream()
            .filter(n -> n % 2 == 0)     // Keep even numbers
            .map(n -> n * 2)             // Double each number
            .sorted()                    // Sort them
            .forEach(System.out::println);
    }
} 
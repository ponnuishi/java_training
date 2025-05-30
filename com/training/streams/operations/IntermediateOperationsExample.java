package com.training.streams.operations;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Examples demonstrating intermediate stream operations
 * Intermediate operations return a Stream and are lazy (not executed until terminal operation)
 */
public class IntermediateOperationsExample {
    public static void main(String[] args) {
        System.out.println("=== Intermediate Operations Examples ===");
        
        filterExample();
        mapExample();
        sortedExample();
        distinctExample();
        limitAndSkipExample();
        combinedOperationsExample();
    }
    
    private static void filterExample() {
        System.out.println("\n--- Filter Operation ---");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        System.out.println("Original: " + numbers);
        
        // Filter even numbers
        List<Integer> evenNumbers = numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());
        System.out.println("Even numbers: " + evenNumbers);
        
        // Filter numbers greater than 5
        List<Integer> greaterThanFive = numbers.stream()
            .filter(n -> n > 5)
            .collect(Collectors.toList());
        System.out.println("Numbers > 5: " + greaterThanFive);
        
        // Filter strings
        List<String> words = Arrays.asList("apple", "banana", "cherry", "apricot", "blueberry");
        List<String> aWords = words.stream()
            .filter(word -> word.startsWith("a"))
            .collect(Collectors.toList());
        System.out.println("Words starting with 'a': " + aWords);
    }
    
    private static void mapExample() {
        System.out.println("\n--- Map Operation ---");
        
        List<String> words = Arrays.asList("hello", "world", "java", "streams");
        
        System.out.println("Original: " + words);
        
        // Convert to uppercase
        List<String> uppercase = words.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        System.out.println("Uppercase: " + uppercase);
        
        // Get string lengths
        List<Integer> lengths = words.stream()
            .map(String::length)
            .collect(Collectors.toList());
        System.out.println("Lengths: " + lengths);
        
        // Square numbers
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squares = numbers.stream()
            .map(n -> n * n)
            .collect(Collectors.toList());
        System.out.println("Squares: " + squares);
    }
    
    private static void sortedExample() {
        System.out.println("\n--- Sorted Operation ---");
        
        List<String> words = Arrays.asList("banana", "apple", "cherry", "date");
        
        System.out.println("Original: " + words);
        
        // Natural sorting
        List<String> sorted = words.stream()
            .sorted()
            .collect(Collectors.toList());
        System.out.println("Sorted: " + sorted);
        
        // Reverse sorting
        List<String> reverseSorted = words.stream()
            .sorted(Comparator.reverseOrder())
            .collect(Collectors.toList());
        System.out.println("Reverse sorted: " + reverseSorted);
        
        // Sort by length
        List<String> sortedByLength = words.stream()
            .sorted(Comparator.comparing(String::length))
            .collect(Collectors.toList());
        System.out.println("Sorted by length: " + sortedByLength);
        
        // Sort numbers
        List<Integer> numbers = Arrays.asList(5, 2, 8, 1, 9, 3);
        List<Integer> sortedNumbers = numbers.stream()
            .sorted()
            .collect(Collectors.toList());
        System.out.println("Sorted numbers: " + sortedNumbers);
    }
    
    private static void distinctExample() {
        System.out.println("\n--- Distinct Operation ---");
        
        List<Integer> numbersWithDuplicates = Arrays.asList(1, 2, 2, 3, 3, 3, 4, 4, 5);
        
        System.out.println("Original: " + numbersWithDuplicates);
        
        List<Integer> distinctNumbers = numbersWithDuplicates.stream()
            .distinct()
            .collect(Collectors.toList());
        System.out.println("Distinct: " + distinctNumbers);
        
        // Distinct strings
        List<String> wordsWithDuplicates = Arrays.asList("apple", "banana", "apple", "cherry", "banana");
        List<String> distinctWords = wordsWithDuplicates.stream()
            .distinct()
            .collect(Collectors.toList());
        System.out.println("Distinct words: " + distinctWords);
    }
    
    private static void limitAndSkipExample() {
        System.out.println("\n--- Limit and Skip Operations ---");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        System.out.println("Original: " + numbers);
        
        // Limit - take first n elements
        List<Integer> first5 = numbers.stream()
            .limit(5)
            .collect(Collectors.toList());
        System.out.println("First 5: " + first5);
        
        // Skip - skip first n elements
        List<Integer> skip5 = numbers.stream()
            .skip(5)
            .collect(Collectors.toList());
        System.out.println("Skip first 5: " + skip5);
        
        // Combine skip and limit for pagination
        List<Integer> page2 = numbers.stream()
            .skip(3)    // Skip first 3
            .limit(3)   // Take next 3
            .collect(Collectors.toList());
        System.out.println("Page 2 (skip 3, take 3): " + page2);
    }
    
    private static void combinedOperationsExample() {
        System.out.println("\n--- Combined Operations ---");
        
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date", "elderberry", "fig", "grape");
        
        System.out.println("Original: " + words);
        
        // Complex chain: filter -> map -> distinct -> sorted -> limit
        List<String> result = words.stream()
            .filter(word -> word.length() > 4)      // Words longer than 4 chars
            .map(String::toUpperCase)               // Convert to uppercase
            .distinct()                             // Remove duplicates
            .sorted()                               // Sort alphabetically
            .limit(3)                               // Take first 3
            .collect(Collectors.toList());
        
        System.out.println("Filtered, mapped, distinct, sorted, limited: " + result);
        
        // Another example with numbers
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 2, 4, 6);
        List<Integer> processedNumbers = numbers.stream()
            .filter(n -> n % 2 == 0)               // Even numbers
            .map(n -> n * n)                       // Square them
            .distinct()                            // Remove duplicates
            .sorted(Comparator.reverseOrder())     // Sort descending
            .limit(5)                              // Take first 5
            .collect(Collectors.toList());
        
        System.out.println("Even numbers squared, distinct, sorted desc, limited: " + processedNumbers);
    }
} 
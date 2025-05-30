package com.training.streams.creation;

import java.util.*;
import java.util.stream.*;
import java.nio.file.*;
import java.io.IOException;

/**
 * Examples demonstrating different ways to create streams
 */
public class StreamCreationExample {
    public static void main(String[] args) {
        System.out.println("=== Stream Creation Examples ===");
        
        // 1. From Collections
        createFromCollections();
        
        // 2. From Arrays
        createFromArrays();
        
        // 3. From Individual Values
        createFromValues();
        
        // 4. From Files
        createFromFiles();
        
        // 5. Infinite Streams
        createInfiniteStreams();
        
        // 6. Empty and Range Streams
        createSpecialStreams();
    }
    
    private static void createFromCollections() {
        System.out.println("\n--- 1. From Collections ---");
        
        List<String> list = Arrays.asList("apple", "banana", "cherry");
        Set<Integer> set = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        
        System.out.println("From List:");
        list.stream().forEach(System.out::println);
        
        System.out.println("From Set:");
        set.stream().sorted().forEach(System.out::println);
        
        // Parallel stream
        System.out.println("Parallel stream from List:");
        list.parallelStream().forEach(System.out::println);
    }
    
    private static void createFromArrays() {
        System.out.println("\n--- 2. From Arrays ---");
        
        // Object array
        String[] words = {"hello", "world", "java", "streams"};
        System.out.println("From String array:");
        Arrays.stream(words).forEach(System.out::println);
        
        // Primitive array
        int[] numbers = {1, 2, 3, 4, 5};
        System.out.println("From int array:");
        Arrays.stream(numbers).forEach(System.out::println);
        
        // Using Stream.of()
        System.out.println("Using Stream.of() with array:");
        Stream.of(words).forEach(System.out::println);
    }
    
    private static void createFromValues() {
        System.out.println("\n--- 3. From Individual Values ---");
        
        Stream<Integer> numberStream = Stream.of(1, 2, 3, 4, 5);
        System.out.println("From individual integers:");
        numberStream.forEach(System.out::println);
        
        Stream<String> stringStream = Stream.of("one", "two", "three");
        System.out.println("From individual strings:");
        stringStream.forEach(System.out::println);
    }
    
    private static void createFromFiles() {
        System.out.println("\n--- 4. From Files ---");
        
        try {
            // Check if numbers.txt exists in the workspace
            Path filePath = Paths.get("numbers.txt");
            if (Files.exists(filePath)) {
                System.out.println("Reading from numbers.txt:");
                Files.lines(filePath)
                    .limit(5)  // Limit to first 5 lines
                    .forEach(System.out::println);
            } else {
                System.out.println("numbers.txt not found, creating example content:");
                // Create a temporary file for demonstration
                List<String> lines = Arrays.asList("Line 1", "Line 2", "Line 3");
                lines.stream().forEach(System.out::println);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    private static void createInfiniteStreams() {
        System.out.println("\n--- 5. Infinite Streams ---");
        
        // Stream.generate() - generates infinite stream
        System.out.println("Random numbers (first 5):");
        Stream.generate(Math::random)
            .limit(5)
            .forEach(System.out::println);
        
        // Stream.iterate() - generates infinite stream with iteration
        System.out.println("Even numbers (first 10):");
        Stream.iterate(0, n -> n + 2)
            .limit(10)
            .forEach(System.out::println);
        
        // Stream.iterate() with condition (Java 9+)
        System.out.println("Numbers less than 20:");
        Stream.iterate(1, n -> n < 20, n -> n + 3)
            .forEach(System.out::println);
    }
    
    private static void createSpecialStreams() {
        System.out.println("\n--- 6. Special Streams ---");
        
        // Empty stream
        Stream<String> emptyStream = Stream.empty();
        System.out.println("Empty stream count: " + emptyStream.count());
        
        // Range streams (IntStream, LongStream, DoubleStream)
        System.out.println("IntStream range 1-10:");
        IntStream.range(1, 11).forEach(System.out::println);
        
        System.out.println("IntStream rangeClosed 1-5:");
        IntStream.rangeClosed(1, 5).forEach(System.out::println);
        
        // Builder pattern
        System.out.println("Using Stream.Builder:");
        Stream<String> builderStream = Stream.<String>builder()
            .add("a")
            .add("b")
            .add("c")
            .build();
        builderStream.forEach(System.out::println);
    }
} 
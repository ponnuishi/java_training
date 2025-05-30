package com.training.streams.terminal;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Examples demonstrating terminal stream operations
 * Terminal operations produce a result or side-effect and consume the stream
 */
public class TerminalOperationsExample {
    public static void main(String[] args) {
        System.out.println("=== Terminal Operations Examples ===");
        
        forEachExample();
        collectExample();
        countExample();
        reduceExample();
        matchingExample();
        findExample();
        aggregateExample();
    }
    
    private static void forEachExample() {
        System.out.println("\n--- forEach Operation ---");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Diana");
        
        System.out.println("Simple forEach:");
        names.stream().forEach(System.out::println);
        
        System.out.println("forEach with index simulation:");
        names.stream().forEach(name -> System.out.println("Hello, " + name + "!"));
        
        // forEachOrdered (maintains order in parallel streams)
        System.out.println("forEachOrdered:");
        names.parallelStream().forEachOrdered(System.out::println);
    }
    
    private static void collectExample() {
        System.out.println("\n--- collect Operation ---");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Collect to List
        List<Integer> evenNumbers = numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());
        System.out.println("Even numbers as List: " + evenNumbers);
        
        // Collect to Set
        Set<Integer> uniqueNumbers = numbers.stream()
            .collect(Collectors.toSet());
        System.out.println("Numbers as Set: " + uniqueNumbers);
        
        // Collect to Map
        List<String> words = Arrays.asList("apple", "banana", "cherry");
        Map<String, Integer> wordLengths = words.stream()
            .collect(Collectors.toMap(
                word -> word,           // key
                String::length          // value
            ));
        System.out.println("Word lengths: " + wordLengths);
        
        // Joining strings
        String joined = words.stream()
            .collect(Collectors.joining(", "));
        System.out.println("Joined words: " + joined);
        
        // Grouping
        List<String> animals = Arrays.asList("ant", "bear", "cat", "dog", "elephant", "fox");
        Map<Integer, List<String>> groupedByLength = animals.stream()
            .collect(Collectors.groupingBy(String::length));
        System.out.println("Grouped by length: " + groupedByLength);
    }
    
    private static void countExample() {
        System.out.println("\n--- count Operation ---");
        
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date", "elderberry");
        
        long totalCount = words.stream().count();
        System.out.println("Total words: " + totalCount);
        
        long longWordsCount = words.stream()
            .filter(word -> word.length() > 5)
            .count();
        System.out.println("Words longer than 5 chars: " + longWordsCount);
        
        // Count with distinct
        List<Integer> numbersWithDuplicates = Arrays.asList(1, 2, 2, 3, 3, 3, 4);
        long distinctCount = numbersWithDuplicates.stream()
            .distinct()
            .count();
        System.out.println("Distinct numbers count: " + distinctCount);
    }
    
    private static void reduceExample() {
        System.out.println("\n--- reduce Operation ---");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        // Sum using reduce
        int sum = numbers.stream()
            .reduce(0, Integer::sum);
        System.out.println("Sum: " + sum);
        
        // Product using reduce
        int product = numbers.stream()
            .reduce(1, (a, b) -> a * b);
        System.out.println("Product: " + product);
        
        // Max using reduce
        Optional<Integer> max = numbers.stream()
            .reduce(Integer::max);
        max.ifPresent(value -> System.out.println("Max: " + value));
        
        // String concatenation
        List<String> words = Arrays.asList("Hello", "World", "Java", "Streams");
        String concatenated = words.stream()
            .reduce("", (a, b) -> a + " " + b);
        System.out.println("Concatenated: " + concatenated.trim());
        
        // Finding longest string
        Optional<String> longest = words.stream()
            .reduce((a, b) -> a.length() > b.length() ? a : b);
        longest.ifPresent(value -> System.out.println("Longest word: " + value));
    }
    
    private static void matchingExample() {
        System.out.println("\n--- Matching Operations (anyMatch, allMatch, noneMatch) ---");
        
        List<Integer> numbers = Arrays.asList(2, 4, 6, 8, 10);
        List<String> words = Arrays.asList("apple", "banana", "cherry");
        
        // anyMatch - returns true if any element matches
        boolean anyEven = numbers.stream().anyMatch(n -> n % 2 == 0);
        System.out.println("Any even numbers: " + anyEven);
        
        boolean anyStartsWithA = words.stream().anyMatch(word -> word.startsWith("a"));
        System.out.println("Any word starts with 'a': " + anyStartsWithA);
        
        // allMatch - returns true if all elements match
        boolean allEven = numbers.stream().allMatch(n -> n % 2 == 0);
        System.out.println("All even numbers: " + allEven);
        
        boolean allLongerThan3 = words.stream().allMatch(word -> word.length() > 3);
        System.out.println("All words longer than 3 chars: " + allLongerThan3);
        
        // noneMatch - returns true if no elements match
        boolean noneOdd = numbers.stream().noneMatch(n -> n % 2 == 1);
        System.out.println("No odd numbers: " + noneOdd);
        
        boolean noneStartsWithZ = words.stream().noneMatch(word -> word.startsWith("z"));
        System.out.println("No word starts with 'z': " + noneStartsWithZ);
    }
    
    private static void findExample() {
        System.out.println("\n--- Find Operations (findFirst, findAny) ---");
        
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        // findFirst - returns Optional of first element
        Optional<String> firstWord = words.stream().findFirst();
        firstWord.ifPresent(word -> System.out.println("First word: " + word));
        
        // findAny - returns Optional of any element (useful in parallel streams)
        Optional<String> anyWord = words.stream().findAny();
        anyWord.ifPresent(word -> System.out.println("Any word: " + word));
        
        // findFirst with filter
        Optional<Integer> firstEven = numbers.stream()
            .filter(n -> n % 2 == 0)
            .findFirst();
        firstEven.ifPresent(num -> System.out.println("First even number: " + num));
        
        // findAny with filter
        Optional<String> anyLongWord = words.stream()
            .filter(word -> word.length() > 5)
            .findAny();
        if (anyLongWord.isPresent()) {
            System.out.println("Any long word: " + anyLongWord.get());
        } else {
            System.out.println("No long words found");
        }
        
        // Handling empty Optional
        Optional<String> notFound = words.stream()
            .filter(word -> word.startsWith("z"))
            .findFirst();
        
        String result = notFound.orElse("Not found");
        System.out.println("Word starting with 'z': " + result);
    }
    
    private static void aggregateExample() {
        System.out.println("\n--- Aggregate Operations ---");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Using specialized methods
        OptionalInt max = numbers.stream().mapToInt(Integer::intValue).max();
        max.ifPresent(value -> System.out.println("Max: " + value));
        
        OptionalInt min = numbers.stream().mapToInt(Integer::intValue).min();
        min.ifPresent(value -> System.out.println("Min: " + value));
        
        double average = numbers.stream().mapToInt(Integer::intValue).average().orElse(0.0);
        System.out.println("Average: " + average);
        
        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Sum: " + sum);
        
        // Statistics
        IntSummaryStatistics stats = numbers.stream()
            .mapToInt(Integer::intValue)
            .summaryStatistics();
        
        System.out.println("Statistics: " + stats);
        System.out.println("Count: " + stats.getCount());
        System.out.println("Sum: " + stats.getSum());
        System.out.println("Min: " + stats.getMin());
        System.out.println("Max: " + stats.getMax());
        System.out.println("Average: " + stats.getAverage());
    }
} 
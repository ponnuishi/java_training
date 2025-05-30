package com.training.streams;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Practical examples combining multiple stream operations
 * Based on the examples from the Stream Programming Tutorial
 */
public class StreamPracticalExamples {
    public static void main(String[] args) {
        System.out.println("=== Practical Stream Examples ===");
        
        filterAndCollectExample();
        mapAndReduceExample();
        sortingAndLimitingExample();
        distinctAndCountExample();
        complexProcessingExample();
        realWorldScenarios();
    }
    
    private static void filterAndCollectExample() {
        System.out.println("\n--- Example 1: Filter and Collect ---");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Anna", "Andrew", "Diana");
        
        // Filter names starting with 'A' and collect to list
        List<String> aNames = names.stream()
            .filter(name -> name.startsWith("A"))
            .collect(Collectors.toList());
        
        System.out.println("Original names: " + names);
        System.out.println("Names starting with 'A': " + aNames);
        
        // Filter and collect to different collection types
        Set<String> longNames = names.stream()
            .filter(name -> name.length() > 5)
            .collect(Collectors.toSet());
        
        System.out.println("Long names (as Set): " + longNames);
    }
    
    private static void mapAndReduceExample() {
        System.out.println("\n--- Example 2: Map and Reduce ---");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        // Calculate sum of squares
        int sumOfSquares = numbers.stream()
            .map(n -> n * n)
            .reduce(0, Integer::sum);
        
        System.out.println("Numbers: " + numbers);
        System.out.println("Sum of squares: " + sumOfSquares);
        
        // Calculate product of doubled numbers
        int productOfDoubled = numbers.stream()
            .map(n -> n * 2)
            .reduce(1, (a, b) -> a * b);
        
        System.out.println("Product of doubled numbers: " + productOfDoubled);
        
        // String transformation and concatenation
        List<String> words = Arrays.asList("hello", "world", "java", "streams");
        String upperCaseConcat = words.stream()
            .map(String::toUpperCase)
            .reduce("", (a, b) -> a + " " + b);
        
        System.out.println("Uppercase concatenation: " + upperCaseConcat.trim());
    }
    
    private static void sortingAndLimitingExample() {
        System.out.println("\n--- Example 3: Sorting and Limiting ---");
        
        List<String> words = Arrays.asList("banana", "apple", "pear", "kiwi", "orange", "grape");
        
        // Sort alphabetically and take first 3
        List<String> sortedLimited = words.stream()
            .sorted()
            .limit(3)
            .collect(Collectors.toList());
        
        System.out.println("Original words: " + words);
        System.out.println("Sorted and limited to 3: " + sortedLimited);
        
        // Sort by length (descending) and take first 4
        List<String> sortedByLength = words.stream()
            .sorted(Comparator.comparing(String::length).reversed())
            .limit(4)
            .collect(Collectors.toList());
        
        System.out.println("Sorted by length (desc) and limited to 4: " + sortedByLength);
    }
    
    private static void distinctAndCountExample() {
        System.out.println("\n--- Example 4: Distinct and Count ---");
        
        List<Integer> numbersWithDuplicates = Arrays.asList(1, 2, 2, 3, 3, 3, 4, 4, 4, 4);
        
        // Count unique numbers
        long uniqueCount = numbersWithDuplicates.stream()
            .distinct()
            .count();
        
        System.out.println("Numbers with duplicates: " + numbersWithDuplicates);
        System.out.println("Unique count: " + uniqueCount);
        
        // Get distinct numbers as list
        List<Integer> distinctNumbers = numbersWithDuplicates.stream()
            .distinct()
            .collect(Collectors.toList());
        
        System.out.println("Distinct numbers: " + distinctNumbers);
        
        // Count occurrences using grouping
        Map<Integer, Long> occurrences = numbersWithDuplicates.stream()
            .collect(Collectors.groupingBy(
                n -> n,
                Collectors.counting()
            ));
        
        System.out.println("Occurrences: " + occurrences);
    }
    
    private static void complexProcessingExample() {
        System.out.println("\n--- Example 5: Complex Processing ---");
        
        List<Person> people = Arrays.asList(
            new Person("Alice", 30, "Engineer"),
            new Person("Bob", 25, "Designer"),
            new Person("Charlie", 35, "Manager"),
            new Person("Diana", 28, "Engineer"),
            new Person("Eve", 32, "Designer")
        );
        
        System.out.println("Original people:");
        people.forEach(System.out::println);
        
        // Find engineers older than 25, sort by age, get names
        List<String> seniorEngineers = people.stream()
            .filter(p -> p.getJob().equals("Engineer"))
            .filter(p -> p.getAge() > 25)
            .sorted(Comparator.comparing(Person::getAge))
            .map(Person::getName)
            .collect(Collectors.toList());
        
        System.out.println("Senior engineers: " + seniorEngineers);
        
        // Group by job and calculate average age
        Map<String, Double> averageAgeByJob = people.stream()
            .collect(Collectors.groupingBy(
                Person::getJob,
                Collectors.averagingInt(Person::getAge)
            ));
        
        System.out.println("Average age by job: " + averageAgeByJob);
        
        // Find oldest person in each job category
        Map<String, Optional<Person>> oldestByJob = people.stream()
            .collect(Collectors.groupingBy(
                Person::getJob,
                Collectors.maxBy(Comparator.comparing(Person::getAge))
            ));
        
        System.out.println("Oldest person by job:");
        oldestByJob.forEach((job, person) -> 
            System.out.println(job + ": " + person.orElse(null))
        );
    }
    
    private static void realWorldScenarios() {
        System.out.println("\n--- Real World Scenarios ---");
        
        // Scenario 1: Processing sales data
        List<Sale> sales = Arrays.asList(
            new Sale("Product A", 100.0, "Q1"),
            new Sale("Product B", 150.0, "Q1"),
            new Sale("Product A", 120.0, "Q2"),
            new Sale("Product C", 200.0, "Q2"),
            new Sale("Product B", 180.0, "Q2")
        );
        
        System.out.println("Sales data:");
        sales.forEach(System.out::println);
        
        // Total sales by product
        Map<String, Double> salesByProduct = sales.stream()
            .collect(Collectors.groupingBy(
                Sale::getProduct,
                Collectors.summingDouble(Sale::getAmount)
            ));
        
        System.out.println("Total sales by product: " + salesByProduct);
        
        // Top selling products
        List<String> topProducts = salesByProduct.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .limit(2)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
        
        System.out.println("Top 2 products: " + topProducts);
        
        // Scenario 2: Text processing
        List<String> sentences = Arrays.asList(
            "Java streams are powerful",
            "Functional programming is elegant",
            "Streams make code more readable",
            "Java 8 introduced many features"
        );
        
        // Find all unique words longer than 4 characters
        List<String> longWords = sentences.stream()
            .flatMap(sentence -> Arrays.stream(sentence.split("\\s+")))
            .map(word -> word.toLowerCase().replaceAll("[^a-zA-Z]", ""))
            .filter(word -> word.length() > 4)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
        
        System.out.println("Long words (>4 chars): " + longWords);
        
        // Word frequency
        Map<String, Long> wordFrequency = sentences.stream()
            .flatMap(sentence -> Arrays.stream(sentence.split("\\s+")))
            .map(word -> word.toLowerCase().replaceAll("[^a-zA-Z]", ""))
            .collect(Collectors.groupingBy(
                word -> word,
                Collectors.counting()
            ));
        
        System.out.println("Word frequency: " + wordFrequency);
    }
    
    // Helper classes
    static class Person {
        private String name;
        private int age;
        private String job;
        
        public Person(String name, int age, String job) {
            this.name = name;
            this.age = age;
            this.job = job;
        }
        
        public String getName() { return name; }
        public int getAge() { return age; }
        public String getJob() { return job; }
        
        @Override
        public String toString() {
            return String.format("Person{name='%s', age=%d, job='%s'}", name, age, job);
        }
    }
    
    static class Sale {
        private String product;
        private double amount;
        private String quarter;
        
        public Sale(String product, double amount, String quarter) {
            this.product = product;
            this.amount = amount;
            this.quarter = quarter;
        }
        
        public String getProduct() { return product; }
        public double getAmount() { return amount; }
        public String getQuarter() { return quarter; }
        
        @Override
        public String toString() {
            return String.format("Sale{product='%s', amount=%.2f, quarter='%s'}", product, amount, quarter);
        }
    }
} 
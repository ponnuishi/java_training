package com.training.optional.advanced;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Advanced Optional Examples demonstrating integration with streams,
 * performance considerations, and best practices
 */
public class OptionalAdvancedExample {
    public static void main(String[] args) {
        System.out.println("=== Advanced Optional Examples ===");
        
        streamIntegration();
        primitiveOptionals();
        combiningOptionals();
        performanceConsiderations();
        bestPracticesAndMistakes();
    }
    
    private static void streamIntegration() {
        System.out.println("\n--- Stream Integration ---");
        
        // List of Optionals
        List<Optional<String>> optionals = Arrays.asList(
            Optional.of("hello"),
            Optional.empty(),
            Optional.of("world"),
            Optional.empty(),
            Optional.of("java")
        );
        
        // Extract present values using flatMap (Java 9+)
        List<String> presentValues = optionals.stream()
            .flatMap(Optional::stream)
            .collect(Collectors.toList());
        
        System.out.println("Present values: " + presentValues);
        
        // Working with Optional results from stream operations
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date");
        
        // Find first word longer than 5 characters
        Optional<String> longWord = words.stream()
            .filter(word -> word.length() > 5)
            .findFirst();
        
        System.out.println("First long word: " + longWord.orElse("None found"));
        
        // Transform stream results
        List<String> upperCaseWords = words.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        
        Optional<String> firstUpperCase = upperCaseWords.stream().findFirst();
        System.out.println("First uppercase word: " + firstUpperCase.orElse("None"));
        
        // Collecting to Optional
        Optional<String> concatenated = words.stream()
            .reduce((a, b) -> a + ", " + b);
        
        System.out.println("Concatenated: " + concatenated.orElse("Empty list"));
    }
    
    private static void primitiveOptionals() {
        System.out.println("\n--- Primitive Optionals ---");
        
        // OptionalInt
        OptionalInt optInt = OptionalInt.of(42);
        OptionalInt emptyInt = OptionalInt.empty();
        
        System.out.println("OptionalInt present: " + optInt.isPresent());
        System.out.println("OptionalInt value: " + optInt.orElse(0));
        System.out.println("Empty OptionalInt: " + emptyInt.orElse(-1));
        
        // OptionalLong
        OptionalLong optLong = OptionalLong.of(123456789L);
        System.out.println("OptionalLong: " + optLong.orElse(0L));
        
        // OptionalDouble
        OptionalDouble optDouble = OptionalDouble.of(3.14159);
        System.out.println("OptionalDouble: " + optDouble.orElse(0.0));
        
        // Working with primitive streams
        int[] numbers = {1, 2, 3, 4, 5};
        OptionalInt max = Arrays.stream(numbers).max();
        OptionalInt min = Arrays.stream(numbers).min();
        OptionalDouble average = Arrays.stream(numbers).average();
        
        System.out.println("Max: " + max.orElse(0));
        System.out.println("Min: " + min.orElse(0));
        System.out.println("Average: " + average.orElse(0.0));
        
        // Converting between Optional types
        Optional<Integer> boxedMax = max.stream().boxed().findFirst();
        System.out.println("Boxed max: " + boxedMax.orElse(0));
    }
    
    private static void combiningOptionals() {
        System.out.println("\n--- Combining Optionals ---");
        
        Optional<String> opt1 = Optional.of("Hello");
        Optional<String> opt2 = Optional.of("World");
        Optional<String> empty = Optional.empty();
        
        // Combining two Optionals
        Optional<String> combined = opt1.flatMap(s1 -> 
            opt2.map(s2 -> s1 + " " + s2)
        );
        System.out.println("Combined: " + combined.orElse("Failed to combine"));
        
        // Using or() method (Java 9+)
        Optional<String> result1 = empty.or(() -> opt1);
        System.out.println("Using or(): " + result1.orElse("None"));
        
        Optional<String> result2 = opt1.or(() -> opt2);
        System.out.println("First or second: " + result2.orElse("None"));
        
        // Chaining multiple fallbacks
        Optional<String> config = getConfigValue("primary")
            .or(() -> getConfigValue("secondary"))
            .or(() -> getConfigValue("default"));
        
        System.out.println("Config value: " + config.orElse("Hard-coded default"));
        
        // Combining multiple Optionals with validation
        Optional<Person> person = createPerson("John", "Doe", 30);
        System.out.println("Created person: " + person.orElse(null));
        
        Optional<Person> invalidPerson = createPerson("", "Doe", -5);
        System.out.println("Invalid person: " + invalidPerson.orElse(null));
    }
    
    private static void performanceConsiderations() {
        System.out.println("\n--- Performance Considerations ---");
        
        // Expensive operation example
        System.out.println("Demonstrating orElse vs orElseGet:");
        
        Optional<String> value = Optional.of("Present");
        
        // orElse always executes the expression
        String result1 = value.orElse(expensiveOperation("orElse"));
        System.out.println("Result 1: " + result1);
        
        // orElseGet only executes if Optional is empty
        String result2 = value.orElseGet(() -> expensiveOperation("orElseGet"));
        System.out.println("Result 2: " + result2);
        
        // With empty Optional
        Optional<String> empty = Optional.empty();
        
        System.out.println("With empty Optional:");
        String result3 = empty.orElse(expensiveOperation("orElse empty"));
        System.out.println("Result 3: " + result3);
        
        String result4 = empty.orElseGet(() -> expensiveOperation("orElseGet empty"));
        System.out.println("Result 4: " + result4);
        
        // Memory considerations
        System.out.println("\nMemory considerations:");
        Optional<String> opt = Optional.of("value");
        System.out.println("Optional overhead exists, but usually negligible");
        System.out.println("Consider primitive optionals for better performance");
    }
    
    private static void bestPracticesAndMistakes() {
        System.out.println("\n--- Best Practices and Common Mistakes ---");
        
        // Good practices
        System.out.println("✓ Good practices:");
        
        // 1. Use Optional as return type
        Optional<String> result = findUserName("john");
        result.ifPresent(name -> System.out.println("Found: " + name));
        
        // 2. Use orElse for simple defaults
        String name = result.orElse("Anonymous");
        System.out.println("Name: " + name);
        
        // 3. Use method chaining
        String processed = Optional.of("  hello  ")
            .filter(s -> !s.trim().isEmpty())
            .map(String::trim)
            .map(String::toUpperCase)
            .orElse("EMPTY");
        System.out.println("Processed: " + processed);
        
        // Common mistakes to avoid
        System.out.println("\n✗ Common mistakes:");
        
        // 1. Don't use Optional for fields
        System.out.println("Don't use Optional for class fields");
        
        // 2. Don't use get() without checking
        try {
            Optional<String> empty = Optional.empty();
            // String value = empty.get(); // This would throw exception
            System.out.println("Always check before using get()");
        } catch (Exception e) {
            System.out.println("Exception avoided");
        }
        
        // 3. Don't use Optional.of() with nullable values
        try {
            String nullValue = null;
            // Optional<String> opt = Optional.of(nullValue); // This would throw NPE
            Optional<String> opt = Optional.ofNullable(nullValue); // Correct way
            System.out.println("Use ofNullable for potentially null values");
        } catch (Exception e) {
            System.out.println("NPE avoided");
        }
        
        // 4. Don't use Optional for collections
        System.out.println("Return empty collections instead of Optional<Collection>");
        List<String> emptyList = Collections.emptyList(); // Better than Optional<List<String>>
        
        // 5. Prefer functional style
        Optional<String> opt = Optional.of("test");
        
        // Instead of this:
        if (opt.isPresent()) {
            System.out.println("Value: " + opt.get());
        }
        
        // Do this:
        opt.ifPresent(value -> System.out.println("Value: " + value));
    }
    
    // Helper methods
    private static Optional<String> getConfigValue(String key) {
        Map<String, String> config = Map.of(
            "secondary", "Secondary Value",
            "default", "Default Value"
        );
        return Optional.ofNullable(config.get(key));
    }
    
    private static Optional<Person> createPerson(String firstName, String lastName, int age) {
        if (firstName == null || firstName.trim().isEmpty()) {
            return Optional.empty();
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            return Optional.empty();
        }
        if (age < 0 || age > 150) {
            return Optional.empty();
        }
        return Optional.of(new Person(firstName, lastName, age));
    }
    
    private static String expensiveOperation(String context) {
        System.out.println("  Executing expensive operation for: " + context);
        // Simulate expensive operation
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Expensive result for " + context;
    }
    
    private static Optional<String> findUserName(String username) {
        // Simulate database lookup
        Map<String, String> users = Map.of(
            "john", "John Doe",
            "jane", "Jane Smith"
        );
        return Optional.ofNullable(users.get(username));
    }
    
    // Helper class
    static class Person {
        private String firstName;
        private String lastName;
        private int age;
        
        public Person(String firstName, String lastName, int age) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
        }
        
        @Override
        public String toString() {
            return String.format("Person{firstName='%s', lastName='%s', age=%d}", 
                firstName, lastName, age);
        }
    }
} 
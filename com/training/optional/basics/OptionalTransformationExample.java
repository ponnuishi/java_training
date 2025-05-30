package com.training.optional.basics;

import java.util.Optional;

/**
 * Examples demonstrating Optional transformation methods: map, flatMap, and filter
 */
public class OptionalTransformationExample {
    public static void main(String[] args) {
        System.out.println("=== Optional Transformation Examples ===");
        
        mapExample();
        flatMapExample();
        filterExample();
        chainingOperations();
    }
    
    private static void mapExample() {
        System.out.println("\n--- map() Examples ---");
        
        Optional<String> name = Optional.of("john doe");
        Optional<String> emptyName = Optional.empty();
        
        // Transform string to uppercase
        Optional<String> upperName = name.map(String::toUpperCase);
        Optional<String> upperEmpty = emptyName.map(String::toUpperCase);
        
        System.out.println("Original: " + name);
        System.out.println("Uppercase: " + upperName);
        System.out.println("Empty mapped: " + upperEmpty);
        
        // Transform string to its length
        Optional<Integer> nameLength = name.map(String::length);
        Optional<Integer> emptyLength = emptyName.map(String::length);
        
        System.out.println("Name length: " + nameLength);
        System.out.println("Empty length: " + emptyLength);
        
        // Chain multiple map operations
        Optional<String> processed = name
            .map(String::trim)
            .map(String::toUpperCase)
            .map(s -> s.replace(" ", "_"));
        
        System.out.println("Chained transformations: " + processed);
        
        // Working with numbers
        Optional<Integer> number = Optional.of(42);
        Optional<Integer> doubled = number.map(n -> n * 2);
        Optional<String> numberAsString = number.map(Object::toString);
        
        System.out.println("Number: " + number);
        System.out.println("Doubled: " + doubled);
        System.out.println("As string: " + numberAsString);
    }
    
    private static void flatMapExample() {
        System.out.println("\n--- flatMap() Examples ---");
        
        Optional<String> name = Optional.of("Alice");
        Optional<String> emptyName = Optional.empty();
        
        // flatMap with method that returns Optional
        Optional<String> greeting = name.flatMap(n -> createGreeting(n));
        Optional<String> emptyGreeting = emptyName.flatMap(n -> createGreeting(n));
        
        System.out.println("Greeting: " + greeting);
        System.out.println("Empty greeting: " + emptyGreeting);
        
        // Difference between map and flatMap
        System.out.println("\nDifference between map and flatMap:");
        
        // Using map - results in Optional<Optional<String>>
        Optional<Optional<String>> nestedOptional = name.map(n -> createGreeting(n));
        System.out.println("Using map: " + nestedOptional);
        
        // Using flatMap - results in Optional<String>
        Optional<String> flatOptional = name.flatMap(n -> createGreeting(n));
        System.out.println("Using flatMap: " + flatOptional);
        
        // Chaining flatMap operations
        Optional<String> result = name
            .flatMap(n -> createGreeting(n))
            .flatMap(g -> addExclamation(g));
        
        System.out.println("Chained flatMap: " + result);
        
        // Working with nested objects
        Optional<Person> person = Optional.of(new Person("Bob", new Address("123 Main St")));
        Optional<String> street = person.flatMap(p -> p.getAddress()).flatMap(a -> a.getStreet());
        
        System.out.println("Person street: " + street);
    }
    
    private static void filterExample() {
        System.out.println("\n--- filter() Examples ---");
        
        Optional<String> name = Optional.of("Alice");
        Optional<String> shortName = Optional.of("Al");
        Optional<String> emptyName = Optional.empty();
        
        // Filter by length
        Optional<String> longName = name.filter(n -> n.length() > 3);
        Optional<String> filteredShortName = shortName.filter(n -> n.length() > 3);
        Optional<String> filteredEmpty = emptyName.filter(n -> n.length() > 3);
        
        System.out.println("Name (length > 3): " + longName);
        System.out.println("Short name (length > 3): " + filteredShortName);
        System.out.println("Empty name (length > 3): " + filteredEmpty);
        
        // Filter by starting letter
        Optional<String> startsWithA = name.filter(n -> n.startsWith("A"));
        Optional<String> startsWithB = name.filter(n -> n.startsWith("B"));
        
        System.out.println("Starts with A: " + startsWithA);
        System.out.println("Starts with B: " + startsWithB);
        
        // Filter numbers
        Optional<Integer> number = Optional.of(42);
        Optional<Integer> evenNumber = number.filter(n -> n % 2 == 0);
        Optional<Integer> oddNumber = number.filter(n -> n % 2 != 0);
        
        System.out.println("Even number: " + evenNumber);
        System.out.println("Odd number: " + oddNumber);
        
        // Complex filtering
        Optional<String> email = Optional.of("user@example.com");
        Optional<String> validEmail = email.filter(e -> e.contains("@") && e.contains("."));
        
        System.out.println("Valid email: " + validEmail);
        
        Optional<String> invalidEmail = Optional.of("invalid-email");
        Optional<String> filteredInvalid = invalidEmail.filter(e -> e.contains("@") && e.contains("."));
        
        System.out.println("Invalid email filtered: " + filteredInvalid);
    }
    
    private static void chainingOperations() {
        System.out.println("\n--- Chaining Operations ---");
        
        Optional<String> input = Optional.of("  hello world  ");
        
        // Chain multiple operations
        Optional<String> result = input
            .filter(s -> !s.trim().isEmpty())    // Check not empty after trim
            .map(String::trim)                   // Remove whitespace
            .map(String::toUpperCase)            // Convert to uppercase
            .filter(s -> s.length() > 5)        // Check length
            .map(s -> s.replace(" ", "_"));      // Replace spaces
        
        System.out.println("Input: " + input);
        System.out.println("Result: " + result);
        
        // Example with empty result due to filtering
        Optional<String> shortInput = Optional.of("hi");
        Optional<String> filteredOut = shortInput
            .filter(s -> s.length() > 5)        // This will filter out "hi"
            .map(String::toUpperCase);
        
        System.out.println("Short input: " + shortInput);
        System.out.println("Filtered out: " + filteredOut);
        
        // Real-world example: processing user input
        Optional<String> userInput = Optional.of("  John.Doe@Example.COM  ");
        Optional<String> processedEmail = userInput
            .filter(s -> !s.trim().isEmpty())
            .map(String::trim)
            .map(String::toLowerCase)
            .filter(s -> s.contains("@"))
            .filter(s -> s.contains("."));
        
        System.out.println("User input: " + userInput);
        System.out.println("Processed email: " + processedEmail);
        
        // Using orElse at the end
        String finalResult = processedEmail.orElse("invalid@email.com");
        System.out.println("Final result: " + finalResult);
    }
    
    // Helper methods
    private static Optional<String> createGreeting(String name) {
        if (name != null && !name.isEmpty()) {
            return Optional.of("Hello, " + name + "!");
        }
        return Optional.empty();
    }
    
    private static Optional<String> addExclamation(String text) {
        if (text != null && !text.isEmpty()) {
            return Optional.of(text + "!");
        }
        return Optional.empty();
    }
    
    // Helper classes
    static class Person {
        private String name;
        private Address address;
        
        public Person(String name, Address address) {
            this.name = name;
            this.address = address;
        }
        
        public String getName() { return name; }
        public Optional<Address> getAddress() { return Optional.ofNullable(address); }
    }
    
    static class Address {
        private String street;
        
        public Address(String street) {
            this.street = street;
        }
        
        public Optional<String> getStreet() { return Optional.ofNullable(street); }
    }
} 
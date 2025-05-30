package com.training.debug.advanced;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.training.debug.util.Person;

/**
 * Collection Debugging Example
 * Demonstrates debugging collections and arrays
 */
public class CollectionDebuggingExample {
    public static void main(String[] args) {
        System.out.println("=== Collection Debugging Example ===");
        
        List<Person> people = Arrays.asList(
            new Person("Alice", 25),
            new Person("Bob", 30),
            new Person("Charlie", 35)
        );
        
        Map<String, Integer> ageMap = new HashMap<>();
        for (Person person : people) {
            ageMap.put(person.getName(), person.getAge());
        }
        
        // ← Set breakpoint here to inspect collections
        // - people.size()
        // - people.get(0).getName()
        // - ageMap.keySet()
        // - ageMap.values()
        // - ageMap.containsKey("Alice")
        
        System.out.println("People count: " + people.size());
        System.out.println("First person: " + people.get(0).getName());
        System.out.println("Age map keys: " + ageMap.keySet());
        System.out.println("Age map values: " + ageMap.values());
        System.out.println("Age map: " + ageMap);
        
        // More collection operations to debug
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        String[] nameArray = names.toArray(new String[0]);
        
        // ← Set another breakpoint here
        System.out.println("Names list: " + names);
        System.out.println("Names array length: " + nameArray.length);
        
        System.out.println("Collection debugging example completed.");
    }
} 
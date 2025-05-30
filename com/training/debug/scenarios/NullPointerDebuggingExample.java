package com.training.debug.scenarios;

import com.training.debug.util.Person;

/**
 * NullPointerException Debugging Example
 * Demonstrates debugging null pointer exceptions
 */
public class NullPointerDebuggingExample {
    public static void main(String[] args) {
        System.out.println("=== NullPointerException Debugging Example ===");
        
        try {
            Person person = createPerson("John", null);  // email is null
            System.out.println(person.getEmail().length());  // NPE
        } catch (NullPointerException e) {
            System.out.println("Caught NullPointerException: " + e.getMessage());
        }
        
        // Another example
        try {
            String[] names = {"Alice", "Bob", null, "David"};
            for (String name : names) {
                System.out.println("Name length: " + name.length());  // NPE on null
            }
        } catch (NullPointerException e) {
            System.out.println("Caught NullPointerException: " + e.getMessage());
        }
        
        System.out.println("NullPointerException debugging example completed.");
    }
    
    public static Person createPerson(String name, String email) {
        Person person = new Person();
        person.setName(name);
        person.setEmail(email);  // ← Set breakpoint here
        return person;
    }
} 
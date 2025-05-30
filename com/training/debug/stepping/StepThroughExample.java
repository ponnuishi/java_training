package com.training.debug.stepping;

import java.util.Arrays;
import com.training.debug.util.Person;

/**
 * Step Through Example
 * Demonstrates step-through debugging
 */
public class StepThroughExample {
    public static void main(String[] args) {
        System.out.println("=== Step Through Example ===");
        
        String name = "John";
        int age = 25;
        
        Person person = createPerson(name, age);  // ← Step Into
        printPersonInfo(person);                  // ← Step Over
        
        System.out.println("Step through example completed.");
    }
    
    public static Person createPerson(String name, int age) {
        Person person = new Person();  // ← Step Into
        person.setName(name);          // ← Step Into
        person.setAge(age);            // ← Step Into
        return person;                 // ← Step Out
    }
    
    public static void printPersonInfo(Person person) {
        System.out.println("Name: " + person.getName());
        System.out.println("Age: " + person.getAge());
    }
} 
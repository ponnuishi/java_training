package com.training.annotation.processing;

/**
 * Person class with GenerateBuilder annotation
 * This would trigger the annotation processor to generate a builder
 */
@GenerateBuilder
public class Person {
    private String name;
    private int age;
    private String email;
    
    // Default constructor
    public Person() {}
    
    // Constructor with all fields
    public Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + ", email='" + email + "'}";
    }
} 
package com.training.annotation.processing;

/**
 * This is the generated builder class that would be created by the annotation processor
 * In a real scenario, this file would be generated automatically when processing @GenerateBuilder
 */
public class GeneratedPersonBuilder {
    private String name;
    private int age;
    private String email;
    
    public GeneratedPersonBuilder name(String name) {
        this.name = name;
        return this;
    }
    
    public GeneratedPersonBuilder age(int age) {
        this.age = age;
        return this;
    }
    
    public GeneratedPersonBuilder email(String email) {
        this.email = email;
        return this;
    }
    
    public Person build() {
        return new Person(name, age, email);
    }
} 
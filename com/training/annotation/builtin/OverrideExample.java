package com.training.annotation.builtin;

/**
 * Demonstrates the usage of @Override annotation
 */
public class OverrideExample {
    
    public static void main(String[] args) {
        Animal animal = new Animal();
        Dog dog = new Dog();
        
        animal.makeSound();  // Output: Some sound
        dog.makeSound();     // Output: Woof!
    }
}

class Animal {
    public void makeSound() {
        System.out.println("Some sound");
    }
}

class Dog extends Animal {
    @Override  // Ensures we're actually overriding a method
    public void makeSound() {
        System.out.println("Woof!");
    }
    
    // This would cause a compile error because there's no such method in Animal
//     @Override
//     public void makeNoise() { }  // Error: method does not override
} 
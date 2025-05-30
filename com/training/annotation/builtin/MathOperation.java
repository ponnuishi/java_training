package com.training.annotation.builtin;

@FunctionalInterface
public interface MathOperation {
    int operate(int a, int b);

    // Can have default methods
    default int add(int a, int b){
        return a + b;
    }

    // Can have static methods
    static MathOperation getDefault() {
        return (a, b) -> a + b;
    }

    // Cannot have more than one abstract method
    // int anotherMethod(int x);  // This would cause compile error
}

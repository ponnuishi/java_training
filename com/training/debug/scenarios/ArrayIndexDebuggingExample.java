package com.training.debug;

/**
 * ArrayIndexOutOfBoundsException Debugging Example
 * Demonstrates debugging array index issues
 */
public class ArrayIndexDebuggingExample {
    public static void main(String[] args) {
        System.out.println("=== ArrayIndexOutOfBoundsException Debugging Example ===");
        
        try {
            int[] numbers = {1, 2, 3};
            int sum = calculateSum(numbers);
            System.out.println("Sum: " + sum);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught ArrayIndexOutOfBoundsException: " + e.getMessage());
        }
        
        // Another example
        try {
            String[] names = {"Alice", "Bob", "Charlie"};
            for (int i = 0; i <= names.length; i++) {  // Bug: should be < not <=
                System.out.println("Name " + i + ": " + names[i]);  // ArrayIndexOutOfBoundsException
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught ArrayIndexOutOfBoundsException: " + e.getMessage());
        }
        
        System.out.println("ArrayIndexOutOfBoundsException debugging example completed.");
    }
    
    public static int calculateSum(int[] numbers) {
        int sum = 0;
        for (int i = 0; i < numbers.length; i++) {  // ← Bug: should be < not <=
            sum += numbers[i];  // ← ArrayIndexOutOfBoundsException
        }
        return sum;
    }
} 
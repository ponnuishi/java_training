/**
 * ArrayBasics.java
 * This program demonstrates the fundamental concepts of arrays in Java
 * including declaration, initialization, and basic operations.
 */
public class ArrayBasics {
    public static void main(String[] args) {
        // 1. Array Declaration and Initialization
        System.out.println("1. Array Declaration and Initialization");
        System.out.println("----------------------------------------");

        // Different ways to declare and initialize arrays
        int[] numbers1 = new int[5];                    // Declaration with size
        int[] numbers2 = {1, 2, 3, 4, 5};               // Declaration with values
        int[] numbers3 = new int[]{10, 20, 30, 40, 50}; // Declaration with size and values

        // 2. Accessing Array Elements
        System.out.println("\n2. Accessing Array Elements");
        System.out.println("----------------------------");

        // Accessing elements using index
        System.out.println("First element of numbers2: " + numbers2[0]);
        System.out.println("Last element of numbers2: " + numbers2[numbers2.length - 1]);

        // Modifying array elements
        numbers1[0] = 100;
        System.out.println("Modified first element of numbers1: " + numbers1[0]);

        // 3. Array Length Property
        System.out.println("\n3. Array Length Property");
        System.out.println("------------------------");
        System.out.println("Length of numbers1: " + numbers1.length);
        System.out.println("Length of numbers2: " + numbers2.length);

        // 4. Iterating Through Arrays
        System.out.println("\n4. Iterating Through Arrays");
        System.out.println("---------------------------");

        // Using traditional for loop
        System.out.println("Using traditional for loop:");
        for (int i = 0; i < numbers2.length; i++) {
            System.out.print(numbers2[i] + " ");
        }
        System.out.println();

        // Using enhanced for loop (for-each)
        System.out.println("\nUsing enhanced for loop:");
        for (int num : numbers2) {
            System.out.print(num + " ");
        }
        System.out.println();

        // 5. Common Pitfalls
        System.out.println("\n5. Common Pitfalls");
        System.out.println("-----------------");

        // Array Index Out of Bounds
        try {
            System.out.println("Attempting to access index 10 in numbers2:");
            System.out.println(numbers2[10]); // This will throw ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Null Array Reference
        int[] nullArray = null;
        try {
            System.out.println("\nAttempting to access null array:");
            System.out.println(nullArray.length); // This will throw NullPointerException
        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // 6. Array of Different Types
        System.out.println("\n6. Array of Different Types");
        System.out.println("--------------------------");

        // String array
        String[] names = {"Alice", "Bob", "Charlie"};
        System.out.println("String array elements:");
        for (String name : names) {
            System.out.print(name + " ");
        }
        System.out.println();

        // Double array
        double[] prices = {10.99, 20.50, 30.75};
        System.out.println("\nDouble array elements:");
        for (double price : prices) {
            System.out.print(price + " ");
        }
        System.out.println();

        // Boolean array
        boolean[] flags = {true, false, true};
        System.out.println("\nBoolean array elements:");
        for (boolean flag : flags) {
            System.out.print(flag + " ");
        }
        System.out.println();
    }
} 
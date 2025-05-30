/**
 * AdvancedArrays.java
 * This program demonstrates advanced array concepts including cloning,
 * comparison, utility methods, and best practices.
 */
import java.util.Arrays;
import java.util.Objects;

public class AdvancedArrays {
    public static void main(String[] args) {
        // 1. Array Cloning
        System.out.println("1. Array Cloning");
        System.out.println("---------------");

        int[] original = {1, 2, 3, 4, 5};
        System.out.println("Original array: " + Arrays.toString(original));

        // Different ways to clone arrays
        int[] clone1 = original.clone();
        int[] clone2 = Arrays.copyOf(original, original.length);
        int[] clone3 = new int[original.length];
        System.arraycopy(original, 0, clone3, 0, original.length);

        System.out.println("Clone 1 (using clone()): " + Arrays.toString(clone1));
        System.out.println("Clone 2 (using Arrays.copyOf): " + Arrays.toString(clone2));
        System.out.println("Clone 3 (using System.arraycopy): " + Arrays.toString(clone3));

        // Demonstrate that clones are independent
        clone1[0] = 100;
        System.out.println("\nAfter modifying clone1[0]:");
        System.out.println("Original: " + Arrays.toString(original));
        System.out.println("Clone 1: " + Arrays.toString(clone1));

        // 2. Array Comparison
        System.out.println("\n2. Array Comparison");
        System.out.println("-----------------");

        int[] arr1 = {1, 2, 3, 4, 5};
        int[] arr2 = {1, 2, 3, 4, 5};
        int[] arr3 = {5, 4, 3, 2, 1};

        System.out.println("arr1: " + Arrays.toString(arr1));
        System.out.println("arr2: " + Arrays.toString(arr2));
        System.out.println("arr3: " + Arrays.toString(arr3));

        System.out.println("\nUsing == operator:");
        System.out.println("arr1 == arr2: " + (arr1 == arr2));

        System.out.println("\nUsing Arrays.equals():");
        System.out.println("Arrays.equals(arr1, arr2): " + Arrays.equals(arr1, arr2));
        System.out.println("Arrays.equals(arr1, arr3): " + Arrays.equals(arr1, arr3));

        // 3. Deep Array Comparison
        System.out.println("\n3. Deep Array Comparison");
        System.out.println("----------------------");

        int[][] deepArr1 = {{1, 2}, {3, 4}};
        int[][] deepArr2 = {{1, 2}, {3, 4}};
        int[][] deepArr3 = {{1, 2}, {3, 5}};

        System.out.println("deepArr1: " + Arrays.deepToString(deepArr1));
        System.out.println("deepArr2: " + Arrays.deepToString(deepArr2));
        System.out.println("deepArr3: " + Arrays.deepToString(deepArr3));

        System.out.println("\nUsing Arrays.deepEquals():");
        System.out.println("Arrays.deepEquals(deepArr1, deepArr2): " +
                Arrays.deepEquals(deepArr1, deepArr2));
        System.out.println("Arrays.deepEquals(deepArr1, deepArr3): " +
                Arrays.deepEquals(deepArr1, deepArr3));

        // 4. Array Utility Methods
        System.out.println("\n4. Array Utility Methods");
        System.out.println("----------------------");

        int[] numbers = {5, 2, 8, 1, 9, 3};
        System.out.println("Original array: " + Arrays.toString(numbers));

        // Sorting
        Arrays.sort(numbers);
        System.out.println("After sorting: " + Arrays.toString(numbers));

        // Filling
        int[] filledArray = new int[5];
        Arrays.fill(filledArray, 42);
        System.out.println("Filled array: " + Arrays.toString(filledArray));

        // Partial filling
        int[] partialFill = new int[5];
        Arrays.fill(partialFill, 1, 3, 99);
        System.out.println("Partially filled array: " + Arrays.toString(partialFill));

        // 5. Array Best Practices
        System.out.println("\n5. Array Best Practices");
        System.out.println("---------------------");

        // Defensive copying
        int[] sensitiveData = {1, 2, 3, 4, 5};
        int[] safeCopy = defensiveCopy(sensitiveData);

        System.out.println("Original sensitive data: " + Arrays.toString(sensitiveData));
        System.out.println("Safe copy: " + Arrays.toString(safeCopy));

        // Modifying the safe copy doesn't affect original
        safeCopy[0] = 100;
        System.out.println("\nAfter modifying safe copy:");
        System.out.println("Original sensitive data: " + Arrays.toString(sensitiveData));
        System.out.println("Safe copy: " + Arrays.toString(safeCopy));

        // 6. Array Performance Considerations
        System.out.println("\n6. Array Performance Considerations");
        System.out.println("--------------------------------");

        // Demonstrating array capacity vs size
        int[] dynamicArray = new int[10];
        int size = 0;

        // Simulating dynamic array growth
        for (int i = 0; i < 15; i++) {
            if (size == dynamicArray.length) {
                dynamicArray = growArray(dynamicArray);
            }
            dynamicArray[size++] = i;
        }

        System.out.println("Final array capacity: " + dynamicArray.length);
        System.out.println("Actual size used: " + size);
        System.out.println("Array contents: " + Arrays.toString(
                Arrays.copyOf(dynamicArray, size)));
    }

    // Method demonstrating defensive copying
    private static int[] defensiveCopy(int[] original) {
        return Arrays.copyOf(original, original.length);
    }

    // Method to grow array capacity
    private static int[] growArray(int[] original) {
        int newCapacity = original.length * 2;
        System.out.println("Growing array from " + original.length +
                " to " + newCapacity + " elements");
        return Arrays.copyOf(original, newCapacity);
    }

    // Custom array equality method
    private static boolean customArrayEquals(int[] arr1, int[] arr2) {
        if (arr1 == arr2) return true;
        if (arr1 == null || arr2 == null) return false;
        if (arr1.length != arr2.length) return false;

        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) return false;
        }

        return true;
    }
}
/**
 * ArrayOperations.java
 * This program demonstrates common operations performed on arrays
 * including finding max/min, calculating sum/average, and searching.
 */
public class ArrayOperations {
    public static void main(String[] args) {
        // Sample array for operations
        int[] numbers = {45, 12, 89, 34, 67, 23, 90, 1, 56, 78};

        // 1. Finding Maximum and Minimum Values
        System.out.println("1. Finding Maximum and Minimum Values");
        System.out.println("------------------------------------");

        int max = numbers[0];
        int min = numbers[0];

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > max) {
                max = numbers[i];
            }
            if (numbers[i] < min) {
                min = numbers[i];
            }
        }

        System.out.println("Array: " + arrayToString(numbers));
        System.out.println("Maximum value: " + max);
        System.out.println("Minimum value: " + min);

        // 2. Calculating Sum and Average
        System.out.println("\n2. Calculating Sum and Average");
        System.out.println("----------------------------");

        int sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        double average = (double) sum / numbers.length;

        System.out.println("Sum of all elements: " + sum);
        System.out.println("Average of all elements: " + average);

        // 3. Linear Search
        System.out.println("\n3. Linear Search");
        System.out.println("---------------");

        int searchValue = 67;
        int searchIndex = linearSearch(numbers, searchValue);

        if (searchIndex != -1) {
            System.out.println("Value " + searchValue + " found at index: " + searchIndex);
        } else {
            System.out.println("Value " + searchValue + " not found in the array");
        }

        // 4. Counting Occurrences
        System.out.println("\n4. Counting Occurrences");
        System.out.println("----------------------");

        int[] numbersWithDuplicates = {1, 2, 3, 2, 4, 2, 5, 2, 6};
        int targetValue = 2;
        int count = countOccurrences(numbersWithDuplicates, targetValue);

        System.out.println("Array: " + arrayToString(numbersWithDuplicates));
        System.out.println("Number of occurrences of " + targetValue + ": " + count);

        // 5. Array Reversal
        System.out.println("\n5. Array Reversal");
        System.out.println("----------------");

        System.out.println("Original array: " + arrayToString(numbers));
        reverseArray(numbers);
        System.out.println("Reversed array: " + arrayToString(numbers));

        // 6. Finding Second Largest Element
        System.out.println("\n6. Finding Second Largest Element");
        System.out.println("--------------------------------");

        int secondLargest = findSecondLargest(numbers);
        System.out.println("Second largest element: " + secondLargest);
    }

    // Helper method to convert array to string
    private static String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // Linear search implementation
    private static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // Count occurrences of a value in array
    private static int countOccurrences(int[] arr, int target) {
        int count = 0;
        for (int num : arr) {
            if (num == target) {
                count++;
            }
        }
        return count;
    }

    // Reverse array in-place
    private static void reverseArray(int[] arr) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            // Swap elements
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;

            // Move pointers
            left++;
            right--;
        }
    }

    // Find second largest element
    private static int findSecondLargest(int[] arr) {
        if (arr.length < 2) {
            throw new IllegalArgumentException("Array must have at least 2 elements");
        }

        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;

        for (int num : arr) {
            if (num > largest) {
                secondLargest = largest;
                largest = num;
            } else if (num > secondLargest && num != largest) {
                secondLargest = num;
            }
        }

        if (secondLargest == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("No second largest element found");
        }

        return secondLargest;
    }
} 
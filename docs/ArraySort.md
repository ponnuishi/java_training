# Java Array Sorting

## 📑 Table of Contents

- [Overview](#overview)
- [Basic Sorting](#basic-sorting)
- [Custom Sorting](#custom-sorting)
- [Common Use Cases](#common-use-cases)
- [Array Conversions](#array-conversions)
- [Performance Considerations](#performance-considerations)
- [Best Practices](#best-practices)

## Overview

Java provides several ways to sort arrays:

- `Arrays.sort()` for primitive and object arrays
- `Collections.sort()` for Lists
- Custom sorting using `Comparator`

## Basic Sorting

### Primitive Arrays

```java
import java.util.Arrays;

public class ArraySortExample {
    public static void main(String[] args) {
        // Integer array
        int[] numbers = {5, 2, 8, 1, 9};
        Arrays.sort(numbers);
        System.out.println("Sorted numbers: " + Arrays.toString(numbers));
        // Output: [1, 2, 5, 8, 9]

        // String array
        String[] fruits = {"banana", "apple", "cherry", "date"};
        Arrays.sort(fruits);
        System.out.println("Sorted fruits: " + Arrays.toString(fruits));
        // Output: [apple, banana, cherry, date]
    }
}
```

### Object Arrays

```java
import java.util.Arrays;

public class ObjectArraySort {
    public static void main(String[] args) {
        // Sorting Person objects by name
        Person[] people = {
            new Person("Bob", 25),
            new Person("Alice", 30),
            new Person("Charlie", 20)
        };

        // Sort using Comparable (Person class implements Comparable)
        Arrays.sort(people);
        System.out.println("Sorted by name: " + Arrays.toString(people));

        // Sort using Comparator
        Arrays.sort(people, (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge()));
        System.out.println("Sorted by age: " + Arrays.toString(people));
    }
}

class Person implements Comparable<Person> {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Person other) {
        return this.name.compareTo(other.name);
    }

    public int getAge() { return age; }

    @Override
    public String toString() {
        return name + "(" + age + ")";
    }
}
```

## Custom Sorting

### Using Comparator

```java
import java.util.Arrays;
import java.util.Comparator;

public class CustomSortExample {
    public static void main(String[] args) {
        String[] words = {"apple", "Banana", "cherry", "Date"};

        // Case-insensitive sort
        Arrays.sort(words, String.CASE_INSENSITIVE_ORDER);
        System.out.println("Case-insensitive sort: " + Arrays.toString(words));

        // Sort by length
        Arrays.sort(words, Comparator.comparingInt(String::length));
        System.out.println("Sort by length: " + Arrays.toString(words));

        // Sort by length, then alphabetically
        Arrays.sort(words,
            Comparator.comparingInt(String::length)
                     .thenComparing(String::compareToIgnoreCase));
        System.out.println("Sort by length then alphabetically: " + Arrays.toString(words));
    }
}
```

## Common Use Cases

### 1. Sorting Numbers

```java
// Ascending order
int[] numbers = {5, 2, 8, 1, 9};
Arrays.sort(numbers);

// Descending order
Integer[] numbers2 = {5, 2, 8, 1, 9};
Arrays.sort(numbers2, Collections.reverseOrder());
```

### 2. Sorting Strings

```java
// Alphabetical order
String[] names = {"John", "Alice", "Bob"};
Arrays.sort(names);

// Case-insensitive
Arrays.sort(names, String.CASE_INSENSITIVE_ORDER);
```

### 3. Sorting Custom Objects

```java
// Sort by multiple fields
class Student {
    String name;
    int grade;
    double gpa;

    // Constructor and getters omitted for brevity
}

Student[] students = {
    new Student("Alice", 10, 3.8),
    new Student("Bob", 9, 3.9),
    new Student("Charlie", 10, 3.7)
};

// Sort by grade (descending) then by GPA (descending)
Arrays.sort(students,
    Comparator.comparingInt(Student::getGrade)
              .reversed()
              .thenComparingDouble(Student::getGpa)
              .reversed());
```

## Array Conversions

### Converting Between Arrays and ArrayLists

```java
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayConversionExample {
    public static void main(String[] args) {
        // 1. Array to ArrayList
        String[] stringArray = {"apple", "banana", "cherry"};

        // Method 1: Using Arrays.asList()
        List<String> list1 = new ArrayList<>(Arrays.asList(stringArray));

        // Method 2: Using Stream API (for primitive arrays)
        int[] intArray = {1, 2, 3, 4, 5};
        List<Integer> list2 = Arrays.stream(intArray)
                                  .boxed()
                                  .collect(Collectors.toList());

        // 2. ArrayList to Array
        List<String> fruits = new ArrayList<>();
        fruits.add("apple");
        fruits.add("banana");

        // Method 1: Using toArray()
        String[] array1 = fruits.toArray(new String[0]);

        // Method 2: Using Stream API
        String[] array2 = fruits.stream().toArray(String[]::new);

        // Method 3: For primitive lists (e.g., List<Integer> to int[])
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        int[] intArray2 = numbers.stream()
                                .mapToInt(Integer::intValue)
                                .toArray();

        System.out.println("Array to List: " + list1);
        System.out.println("Int Array to List: " + list2);
        System.out.println("List to Array: " + Arrays.toString(array1));
        System.out.println("List to Int Array: " + Arrays.toString(intArray2));
    }
}
```

### Common Conversion Patterns

1. **Converting and Sorting**

```java
// Convert array to list, sort, and back to array
int[] numbers = {5, 2, 8, 1, 9};
List<Integer> list = Arrays.stream(numbers)
                          .boxed()
                          .collect(Collectors.toList());
Collections.sort(list);
int[] sortedNumbers = list.stream()
                         .mapToInt(Integer::intValue)
                         .toArray();
```

2. **Filtering During Conversion**

```java
// Filter even numbers while converting
int[] numbers = {1, 2, 3, 4, 5, 6};
int[] evenNumbers = Arrays.stream(numbers)
                         .filter(n -> n % 2 == 0)
                         .toArray();
```

### Best Practices for Conversions

1. **Choose the Right Method**

   ```java
   // For object arrays
   String[] array = {"a", "b", "c"};
   List<String> list = new ArrayList<>(Arrays.asList(array));

   // For primitive arrays
   int[] intArray = {1, 2, 3};
   List<Integer> intList = Arrays.stream(intArray)
                                .boxed()
                                .collect(Collectors.toList());
   ```

2. **Handle Null Values**

   ```java
   // Safe conversion with null check
   String[] array = {"a", null, "c"};
   List<String> list = Arrays.stream(array)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
   ```

3. **Avoid Common Pitfalls**

   ```java
   // ❌ Bad: Using Arrays.asList() directly
   List<String> list = Arrays.asList(array);  // Returns fixed-size list

   // ✅ Good: Create new ArrayList
   List<String> list = new ArrayList<>(Arrays.asList(array));  // Mutable list
   ```

## Performance Considerations

### Time Complexity

- `Arrays.sort()` uses a dual-pivot quicksort algorithm
- Average time complexity: O(n log n)
- Worst case: O(n²) for primitive arrays
- Stable sort for object arrays

### Memory Usage

- In-place sorting for primitive arrays
- Additional space for object arrays
- Consider using `Arrays.parallelSort()` for large arrays

## Best Practices

1. **Choose the Right Method**

   ```java
   // For primitive arrays
   int[] numbers = {5, 2, 8, 1, 9};
   Arrays.sort(numbers);

   // For object arrays
   String[] words = {"apple", "banana", "cherry"};
   Arrays.sort(words);

   // For custom sorting
   Arrays.sort(words, Comparator.comparingInt(String::length));
   ```

2. **Handle Null Values**

   ```java
   // Null-safe comparator
   Arrays.sort(array, Comparator.nullsLast(Comparator.naturalOrder()));
   ```

3. **Consider Parallel Sorting**

   ```java
   // For large arrays
   int[] largeArray = new int[1000000];
   Arrays.parallelSort(largeArray);
   ```

4. **Avoid Modifying During Sort**

   ```java
   // ❌ Bad: Modifying during sort
   Arrays.sort(array, (a, b) -> {
       array[0] = newValue;  // Don't modify array during sort
       return a.compareTo(b);
   });

   // ✅ Good: Pure comparison
   Arrays.sort(array, (a, b) -> a.compareTo(b));
   ```

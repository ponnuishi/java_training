# ArrayList vs Arrays in Java

## 📌 Overview

Both ArrayList and arrays are used to store collections of elements in Java, but they have different characteristics and use cases. Understanding when to use each is crucial for writing efficient and maintainable code.

## 🔍 Key Differences

| Feature       | Array                            | ArrayList                                             |
| ------------- | -------------------------------- | ----------------------------------------------------- |
| Size          | Fixed at creation                | Dynamic (grows/shrinks automatically)                 |
| Type          | Can be primitive or reference    | Only reference types (uses autoboxing for primitives) |
| Memory        | Contiguous memory allocation     | Dynamic memory allocation                             |
| Performance   | Faster for fixed-size operations | Slightly slower due to dynamic resizing               |
| Methods       | Basic length property            | Rich set of utility methods                           |
| Generics      | Not supported                    | Fully supports generics                               |
| Thread Safety | Not thread-safe                  | Not thread-safe (use Vector for thread safety)        |

## 💡 When to Use Arrays

### 1. Fixed-Size Collections

```java
// Good use case for arrays: Fixed-size data
public class GameBoard {
    private final int[][] board;  // Fixed-size game board

    public GameBoard(int size) {
        board = new int[size][size];  // Size won't change
    }
}
```

### 2. Performance-Critical Operations

```java
// Good use case: High-performance numerical computations
public class MatrixOperations {
    public double[] matrixMultiply(double[] vector, double[][] matrix) {
        double[] result = new double[vector.length];  // Fixed size, known at creation
        for (int i = 0; i < vector.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                result[i] += vector[j] * matrix[i][j];
            }
        }
        return result;
    }
}
```

### 3. Primitive Types

```java
// Good use case: Working with primitive types
public class ImageProcessor {
    private final int[] pixelData;  // More memory efficient for primitives

    public ImageProcessor(int width, int height) {
        pixelData = new int[width * height];
    }
}
```

### 4. Multi-dimensional Data

```java
// Good use case: Multi-dimensional data with fixed dimensions
public class ChessGame {
    private final Piece[][] board = new Piece[8][8];  // Fixed-size chess board
}
```

## 💡 When to Use ArrayList

### 1. Dynamic Collections

```java
// Good use case: Dynamic data that grows/shrinks
public class ShoppingCart {
    private ArrayList<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);  // Size automatically grows
    }

    public void removeItem(Item item) {
        items.remove(item);  // Size automatically shrinks
    }
}
```

### 2. Frequent Insertions/Deletions

```java
// Good use case: Frequently modifying collection
public class TaskManager {
    private ArrayList<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeCompletedTasks() {
        tasks.removeIf(Task::isCompleted);
    }

    public void reorderTasks(int fromIndex, int toIndex) {
        Task task = tasks.remove(fromIndex);
        tasks.add(toIndex, task);
    }
}
```

### 3. Rich Collection Operations

```java
// Good use case: Using collection utilities
public class StudentManager {
    private ArrayList<Student> students = new ArrayList<>();

    public List<Student> getTopPerformers() {
        return students.stream()
                      .filter(s -> s.getGrade() >= 90)
                      .sorted(Comparator.comparing(Student::getGrade).reversed())
                      .collect(Collectors.toList());
    }

    public void updateGrades() {
        students.replaceAll(student ->
            new Student(student.getName(), student.getGrade() + 5));
    }
}
```

### 4. Generic Type Safety

```java
// Good use case: Type-safe collections
public class Database<T extends Entity> {
    private ArrayList<T> records = new ArrayList<>();

    public void addRecord(T record) {
        records.add(record);
    }

    public T findById(String id) {
        return records.stream()
                     .filter(r -> r.getId().equals(id))
                     .findFirst()
                     .orElse(null);
    }
}
```

## 🔧 Performance Considerations

### Memory Usage

- **Arrays**: More memory efficient for primitive types
- **ArrayList**: More memory overhead due to:
  - Object wrapper for each element
  - Dynamic resizing buffer
  - Additional object metadata

### Time Complexity

| Operation       | Array | ArrayList      |
| --------------- | ----- | -------------- |
| Access          | O(1)  | O(1)           |
| Search          | O(n)  | O(n)           |
| Insert at end   | N/A   | O(1) amortized |
| Insert at index | N/A   | O(n)           |
| Delete          | N/A   | O(n)           |

## 📝 Best Practices

### Use Arrays when:

1. The size is fixed and known at creation
2. Working with primitive types
3. Performance is critical
4. Memory usage is a concern
5. Working with multi-dimensional data
6. Implementing low-level algorithms

### Use ArrayList when:

1. The size is dynamic or unknown
2. Need frequent insertions/deletions
3. Working with objects
4. Need rich collection operations
5. Want type safety with generics
6. Implementing high-level business logic

## 🧪 Real-World Example

```java
public class InventorySystem {
    // Use array for fixed-size product categories
    private final String[] categories = {"Electronics", "Clothing", "Food", "Books"};

    // Use ArrayList for dynamic product inventory
    private ArrayList<Product> inventory = new ArrayList<>();

    public void addProduct(Product product) {
        inventory.add(product);
    }

    public Product[] getProductsByCategory(String category) {
        // Convert to array for fixed-size return
        return inventory.stream()
                       .filter(p -> p.getCategory().equals(category))
                       .toArray(Product[]::new);
    }

    public void updateStock() {
        // Use ArrayList for dynamic operations
        inventory.removeIf(p -> p.getStock() <= 0);
        inventory.sort(Comparator.comparing(Product::getStock));
    }
}
```

## ⚠️ Common Pitfalls

1. **Array Size Limitations**

   ```java
   // Bad: Array size must be known at creation
   int[] numbers = new int[userInput];  // What if userInput is too large?

   // Good: ArrayList handles size dynamically
   ArrayList<Integer> numbers = new ArrayList<>();
   numbers.add(userInput);  // No size limitation
   ```

2. **Type Safety**

   ```java
   // Bad: Array type safety issues
   Object[] objects = new String[10];
   objects[0] = 42;  // Runtime error

   // Good: ArrayList with generics
   ArrayList<String> strings = new ArrayList<>();
   strings.add("Hello");  // Compile-time type checking
   ```

3. **Memory Management**

   ```java
   // Bad: Large fixed-size array
   int[] largeArray = new int[1000000];  // Allocates memory even if unused

   // Good: ArrayList grows as needed
   ArrayList<Integer> dynamicList = new ArrayList<>();
   // Memory allocated only as needed

   ```

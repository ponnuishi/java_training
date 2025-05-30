# Java Collections: HashSet and TreeSet

## 📌 Overview

`HashSet` and `TreeSet` are two implementations of the `Set` interface in Java. Both ensure unique elements, but they have different characteristics and use cases. Understanding when to use each is crucial for writing efficient and maintainable code.

## 🔍 Key Differences

| Feature        | HashSet                       | TreeSet                          |
| -------------- | ----------------------------- | -------------------------------- |
| Ordering       | No guaranteed order           | Sorted order (natural or custom) |
| Null Elements  | Allows one null element       | No null elements allowed         |
| Performance    | O(1) for basic operations     | O(log n) for basic operations    |
| Implementation | Hash table                    | Red-Black tree                   |
| Memory Usage   | Less memory overhead          | More memory overhead             |
| Thread Safety  | Not thread-safe               | Not thread-safe                  |
| Use Case       | General purpose, fast lookups | Sorted data, range operations    |

## 💡 When to Use HashSet

### 1. Basic HashSet Operations

```java
// Simple example: Basic HashSet operations
HashSet<String> fruits = new HashSet<>();

// Adding elements
fruits.add("Apple");
fruits.add("Banana");
fruits.add("Orange");
fruits.add("Apple");  // Duplicate - won't be added

System.out.println("Fruits: " + fruits);  // [Apple, Banana, Orange]

// Checking if element exists
System.out.println("Contains Apple? " + fruits.contains("Apple"));  // true
System.out.println("Contains Mango? " + fruits.contains("Mango"));  // false

// Removing element
fruits.remove("Banana");
System.out.println("After removing Banana: " + fruits);  // [Apple, Orange]

// Size of set
System.out.println("Number of fruits: " + fruits.size());  // 2
```

### 2. Simple Set Operations

```java
// Simple example: Basic set operations
HashSet<String> set1 = new HashSet<>(Arrays.asList("A", "B", "C"));
HashSet<String> set2 = new HashSet<>(Arrays.asList("B", "C", "D"));

// Union
HashSet<String> union = new HashSet<>(set1);
union.addAll(set2);
System.out.println("Union: " + union);  // [A, B, C, D]

// Intersection
HashSet<String> intersection = new HashSet<>(set1);
intersection.retainAll(set2);
System.out.println("Intersection: " + intersection);  // [B, C]

// Difference
HashSet<String> difference = new HashSet<>(set1);
difference.removeAll(set2);
System.out.println("Difference: " + difference);  // [A]
```

## 💡 When to Use TreeSet

### 1. Basic TreeSet Operations

```java
// Simple example: Basic TreeSet operations
TreeSet<Integer> numbers = new TreeSet<>();

// Adding elements
numbers.add(5);
numbers.add(2);
numbers.add(8);
numbers.add(1);
numbers.add(5);  // Duplicate - won't be added

System.out.println("Numbers: " + numbers);  // [1, 2, 5, 8]

// First and last elements
System.out.println("First: " + numbers.first());  // 1
System.out.println("Last: " + numbers.last());    // 8

// Getting elements less than or greater than
System.out.println("Less than 5: " + numbers.headSet(5));  // [1, 2]
System.out.println("Greater than 2: " + numbers.tailSet(2));  // [2, 5, 8]
```

### 2. Simple Custom Ordering

```java
// Simple example: Custom ordering
TreeSet<String> words = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

words.add("Zebra");
words.add("apple");
words.add("Banana");
words.add("zebra");  // Won't be added (case-insensitive duplicate)

System.out.println("Words: " + words);  // [apple, Banana, Zebra]
```

### 3. Basic Range Operations

```java
// Simple example: Range operations
TreeSet<Integer> scores = new TreeSet<>(Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80, 90));

// Get scores between 30 and 70
System.out.println("Scores between 30 and 70: " +
    scores.subSet(30, true, 70, true));  // [30, 40, 50, 60, 70]

// Get scores less than 50
System.out.println("Scores less than 50: " +
    scores.headSet(50));  // [10, 20, 30, 40]

// Get scores greater than 50
System.out.println("Scores greater than 50: " +
    scores.tailSet(50));  // [50, 60, 70, 80, 90]
```

## 🧪 Simple Real-World Example

```java
// Simple example: Student Grade Management
public class GradeManager {
    // Use HashSet to track unique student IDs
    private HashSet<String> studentIds = new HashSet<>();

    // Use TreeSet to maintain sorted grades
    private TreeSet<Integer> grades = new TreeSet<>();

    public void addStudent(String studentId) {
        studentIds.add(studentId);
    }

    public void addGrade(int grade) {
        grades.add(grade);
    }

    public boolean isStudentRegistered(String studentId) {
        return studentIds.contains(studentId);
    }

    public int getHighestGrade() {
        return grades.last();
    }

    public int getLowestGrade() {
        return grades.first();
    }

    public static void main(String[] args) {
        GradeManager manager = new GradeManager();

        // Add students
        manager.addStudent("S001");
        manager.addStudent("S002");
        manager.addStudent("S001");  // Duplicate - won't be added

        // Add grades
        manager.addGrade(85);
        manager.addGrade(92);
        manager.addGrade(78);
        manager.addGrade(92);  // Duplicate - won't be added

        // Check results
        System.out.println("Is S001 registered? " +
            manager.isStudentRegistered("S001"));  // true
        System.out.println("Highest grade: " +
            manager.getHighestGrade());  // 92
        System.out.println("Lowest grade: " +
            manager.getLowestGrade());   // 78
    }
}
```

## ⚠️ Common Pitfalls

1. **Basic HashSet Ordering**

   ```java
   // Bad: Don't assume order in HashSet
   HashSet<String> set = new HashSet<>();
   set.add("First");
   set.add("Second");
   set.add("Third");
   // Order might be different when printed!

   // Good: Use TreeSet if order matters
   TreeSet<String> orderedSet = new TreeSet<>();
   orderedSet.add("First");
   orderedSet.add("Second");
   orderedSet.add("Third");
   // Always prints in alphabetical order
   ```

2. **Basic TreeSet Null Handling**

   ```java
   // Bad: Can't add null to TreeSet
   TreeSet<String> set = new TreeSet<>();
   set.add(null);  // Throws NullPointerException

   // Good: Use HashSet if you need to store null
   HashSet<String> set = new HashSet<>();
   set.add(null);  // Works fine
   ```

3. **Basic String Comparison**

   ```java
   // Bad: Case-sensitive comparison might not be what you want
   HashSet<String> names = new HashSet<>();
   names.add("John");
   names.add("JOHN");  // Treated as different

   // Good: Use TreeSet with case-insensitive comparator
   TreeSet<String> names = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
   names.add("John");
   names.add("JOHN");  // Treated as same
   ```

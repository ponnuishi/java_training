# Java Stream Programming Tutorial

## Introduction

Java Streams provide a modern, functional way to process collections of data. Introduced in Java 8, the Stream API allows you to write concise, readable, and efficient code for filtering, transforming, and aggregating data.

---

## 1. What is a Stream?
A Stream is a sequence of elements supporting sequential and parallel aggregate operations. Streams do **not** store data; they operate on a data source such as a `List`, `Set`, or array.

---

## 2. Stream Pipeline Structure
A typical stream pipeline consists of three parts:

1. **Source**: Where the data comes from (e.g., a collection)
2. **Intermediate Operations**: Transform the stream (e.g., `filter`, `map`, `sorted`)
3. **Terminal Operation**: Produces a result or side-effect (e.g., `forEach`, `collect`, `count`)

**Visual Flow:**
```
Source → [Intermediate Operation] → ... → Terminal Operation
```

---

## 2a. Creating Streams

You can create streams from various data sources in Java:

### 1. From Collections
Most collection classes (like `List`, `Set`) have a `stream()` method:
```java
List<String> list = Arrays.asList("a", "b", "c");
Stream<String> stream = list.stream();
```

### 2. From Arrays
Use `Arrays.stream()` or `Stream.of()`:
```java
int[] numbers = {1, 2, 3};
IntStream intStream = Arrays.stream(numbers);

String[] words = {"hello", "world"};
Stream<String> wordStream = Stream.of(words);
```

### 3. From Individual Values
Use `Stream.of()`:
```java
Stream<Integer> numberStream = Stream.of(1, 2, 3, 4);
```

### 4. From Files (Lines)
Use `Files.lines()` (throws IOException):
```java
Stream<String> lines = Files.lines(Paths.get("file.txt"));
```

### 5. Infinite Streams
Use `Stream.generate()` or `Stream.iterate()`:
```java
Stream<Double> randoms = Stream.generate(Math::random).limit(5);
Stream<Integer> evens = Stream.iterate(0, n -> n + 2).limit(5);
```

---

## 3. Basic Example

Let's print all even numbers from a list:
```java
import java.util.*;

public class StreamBasicExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        numbers.stream()                  // Source
            .filter(n -> n % 2 == 0)     // Intermediate: keep even numbers
            .forEach(System.out::println); // Terminal: print each
    }
}
// Output:
// 2
// 4
// 6
```

---

## 4. Common Stream Operations

### Intermediate Operations (return a Stream)
- `filter(Predicate)`: Keep elements that match a condition
- `map(Function)`: Transform each element
- `sorted()`: Sort elements
- `distinct()`: Remove duplicates
- `limit(n)`: Take only the first n elements
- `skip(n)`: Skip the first n elements

### Terminal Operations (produce a result)
- `forEach(Consumer)`: Perform an action for each element
- `collect(Collector)`: Gather results into a collection (e.g., `List`, `Set`)
- `count()`: Count elements
- `reduce(BinaryOperator)`: Combine elements into a single result
- `anyMatch(Predicate)`, `allMatch(Predicate)`, `noneMatch(Predicate)`: Test conditions
- `findFirst()`, `findAny()`: Get an element if present

---

#### Common Terminal Operations Explained

**1. forEach(Consumer)**
- Performs an action for each element in the stream (usually used for printing or side effects).
```java
List<String> names = Arrays.asList("Alice", "Bob");
names.stream().forEach(System.out::println);
// Output:
// Alice
// Bob
```

**2. collect(Collector)**
- Gathers the elements into a collection or another form.
```java
List<Integer> numbers = Arrays.asList(1, 2, 3);
List<Integer> doubled = numbers.stream()
    .map(n -> n * 2)
    .collect(Collectors.toList());
System.out.println(doubled); // [2, 4, 6]
```

**3. count()**
- Returns the number of elements in the stream.
```java
long count = Stream.of("a", "b", "c").count();
System.out.println(count); // 3
```

**4. reduce(BinaryOperator)**
- Combines elements to produce a single value (e.g., sum, product).
```java
List<Integer> nums = Arrays.asList(1, 2, 3, 4);
int sum = nums.stream().reduce(0, Integer::sum);
System.out.println(sum); // 10
```

**5. anyMatch, allMatch, noneMatch (Predicate)**
- Test whether any, all, or none of the elements match a condition.
```java
List<String> words = Arrays.asList("apple", "banana", "cherry");
boolean anyStartsWithA = words.stream().anyMatch(w -> w.startsWith("a")); // true
boolean allHaveLength5 = words.stream().allMatch(w -> w.length() == 5); // false
boolean noneEndWithZ = words.stream().noneMatch(w -> w.endsWith("z")); // true
```

**6. findFirst(), findAny()**
- Retrieve an element from the stream if present (returns Optional).
```java
List<String> list = Arrays.asList("x", "y", "z");
Optional<String> first = list.stream().findFirst();
first.ifPresent(System.out::println); // x

Optional<String> any = list.stream().findAny();
any.ifPresent(System.out::println); // x (or any element, especially in parallel streams)
```

---

## 5. Practical Examples

### Example 1: Filter and Collect
```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Anna");
List<String> aNames = names.stream()
    .filter(name -> name.startsWith("A"))
    .collect(Collectors.toList());
System.out.println(aNames); // [Alice, Anna]
```

### Example 2: Map and Reduce
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
int sumOfSquares = numbers.stream()
    .map(n -> n * n)
    .reduce(0, Integer::sum);
System.out.println(sumOfSquares); // 30
```

### Example 3: Sorting and Limiting
```java
List<String> words = Arrays.asList("banana", "apple", "pear", "kiwi");
List<String> sorted = words.stream()
    .sorted()
    .limit(2)
    .collect(Collectors.toList());
System.out.println(sorted); // [apple, banana]
```

### Example 4: Distinct and Count
```java
List<Integer> nums = Arrays.asList(1, 2, 2, 3, 3, 3, 4);
long uniqueCount = nums.stream()
    .distinct()
    .count();
System.out.println(uniqueCount); // 4
```

---

## 6. Stream Best Practices
- Streams are single-use: create a new stream for each operation.
- Prefer method references (`String::toUpperCase`) for readability.
- Avoid side effects in intermediate operations.
- Use parallel streams (`.parallelStream()`) only for large, independent tasks.
- Use `Optional` to handle possible absence of results (e.g., `findFirst()`).

---

## 7. Summary Table
| Part           | Example Code                  | Description                  |
|----------------|------------------------------|------------------------------|
| Source         | numbers.stream()              | Start the stream             |
| Intermediate   | .filter(n -> n > 0)          | Keep positive numbers        |
| Intermediate   | .map(n -> n * 2)             | Double each number           |
| Terminal       | .collect(Collectors.toList()) | Collect results into a list  |
| Terminal       | .forEach(System.out::println) | Print each element           |
| Terminal       | .count()                      | Count elements               |

---

# Java Collections: Queue, PriorityQueue, and Deque

## 📑 Table of Contents

- [Overview](#overview)
- [Key Differences](#key-differences)
- [Queue Interface](#1-queue-interface)
- [PriorityQueue](#2-priorityqueue)
- [Deque Interface](#3-deque-interface)
- [Performance Considerations](#performance-considerations)
- [Best Practices](#best-practices)
- [Common Pitfalls](#common-pitfalls)

## Overview

Java provides three main queue-related interfaces and implementations:

- `Queue`: Basic FIFO queue interface
- `PriorityQueue`: Priority-based queue implementation
- `Deque`: Double-ended queue interface

## Key Differences

| Feature                | Queue                     | PriorityQueue              | Deque                     |
| ---------------------- | ------------------------- | -------------------------- | ------------------------- |
| Ordering               | FIFO                      | Priority-based             | Both ends                 |
| Null Elements          | Depends on implementation | Not allowed                | Depends on implementation |
| Thread Safety          | Not thread-safe           | Not thread-safe            | Not thread-safe           |
| Common Implementations | LinkedList, ArrayDeque    | PriorityQueue              | ArrayDeque, LinkedList    |
| Best Use Case          | Simple FIFO operations    | Priority-based processing  | Double-ended operations   |
| Memory Usage           | Moderate                  | Moderate                   | Moderate                  |
| Performance            | O(1) for basic operations | O(log n) for insert/remove | O(1) for basic operations |

## 1. Queue in Java

### 📌 Overview

- `Queue` is a linear data structure that follows the **FIFO (First In, First Out)** principle.
- Defined as an **interface** in `java.util`.

### ✅ Key Features

- Elements are **added at the end** and **removed from the front**.
- Common implementations:
  - `LinkedList`
  - `PriorityQueue`
- Throws exceptions or returns special values when operations fail (e.g., `add()` vs `offer()`, `remove()` vs `poll()`).

### 🧪 Code Example

```java
import java.util.*;

public class QueueExample {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();

        // Using add() method
        queue.add("Alice");
        queue.add("Bob");
        // Using offer() method
        queue.offer("Charlie");

        System.out.println("Queue: " + queue);

        // Both remove() and poll() work similarly
        System.out.println("Removed: " + queue.poll()); // Alice
        System.out.println("Peek: " + queue.peek());    // Bob
        System.out.println("Queue after removal: " + queue);
    }
} 
```

## 2. PriorityQueue in Java

### 📌 Overview
- `PriorityQueue` is a specialized queue implementation where elements are ordered based on their **priority** rather than insertion order.
- Located in the `java.util` package.
- Internally implemented using a **min-heap** data structure (by default).
- Elements with **lowest priority value** are served first (natural ordering).
- Can be customized using a **Comparator** for custom ordering.

### ✅ Key Features
- **Not FIFO**: Order is determined by priority, not insertion order
- **No null elements**: Does not allow null values
- **Not thread-safe**: For concurrent usage, use `PriorityBlockingQueue`
- Efficient operations:
  - Insert: O(log n)
  - Remove: O(log n)
  - Peek: O(1)

### 🧪 Code Example

```java
import java.util.*;

public class PriorityQueueExample {
    public static void main(String[] args) {
        // Natural ordering (min-heap)
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        // Using add() method
        pq.add(5);
        pq.add(2);
        // Using offer() method
        pq.offer(8);
        pq.offer(1);

        System.out.println("PriorityQueue (natural order):");
        while (!pq.isEmpty()) {
            System.out.println(pq.poll()); // Prints: 1, 2, 5, 8
        }

        // Custom ordering using Comparator
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        // Using add() method
        maxHeap.add(5);
        maxHeap.add(2);
        // Using offer() method
        maxHeap.offer(8);
        maxHeap.offer(1);

        System.out.println("\nPriorityQueue (reverse order):");
        while (!maxHeap.isEmpty()) {
            System.out.println(maxHeap.poll()); // Prints: 8, 5, 2, 1
        }
    }
}

## 3. Deque in Java

### 📌 Overview
- `Deque` (Double Ended Queue) is a linear collection that supports element insertion and removal at both ends.
- Pronounced as "deck" (short for "double-ended queue").
- Located in the `java.util` package.
- Combines the functionality of both stack and queue data structures.

### ✅ Key Features
- **Double-ended access**: Add/remove elements from both ends
- **No capacity restrictions**: Automatically grows as needed
- Common implementations:
  - `ArrayDeque`: Resizable array implementation
  - `LinkedList`: Doubly-linked list implementation
- Thread-safe variant: `ConcurrentLinkedDeque`

### 🔧 Common Operations
- First end operations:
  - `addFirst()`, `offerFirst()`
  - `removeFirst()`, `pollFirst()`
  - `getFirst()`, `peekFirst()`
- Last end operations:
  - `addLast()`, `offerLast()`
  - `removeLast()`, `pollLast()`
  - `getLast()`, `peekLast()`

### 🧪 Code Example

```java
import java.util.*;

public class DequeExample {
    public static void main(String[] args) {
        Deque<String> deque = new ArrayDeque<>();

        // Adding elements using add methods
        deque.addFirst("First");
        deque.addLast("Last");
        // Adding elements using offer methods
        deque.offerFirst("New First");
        deque.offerLast("New Last");

        System.out.println("Deque: " + deque);

        // Accessing elements
        System.out.println("First element: " + deque.getFirst());
        System.out.println("Last element: " + deque.getLast());

        // Removing elements
        System.out.println("Removed from front: " + deque.removeFirst());
        System.out.println("Removed from end: " + deque.removeLast());

        System.out.println("Deque after removals: " + deque);
    }
}

### 📝 Usage Scenarios
1. **As a Stack**:
   - Use `push()` and `pop()` methods
   - More efficient than `Stack` class
2. **As a Queue**:
   - Use `offer()` and `poll()` methods
   - More flexible than `Queue` interface
3. **As a Double-ended Queue**:
   - Use any combination of first/last operations
   - Perfect for sliding window algorithms
   - Useful in work-stealing algorithms

## Performance Considerations

### Time Complexity

| Operation | Queue | PriorityQueue | Deque |
|-----------|-------|---------------|-------|
| Insert (add/offer) | O(1) | O(log n) | O(1) |
| Remove (remove/poll) | O(1) | O(log n) | O(1) |
| Peek | O(1) | O(1) | O(1) |
| Search | O(n) | O(n) | O(n) |

### Memory Usage
- All implementations use moderate memory
- `PriorityQueue` uses a heap data structure
- `ArrayDeque` uses a resizable array
- `LinkedList` uses node-based storage

## Best Practices

1. **Choosing the Right Implementation**
   - Use `Queue` for simple FIFO operations
   - Use `PriorityQueue` when order matters
   - Use `Deque` when you need double-ended access

2. **Method Selection**
   - Use `offer()` instead of `add()` for better error handling
   - Use `poll()` instead of `remove()` to avoid exceptions
   - Use `peek()` to check without removing

3. **Thread Safety**
   - Use `BlockingQueue` for concurrent operations
   - Use `PriorityBlockingQueue` for concurrent priority queues
   - Use `ConcurrentLinkedDeque` for concurrent deques

## Common Pitfalls

1. **Queue Operations**
   ```java
   // ❌ Bad: May throw exception
   queue.remove();  // Throws NoSuchElementException if empty

   // ✅ Good: Returns null if empty
   queue.poll();    // Returns null if empty
   ```

2. **PriorityQueue Ordering**

   ```java
   // ❌ Bad: Assuming FIFO order
   while (!pq.isEmpty()) {
       System.out.println(pq.poll());  // Order depends on priority
   }

   // ✅ Good: Understanding priority order
   PriorityQueue<Integer> pq = new PriorityQueue<>();
   pq.add(5);  // Will be processed based on natural ordering
   ```

3. **Deque Operations**

   ```java
   // ❌ Bad: Mixing Queue and Deque operations
   deque.add("element");    // Adds to end
   deque.remove();          // Removes from front

   // ✅ Good: Consistent end usage
   deque.addLast("element");  // Adds to end
   deque.removeLast();        // Removes from end
   ```

# Java Collections: ArrayList and LinkedList

---

## 1. ArrayList in Java

### 📌 Overview

- `ArrayList` is a resizable array implementation of the `List` interface.
- Located in the `java.util` package.
- Provides dynamic array functionality with automatic resizing.
- Maintains insertion order of elements.
- Allows random access to elements using index.

### ✅ Key Features

- **Dynamic Sizing**: Automatically grows and shrinks as needed
- **Random Access**: O(1) time complexity for get/set operations
- **Not Synchronized**: Not thread-safe by default
- **Allows Duplicates**: Can store multiple identical elements
- **Allows Null**: Can store null values
- **Implements**: List, RandomAccess, Cloneable, Serializable

### 🔧 Common Operations and Time Complexities

- Add element: O(1) amortized (O(n) worst case due to resizing)
- Remove element: O(n) (requires shifting elements)
- Get/Set element: O(1)
- Search element: O(n)
- Insert at index: O(n)

### 🧪 Code Example

```java
import java.util.*;

public class ArrayListExample {
    public static void main(String[] args) {
        // Example 1: Managing a list of User objects
        ArrayList<User> users = new ArrayList<>();

        // Adding users
        users.add(new User("john.doe@email.com", "John Doe", "Active"));
        users.add(new User("jane.smith@email.com", "Jane Smith", "Inactive"));
        users.add(new User("admin@company.com", "Admin User", "Active"));
        users.add(1, new User("support@company.com", "Support Team", "Active")); // Insert at specific position

        System.out.println("User List: " + users);

        // Finding active users
        System.out.println("\nActive Users:");
        for (User user : users) {
            if ("Active".equals(user.getStatus())) {
                System.out.println(user.getName() + " (" + user.getEmail() + ")");
            }
        }

        // Example 2: Managing a task queue
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task("TASK-001", "Fix login bug", "High", "In Progress"));
        tasks.add(new Task("TASK-002", "Update documentation", "Medium", "Todo"));
        tasks.add(new Task("TASK-003", "Code review", "High", "Todo"));

        // Modifying task status
        tasks.get(1).setStatus("In Progress");
        System.out.println("\nUpdated Tasks: " + tasks);

        // Sorting tasks by priority
        Collections.sort(tasks, (t1, t2) -> t1.getPriority().compareTo(t2.getPriority()));
        System.out.println("\nTasks sorted by priority: " + tasks);
    }
}

// Supporting classes for the example
class User {
    private String email;
    private String name;
    private String status;

    public User(String email, String name, String status) {
        this.email = email;
        this.name = name;
        this.status = status;
    }

    // Getters and setters
    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return name + " (" + status + ")";
    }
}

class Task {
    private String id;
    private String description;
    private String priority;
    private String status;

    public Task(String id, String description, String priority, String status) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    // Getters and setters
    public String getPriority() { return priority; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return id + ": " + description + " [" + priority + "]";
    }
}
```

## 2. LinkedList in Java

### 📌 Overview

- `LinkedList` is a doubly-linked list implementation of the `List` and `Deque` interfaces.
- Located in the `java.util` package.
- Each element (node) contains a reference to both next and previous elements.
- Efficient for frequent insertions and deletions.
- Can be used as both a List and a Queue/Deque.

### ✅ Key Features

- **Doubly-Linked**: Each node has references to both next and previous nodes
- **No Random Access**: Sequential access only
- **Not Synchronized**: Not thread-safe by default
- **Implements**: List, Deque, Queue
- **Memory Efficient**: No need for contiguous memory allocation
- **Allows Duplicates**: Can store multiple identical elements
- **Allows Null**: Can store null values

### 🔧 Common Operations and Time Complexities

- Add element: O(1) at beginning/end, O(n) at index
- Remove element: O(1) at beginning/end, O(n) at index
- Get/Set element: O(n)
- Search element: O(n)
- Insert at index: O(n)

### 🧪 Code Example

```java
import java.util.*;

public class LinkedListExample {
    public static void main(String[] args) {
        // Example 1: Managing a browser history
        LinkedList<WebPage> browserHistory = new LinkedList<>();

        // Simulating user navigation
        browserHistory.addLast(new WebPage("https://www.google.com", "Google", System.currentTimeMillis()));
        browserHistory.addLast(new WebPage("https://www.github.com", "GitHub", System.currentTimeMillis()));
        browserHistory.addLast(new WebPage("https://www.stackoverflow.com", "Stack Overflow", System.currentTimeMillis()));

        System.out.println("Current History: " + browserHistory);

        // Simulating back button
        WebPage lastPage = browserHistory.removeLast();
        System.out.println("\nBack to: " + browserHistory.getLast());

        // Example 2: Managing a print queue
        LinkedList<PrintJob> printQueue = new LinkedList<>();

        // Adding print jobs
        printQueue.offer(new PrintJob("DOC-001", "Project Proposal", 10, "High"));
        printQueue.offer(new PrintJob("DOC-002", "Meeting Notes", 5, "Normal"));
        printQueue.offer(new PrintJob("DOC-003", "Financial Report", 15, "High"));

        System.out.println("\nPrint Queue:");
        while (!printQueue.isEmpty()) {
            PrintJob job = printQueue.poll();
            System.out.println("Printing: " + job.getDocumentName() +
                             " (Pages: " + job.getPages() +
                             ", Priority: " + job.getPriority() + ")");
        }

        // Example 3: Managing a chat application's message history
        LinkedList<ChatMessage> messageHistory = new LinkedList<>();

        // Adding messages
        messageHistory.addLast(new ChatMessage("Alice", "Hello team!", System.currentTimeMillis()));
        messageHistory.addLast(new ChatMessage("Bob", "Hi Alice, how's the project going?", System.currentTimeMillis()));
        messageHistory.addLast(new ChatMessage("Alice", "Going well, just finished the backend!", System.currentTimeMillis()));

        // Displaying recent messages
        System.out.println("\nRecent Messages:");
        for (ChatMessage msg : messageHistory) {
            System.out.println(msg.getSender() + ": " + msg.getContent());
        }
    }
}

// Supporting classes for the example
class WebPage {
    private String url;
    private String title;
    private long timestamp;

    public WebPage(String url, String title, long timestamp) {
        this.url = url;
        this.title = title;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return title;
    }
}

class PrintJob {
    private String documentId;
    private String documentName;
    private int pages;
    private String priority;

    public PrintJob(String documentId, String documentName, int pages, String priority) {
        this.documentId = documentId;
        this.documentName = documentName;
        this.pages = pages;
        this.priority = priority;
    }

    public String getDocumentName() { return documentName; }
    public int getPages() { return pages; }
    public String getPriority() { return priority; }
}

class ChatMessage {
    private String sender;
    private String content;
    private long timestamp;

    public ChatMessage(String sender, String content, long timestamp) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getSender() { return sender; }
    public String getContent() { return content; }
}
```

### 📝 When to Use Which?

#### Use ArrayList when:

- You need random access to elements
- You have more get/set operations than add/remove
- You know the approximate size of the list
- Memory is not a constraint
- You need to sort the list frequently

#### Use LinkedList when:

- You have frequent insertions/deletions at the beginning or middle
- You need to use the list as a Queue or Deque
- You don't need random access to elements
- Memory overhead is not a concern
- You need to implement a stack or queue

### 🔍 Performance Comparison

| Operation           | ArrayList      | LinkedList                    |
| ------------------- | -------------- | ----------------------------- |
| Get/Set             | O(1)           | O(n)                          |
| Add/Remove at end   | O(1) amortized | O(1)                          |
| Add/Remove at index | O(n)           | O(n)                          |
| Memory Usage        | Less           | More (due to node references) |
| Random Access       | Fast           | Slow                          |
| Sequential Access   | Fast           | Fast                          |

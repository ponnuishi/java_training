# Lambda Expressions vs Functional Interfaces

Understanding the relationship between lambda expressions and functional interfaces is crucial for effective functional programming in Java.

## How They Work Together

Lambda expressions are **implementations** of functional interfaces. Every lambda expression has a target type, which must be a functional interface.

```java
// These are equivalent:

// 1. Lambda expression (implicit)
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
names.forEach(name -> System.out.println(name));

// 2. Lambda assigned to functional interface variable (explicit)
Consumer<String> printer = name -> System.out.println(name);
names.forEach(printer);

// 3. Anonymous class (traditional way)
Consumer<String> printer2 = new Consumer<String>() {
    @Override
    public void accept(String name) {
        System.out.println(name);
    }
};
names.forEach(printer2);
```

## When to Use Lambda Expressions Directly

Use lambda expressions directly when:
- **Simple, one-time operations**
- **Inline processing**
- **Method parameters**

```java
// Good for simple, one-time operations
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// Direct lambda in method call
numbers.removeIf(n -> n % 2 == 0);  // Remove even numbers

// Direct lambda for sorting
numbers.sort((a, b) -> Integer.compare(a, b));

// Direct lambda for processing
numbers.forEach(n -> System.out.println("Number: " + n));
```

## When to Use Functional Interface Variables

Use functional interface variables when:
- **Reusable logic**
- **Complex operations**
- **Configuration or parameters**
- **Testing and debugging**

```java
// Good for reusable logic
Predicate<String> isValidEmail = email -> 
    email != null && email.contains("@") && email.contains(".");

Function<String, String> normalizeEmail = email -> 
    email.trim().toLowerCase();

Consumer<String> auditLogger = message -> {
    String timestamp = LocalDateTime.now().toString();
    System.out.println("[" + timestamp + "] " + message);
};

// Reuse across different contexts
public boolean validateUser(String email) {
    return isValidEmail.test(email);
}

public String processEmail(String email) {
    return normalizeEmail.apply(email);
}

public void logUserAction(String action) {
    auditLogger.accept(action);
}
```

## Comparison Examples

### Example 1: Simple vs Complex Logic

```java
// Simple logic - Direct lambda is fine
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
names.stream()
    .filter(name -> name.length() > 3)  // Simple condition
    .forEach(System.out::println);

// Complex logic - Functional interface variable is better
Predicate<User> isEligibleForPromotion = user -> {
    return user.getYearsOfService() >= 2 
        && user.getPerformanceRating() >= 4.0
        && user.getLastPromotionDate().isBefore(LocalDate.now().minusYears(1))
        && user.getSkillLevel() >= SkillLevel.INTERMEDIATE;
};

List<User> users = getUsers();
List<User> eligible = users.stream()
    .filter(isEligibleForPromotion)  // Reusable and testable
    .collect(Collectors.toList());
```

### Example 2: Configuration and Flexibility

```java
public class DataProcessor {
    // Using functional interface parameters for flexibility
    public <T> void processData(List<T> data, 
                               Predicate<T> filter, 
                               Function<T, String> formatter,
                               Consumer<String> output) {
        data.stream()
            .filter(filter)
            .map(formatter)
            .forEach(output);
    }
}

// Usage with different configurations
DataProcessor processor = new DataProcessor();
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// Configuration 1: Even numbers to console
processor.processData(
    numbers,
    n -> n % 2 == 0,              // filter
    n -> "Even: " + n,            // formatter
    System.out::println           // output
);

// Configuration 2: Odd numbers to log
Predicate<Integer> isOdd = n -> n % 2 != 0;
Function<Integer, String> oddFormatter = n -> "Odd number: " + n;
Consumer<String> logger = msg -> System.out.println("LOG: " + msg);

processor.processData(numbers, isOdd, oddFormatter, logger);
```

## Testing and Debugging Benefits

```java
public class UserService {
    // Functional interface fields for easy testing
    private Predicate<User> isActiveUser = user -> user.isActive();
    private Function<User, String> userFormatter = user -> 
        user.getName() + " (" + user.getEmail() + ")";
    private Consumer<String> notificationSender = message -> 
        emailService.send(message);
    
    public void notifyActiveUsers(List<User> users) {
        users.stream()
            .filter(isActiveUser)
            .map(userFormatter)
            .forEach(notificationSender);
    }
    
    // Easy to test by setting different implementations
    public void setUserFormatter(Function<User, String> formatter) {
        this.userFormatter = formatter;
    }
    
    public void setNotificationSender(Consumer<String> sender) {
        this.notificationSender = sender;
    }
}

// In tests
@Test
public void testUserNotification() {
    UserService service = new UserService();
    List<String> sentMessages = new ArrayList<>();
    
    // Replace with test implementation
    service.setNotificationSender(message -> sentMessages.add(message));
    service.setUserFormatter(user -> user.getName());
    
    service.notifyActiveUsers(testUsers);
    
    assertEquals(3, sentMessages.size());
    assertTrue(sentMessages.contains("Alice"));
}
```

## Performance Considerations

```java
// Lambda expressions are compiled to invokedynamic calls
// Functional interface variables can be reused

public class PerformanceExample {
    // Good: Reuse functional interface instances
    private static final Predicate<String> IS_VALID_EMAIL = 
        email -> email.contains("@");
    
    private static final Function<String, String> NORMALIZE_EMAIL = 
        email -> email.trim().toLowerCase();
    
    public void processEmails(List<String> emails) {
        emails.stream()
            .filter(IS_VALID_EMAIL)    // Reused instance
            .map(NORMALIZE_EMAIL)      // Reused instance
            .forEach(this::sendEmail);
    }
    
    // Less efficient: Creating new lambda instances in loop
    public void processEmailsInefficient(List<String> emails) {
        for (String email : emails) {
            // New lambda created each iteration
            if (emails.stream().anyMatch(e -> e.contains("@"))) {
                String normalized = emails.stream()
                    .map(e -> e.trim().toLowerCase())  // New lambda each time
                    .findFirst()
                    .orElse("");
                sendEmail(normalized);
            }
        }
    }
}
```

## Best Practices Summary

### Use Lambda Expressions Directly When:
- ✅ Simple, one-line operations
- ✅ Used only once in a specific context
- ✅ Clear and readable inline
- ✅ Method parameters for callbacks

```java
// Good examples
list.removeIf(item -> item.isExpired());
list.sort((a, b) -> a.compareTo(b));
button.setOnAction(e -> showDialog());
```

### Use Functional Interface Variables When:
- ✅ Complex logic (multiple lines)
- ✅ Reused across multiple places
- ✅ Need to test the logic separately
- ✅ Configuration or strategy patterns
- ✅ Debugging and logging

```java
// Good examples
Predicate<Order> isUrgentOrder = order -> 
    order.getPriority() == Priority.HIGH && 
    order.getDeadline().isBefore(LocalDate.now().plusDays(1));

Function<Customer, String> formatCustomerName = customer -> {
    String title = customer.getTitle();
    String firstName = customer.getFirstName();
    String lastName = customer.getLastName();
    return title + " " + firstName + " " + lastName;
};
```

## Common Mistakes to Avoid

```java
// ❌ Don't create functional interface variables for simple operations
Predicate<String> isEmpty = str -> str.isEmpty();
if (isEmpty.test(userInput)) { ... }

// ✅ Better: Use lambda directly
if (userInput.isEmpty()) { ... }

// ❌ Don't use direct lambdas for complex logic
list.filter(user -> user.getAge() > 18 && user.getIncome() > 50000 && 
           user.getCreditScore() > 700 && user.hasNoDefaults());

// ✅ Better: Extract to functional interface variable
Predicate<User> isEligibleForLoan = user -> 
    user.getAge() > 18 && 
    user.getIncome() > 50000 && 
    user.getCreditScore() > 700 && 
    user.hasNoDefaults();
    
list.filter(isEligibleForLoan);
```

## Summary

The choice between lambda expressions and functional interface variables depends on:

### **Lambda Expressions** are best for:
- **Simple operations** that fit on one line
- **One-time use** in specific contexts
- **Method parameters** for callbacks
- **Inline processing** where intent is clear

### **Functional Interface Variables** are best for:
- **Complex logic** that spans multiple lines
- **Reusable operations** across different contexts
- **Testable code** that needs to be verified separately
- **Configuration patterns** where behavior is parameterized
- **Performance-critical code** where instances can be reused

### **Key Principles:**
1. **Readability first** - Choose the approach that makes code clearer
2. **Reusability** - Extract to variables when used multiple times
3. **Testability** - Use variables for logic that needs separate testing
4. **Performance** - Consider reusing functional interface instances
5. **Maintainability** - Complex logic is easier to maintain as named variables

Remember: Both approaches implement the same functional interfaces, so you can mix and match based on the specific needs of each situation. 
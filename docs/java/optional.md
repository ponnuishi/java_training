# Java Optional Tutorial

## Introduction

Java Optional is a container class introduced in Java 8 that may or may not contain a non-null value. It's designed to help developers write more robust code by explicitly handling the absence of values, reducing the likelihood of `NullPointerException`.

---

## 1. What is Optional?

Optional is a wrapper class that can hold either:
- A value (non-null)
- No value (empty)

It forces developers to explicitly handle the case where a value might be absent, making code more readable and less error-prone.

**Key Benefits:**
- Eliminates `NullPointerException` in many cases
- Makes code more expressive and self-documenting
- Encourages functional programming patterns
- Improves API design by making nullability explicit

---

## 2. Creating Optional Objects

### 2.1 Optional.of()
Creates an Optional with a non-null value. Throws `NullPointerException` if value is null.

```java
Optional<String> optional = Optional.of("Hello");
// Optional<String> nullOptional = Optional.of(null); // Throws NPE
```

### 2.2 Optional.ofNullable()
Creates an Optional that may contain null. Returns empty Optional if value is null.

```java
Optional<String> optional1 = Optional.ofNullable("Hello");    // Contains "Hello"
Optional<String> optional2 = Optional.ofNullable(null);       // Empty Optional
```

### 2.3 Optional.empty()
Creates an empty Optional.

```java
Optional<String> emptyOptional = Optional.empty();
```

---

## 3. Checking for Values

### 3.1 isPresent() and isEmpty()
```java
Optional<String> optional = Optional.of("Hello");

if (optional.isPresent()) {
    System.out.println("Value is present: " + optional.get());
}

if (optional.isEmpty()) {  // Java 11+
    System.out.println("No value present");
}
```

### 3.2 ifPresent() and ifPresentOrElse()
```java
Optional<String> optional = Optional.of("Hello");

// Execute action if value is present
optional.ifPresent(System.out::println);

// Execute action if present, or else run alternative (Java 9+)
optional.ifPresentOrElse(
    System.out::println,
    () -> System.out.println("No value")
);
```

---

## 4. Retrieving Values

### 4.1 get()
Returns the value if present, throws `NoSuchElementException` if empty.
**⚠️ Use with caution - prefer other methods.**

```java
Optional<String> optional = Optional.of("Hello");
String value = optional.get(); // "Hello"
```

### 4.2 orElse()
Returns the value if present, otherwise returns the provided default.

```java
Optional<String> optional = Optional.empty();
String value = optional.orElse("Default"); // "Default"
```

### 4.3 orElseGet()
Returns the value if present, otherwise returns the result of the supplier.

```java
Optional<String> optional = Optional.empty();
String value = optional.orElseGet(() -> "Generated Default");
```

### 4.4 orElseThrow()
Returns the value if present, otherwise throws an exception.

```java
Optional<String> optional = Optional.empty();
String value = optional.orElseThrow(); // Throws NoSuchElementException
String value2 = optional.orElseThrow(() -> new IllegalStateException("No value"));
```

---

## 5. Transforming Values

### 5.1 map()
Transforms the value if present, returns empty Optional if original was empty.

```java
Optional<String> optional = Optional.of("hello");
Optional<String> upper = optional.map(String::toUpperCase); // Optional["HELLO"]

Optional<Integer> length = optional.map(String::length); // Optional[5]
```

### 5.2 flatMap()
Similar to map(), but flattens nested Optionals.

```java
Optional<String> optional = Optional.of("hello");
Optional<String> result = optional.flatMap(s -> Optional.of(s.toUpperCase()));
```

### 5.3 filter()
Filters the value based on a predicate.

```java
Optional<String> optional = Optional.of("hello");
Optional<String> filtered = optional.filter(s -> s.length() > 3); // Optional["hello"]
Optional<String> empty = optional.filter(s -> s.length() > 10);    // Optional.empty()
```

---

## 6. Combining Optionals

### 6.1 or() (Java 9+)
Returns the Optional if present, otherwise returns the result of the supplier.

```java
Optional<String> optional1 = Optional.empty();
Optional<String> optional2 = Optional.of("backup");
Optional<String> result = optional1.or(() -> optional2); // Optional["backup"]
```

### 6.2 Chaining Operations
```java
Optional<String> result = Optional.of("  hello  ")
    .filter(s -> !s.trim().isEmpty())
    .map(String::trim)
    .map(String::toUpperCase)
    .filter(s -> s.startsWith("H"));
```

---

## 7. Common Patterns

### 7.1 Repository Pattern
```java
public Optional<User> findUserById(Long id) {
    // Instead of returning null, return Optional
    User user = database.findById(id);
    return Optional.ofNullable(user);
}

// Usage
Optional<User> userOpt = repository.findUserById(1L);
userOpt.ifPresent(user -> System.out.println("Found: " + user.getName()));
```

### 7.2 Configuration Values
```java
public class Config {
    public Optional<String> getDatabaseUrl() {
        return Optional.ofNullable(System.getProperty("db.url"));
    }
}

// Usage
String dbUrl = config.getDatabaseUrl()
    .orElse("jdbc:h2:mem:testdb");
```

### 7.3 Nested Object Access
```java
// Instead of multiple null checks
public Optional<String> getPersonAddressStreet(Person person) {
    return Optional.ofNullable(person)
        .map(Person::getAddress)
        .map(Address::getStreet);
}
```

---

## 8. Best Practices

### 8.1 DO's
- Use Optional as return type for methods that might not return a value
- Use `orElse()` for simple default values
- Use `orElseGet()` for expensive default value computation
- Chain operations using `map()`, `filter()`, and `flatMap()`
- Use `ifPresent()` instead of `isPresent()` + `get()`

### 8.2 DON'Ts
- Don't use Optional for fields/parameters (use it for return types)
- Don't use `get()` without checking `isPresent()` first
- Don't use `Optional.of()` with potentially null values
- Don't use Optional for collections (return empty collection instead)
- Don't use Optional just to avoid null checks in internal methods

---

## 9. Optional vs Null

### Traditional Null Handling
```java
public String processUser(User user) {
    if (user != null) {
        Address address = user.getAddress();
        if (address != null) {
            return address.getStreet();
        }
    }
    return "Unknown";
}
```

### Optional Approach
```java
public String processUser(Optional<User> userOpt) {
    return userOpt
        .map(User::getAddress)
        .map(Address::getStreet)
        .orElse("Unknown");
}
```

---

## 10. Performance Considerations

- Optional creates object overhead
- Use primitives (OptionalInt, OptionalLong, OptionalDouble) for primitive types
- Consider performance impact in hot code paths
- Optional is not serializable by default

---

## 11. Integration with Streams

Optional works seamlessly with Java Streams:

```java
List<Optional<String>> optionals = Arrays.asList(
    Optional.of("hello"),
    Optional.empty(),
    Optional.of("world")
);

List<String> values = optionals.stream()
    .flatMap(Optional::stream)  // Java 9+
    .collect(Collectors.toList());
```

---

## 12. Common Mistakes

### Mistake 1: Using Optional for fields
```java
// DON'T
public class Person {
    private Optional<String> name; // Wrong!
}

// DO
public class Person {
    private String name; // Can be null
    
    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }
}
```

### Mistake 2: Using get() without checking
```java
// DON'T
Optional<String> opt = getValue();
String value = opt.get(); // Can throw exception!

// DO
String value = opt.orElse("default");
```

---

## 13. Summary

Optional is a powerful tool for handling null values more explicitly and safely. It encourages better API design and reduces NullPointerException occurrences. However, it should be used judiciously and primarily for return types where the absence of a value is a valid scenario.

### Key Takeaways:
- Use Optional for return types, not fields or parameters
- Prefer `orElse()`, `orElseGet()`, and `ifPresent()` over `get()`
- Chain operations using `map()`, `filter()`, and `flatMap()`
- Consider performance implications in critical code paths
- Make your APIs more expressive and self-documenting


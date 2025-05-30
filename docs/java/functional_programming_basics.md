# Java Functional Programming Basics

## What is Functional Programming?

Functional Programming is a programming style that treats computation as the evaluation of mathematical functions. In Java, functional programming features were introduced in Java 8 and make code more concise and readable.

### Key Benefits:
- **Less code** - More concise than traditional approaches
- **More readable** - Intent is clearer
- **Safer** - Reduces common errors like null pointer exceptions
- **Easier to test** - Pure functions are predictable

## Before vs After: A Simple Example

### Traditional Way (Imperative)
```java
List<String> names = Arrays.asList("alice", "bob", "charlie");

// Convert to uppercase and print
List<String> upperNames = new ArrayList<>();
for (String name : names) {
    upperNames.add(name.toUpperCase());
}

for (String name : upperNames) {
    System.out.println(name);
}
```

### Functional Way (Declarative)
```java
List<String> names = Arrays.asList("alice", "bob", "charlie");

// Convert to uppercase and print using functional interfaces
Function<String, String> toUpperCase = String::toUpperCase;
Consumer<String> printer = System.out::println;

names.forEach(name -> printer.accept(toUpperCase.apply(name)));
```

## Lambda Expressions

Lambda expressions are anonymous functions that make code more concise.

### Basic Syntax
```java
// Old way with anonymous class
Runnable oldWay = new Runnable() {
    @Override
    public void run() {
        System.out.println("Hello World");
    }
};

// New way with lambda
Runnable newWay = () -> System.out.println("Hello World");
```

### Lambda with Parameters
```java
// One parameter
Consumer<String> printer = message -> System.out.println(message);
printer.accept("Hello");

// Multiple parameters
BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
int sum = add.apply(5, 3); // Returns 8

// Multiple statements
Consumer<String> complexPrinter = message -> {
    String formatted = "Message: " + message.toUpperCase();
    System.out.println(formatted);
};
```

## Functional Interfaces Explained

### What are Functional Interfaces?
A functional interface is an interface that contains **exactly one abstract method**. They can have multiple default or static methods, but only one abstract method. This single method is what lambda expressions implement.

```java
// This is a functional interface
@FunctionalInterface
public interface Processor {
    String process(String input);  // Only one abstract method
    
    // Default methods are allowed
    default String processWithPrefix(String input) {
        return "Processed: " + process(input);
    }
    
    // Static methods are allowed
    static Processor identity() {
        return input -> input;
    }
}
```

### The @FunctionalInterface Annotation
While not required, it's a good practice to use `@FunctionalInterface` annotation. It:
- Documents that the interface is intended to be functional
- Compiler checks that it has exactly one abstract method
- Prevents accidental addition of abstract methods

```java
@FunctionalInterface
public interface Calculator {
    int calculate(int a, int b);
    // If you try to add another abstract method, compiler will error
}

// Usage
Calculator add = (a, b) -> a + b;
Calculator multiply = (a, b) -> a * b;
```

## Common Built-in Functional Interfaces

Java provides several built-in functional interfaces in the `java.util.function` package:

### 1. Predicate<T> - Test a condition
**Purpose**: Takes one argument and returns a boolean
**Method**: `boolean test(T t)`

```java
// Basic usage
Predicate<String> isEmpty = str -> str.isEmpty();
Predicate<String> isLong = str -> str.length() > 5;
Predicate<Integer> isEven = num -> num % 2 == 0;

// Testing
System.out.println(isEmpty.test(""));      // true
System.out.println(isLong.test("hello"));  // false
System.out.println(isEven.test(4));        // true

// Combining predicates
Predicate<String> isNotEmpty = isEmpty.negate();
Predicate<String> isLongAndNotEmpty = isLong.and(isNotEmpty);
Predicate<String> isEmptyOrLong = isEmpty.or(isLong);

System.out.println(isNotEmpty.test("hello"));        // true
System.out.println(isLongAndNotEmpty.test("hello")); // false
System.out.println(isEmptyOrLong.test(""));          // true
```

### 2. Function<T, R> - Transform input to output
**Purpose**: Takes one argument of type T and returns a result of type R
**Method**: `R apply(T t)`

```java
// Basic usage
Function<String, Integer> getLength = str -> str.length();
Function<String, String> toUpperCase = str -> str.toUpperCase();
Function<Integer, String> toString = num -> "Number: " + num;

System.out.println(getLength.apply("hello"));    // 5
System.out.println(toUpperCase.apply("hello"));  // HELLO
System.out.println(toString.apply(42));          // Number: 42

// Chaining functions
Function<String, String> addPrefix = str -> "Hello " + str;
Function<String, String> addSuffix = str -> str + "!";
Function<String, String> greet = addPrefix.andThen(addSuffix);

System.out.println(greet.apply("World")); // Hello World!

// Function composition
Function<String, Integer> getUpperLength = toUpperCase.andThen(getLength);
System.out.println(getUpperLength.apply("hello")); // 5
```

### 3. Consumer<T> - Perform an action
**Purpose**: Takes one argument and performs an operation without returning anything
**Method**: `void accept(T t)`

```java
// Basic usage
Consumer<String> print = str -> System.out.println(str);
Consumer<String> logToFile = str -> {
    // Simulate file logging
    System.out.println("LOG: " + str);
};

print.accept("Hello World");      // Hello World
logToFile.accept("Error message"); // LOG: Error message

// Chaining consumers
Consumer<String> printAndLog = print.andThen(logToFile);
printAndLog.accept("Important message");
// Output:
// Important message
// LOG: Important message

// Practical example with lists
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
Consumer<String> formatter = name -> System.out.println("Name: " + name);
names.forEach(formatter);
```

### 4. Supplier<T> - Provide a value
**Purpose**: Takes no arguments and returns a result
**Method**: `T get()`

```java
// Basic usage
Supplier<String> getGreeting = () -> "Hello World";
Supplier<Double> getRandom = () -> Math.random();
Supplier<List<String>> getNewList = () -> new ArrayList<>();

System.out.println(getGreeting.get()); // Hello World
System.out.println(getRandom.get());   // Random number
List<String> list = getNewList.get();  // New ArrayList

// Lazy evaluation example
Supplier<String> expensiveOperation = () -> {
    System.out.println("Performing expensive operation...");
    return "Result";
};

// Only executes when needed
String result = expensiveOperation.get();

// Factory pattern example
Supplier<Random> randomFactory = Random::new;
Random random1 = randomFactory.get();
Random random2 = randomFactory.get(); // Different instance
```

## Additional Common Functional Interfaces

### 5. BiFunction<T, U, R> - Two inputs, one output
```java
BiFunction<String, String, String> concat = (a, b) -> a + b;
BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
BiFunction<String, Integer, String> repeat = (str, times) -> str.repeat(times);

System.out.println(concat.apply("Hello", "World")); // HelloWorld
System.out.println(add.apply(5, 3));                // 8
System.out.println(repeat.apply("Hi", 3));          // HiHiHi
```

### 6. UnaryOperator<T> - Special case of Function<T, T>
```java
UnaryOperator<String> toUpper = String::toUpperCase;
UnaryOperator<Integer> square = x -> x * x;

System.out.println(toUpper.apply("hello")); // HELLO
System.out.println(square.apply(5));        // 25
```

### 7. BinaryOperator<T> - Special case of BiFunction<T, T, T>
```java
BinaryOperator<Integer> max = Integer::max;
BinaryOperator<String> combine = (a, b) -> a + " & " + b;

System.out.println(max.apply(5, 10));           // 10
System.out.println(combine.apply("A", "B"));    // A & B
```

## Creating Custom Functional Interfaces

```java
@FunctionalInterface
public interface StringValidator {
    boolean isValid(String input);
    
    // Default method for combining validators
    default StringValidator and(StringValidator other) {
        return input -> this.isValid(input) && other.isValid(input);
    }
}

@FunctionalInterface
public interface MathOperation {
    double calculate(double a, double b);
}

// Usage
public class FunctionalInterfaceExample {
    public static void main(String[] args) {
        // Using custom StringValidator
        StringValidator notEmpty = str -> !str.trim().isEmpty();
        StringValidator minLength = str -> str.length() >= 3;
        StringValidator combined = notEmpty.and(minLength);
        
        System.out.println(combined.isValid("Hi"));    // false
        System.out.println(combined.isValid("Hello")); // true
        
        // Using custom MathOperation
        MathOperation power = (a, b) -> Math.pow(a, b);
        MathOperation divide = (a, b) -> a / b;
        
        System.out.println(power.calculate(2, 3));  // 8.0
        System.out.println(divide.calculate(10, 2)); // 5.0
    }
}
```

## Why Use Functional Interfaces?

### 1. **Code Reusability**
```java
// Define once, use many times
Predicate<String> isValidEmail = email -> email.contains("@");
Function<String, String> normalize = String::toLowerCase;

// Use in different contexts
public boolean validateUser(String email) {
    return isValidEmail.test(email);
}

public String processEmail(String email) {
    return normalize.apply(email);
}
```

### 2. **Flexible APIs**
```java
public class DataProcessor {
    public <T> List<T> filter(List<T> items, Predicate<T> condition) {
        List<T> result = new ArrayList<>();
        for (T item : items) {
            if (condition.test(item)) {
                result.add(item);
            }
        }
        return result;
    }
    
    public <T, R> List<R> transform(List<T> items, Function<T, R> mapper) {
        List<R> result = new ArrayList<>();
        for (T item : items) {
            result.add(mapper.apply(item));
        }
        return result;
    }
}

// Usage
DataProcessor processor = new DataProcessor();
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

// Filter long names
List<String> longNames = processor.filter(names, name -> name.length() > 3);

// Transform to uppercase
List<String> upperNames = processor.transform(names, String::toUpperCase);
```

### 3. **Event Handling**
```java
public class EventManager {
    private List<Consumer<String>> listeners = new ArrayList<>();
    
    public void addListener(Consumer<String> listener) {
        listeners.add(listener);
    }
    
    public void fireEvent(String message) {
        listeners.forEach(listener -> listener.accept(message));
    }
}

// Usage
EventManager manager = new EventManager();
manager.addListener(msg -> System.out.println("Console: " + msg));
manager.addListener(msg -> System.out.println("Log: " + msg));
manager.fireEvent("Something happened!"); // Both listeners execute
```

## Method References

Method references are shorthand for lambda expressions that call existing methods.

```java
List<String> names = Arrays.asList("alice", "bob", "charlie");

// Lambda expression
names.stream()
    .map(name -> name.toUpperCase())
    .forEach(name -> System.out.println(name));

// Method reference (shorter)
names.stream()
    .map(String::toUpperCase)
    .forEach(System.out::println);
```

### Types of Method References
```java
// 1. Static method reference
Function<String, Integer> parseInt = Integer::parseInt;

// 2. Instance method reference
String prefix = "Hello ";
Function<String, String> addPrefix = prefix::concat;

// 3. Constructor reference
Supplier<ArrayList<String>> newList = ArrayList::new;
```

## Working with Collections

Functional programming works well with collections using functional interfaces.

### Processing Lists with Functional Interfaces
```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

// Using functional interfaces to process collections
Predicate<String> isLongName = name -> name.length() > 4;
Function<String, String> toUpperCase = String::toUpperCase;
Consumer<String> printer = System.out::println;

// Traditional approach with functional interfaces
List<String> longNames = new ArrayList<>();
for (String name : names) {
    if (isLongName.test(name)) {
        longNames.add(toUpperCase.apply(name));
    }
}

// Print results
longNames.forEach(printer);
```

### Combining Functional Interfaces
```java
// Create reusable functions
Function<String, String> addPrefix = name -> "Mr. " + name;
Function<String, String> addSuffix = name -> name + " Jr.";

// Combine functions
Function<String, String> fullTransform = addPrefix.andThen(addSuffix);

String result = fullTransform.apply("John"); // "Mr. John Jr."
System.out.println(result);
```

## Real-World Example: Processing Student Data

```java
public class Student {
    private String name;
    private int age;
    private double grade;
    
    public Student(String name, int age, double grade) {
        this.name = name;
        this.age = age;
        this.grade = grade;
    }
    
    // Getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getGrade() { return grade; }
    
    @Override
    public String toString() {
        return name + " (Age: " + age + ", Grade: " + grade + ")";
    }
}

public class StudentProcessor {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
            new Student("Alice", 20, 85.5),
            new Student("Bob", 19, 92.0),
            new Student("Charlie", 21, 78.5),
            new Student("David", 20, 88.0)
        );
        
        // Define functional interfaces for processing
        Predicate<Student> highGrade = student -> student.getGrade() > 80.0;
        Function<Student, String> formatStudent = student -> 
            student.getName() + " (Grade: " + student.getGrade() + ")";
        Consumer<String> printer = System.out::println;
        
        // Find students with grade above 80
        List<Student> topStudents = new ArrayList<>();
        for (Student student : students) {
            if (highGrade.test(student)) {
                topStudents.add(student);
            }
        }
        
        // Sort by grade (descending)
        topStudents.sort((s1, s2) -> Double.compare(s2.getGrade(), s1.getGrade()));
        
        System.out.println("Top Students:");
        topStudents.forEach(student -> printer.accept(formatStudent.apply(student)));
        
        // Calculate average grade using functional approach
        Function<List<Student>, Double> calculateAverage = studentList -> {
            double sum = 0.0;
            for (Student student : studentList) {
                sum += student.getGrade();
            }
            return sum / studentList.size();
        };
        
        double averageGrade = calculateAverage.apply(students);
        System.out.println("Average Grade: " + averageGrade);
        
        // Find youngest student
        Function<List<Student>, Student> findYoungest = studentList -> {
            Student youngest = studentList.get(0);
            for (Student student : studentList) {
                if (student.getAge() < youngest.getAge()) {
                    youngest = student;
                }
            }
            return youngest;
        };
        
        Student youngest = findYoungest.apply(students);
        System.out.println("Youngest Student: " + youngest);
    }
}
```

## Common Patterns

### 1. Filtering and Transforming
```java
// Get uppercase names of students with grade > 80
Predicate<Student> highGradeFilter = student -> student.getGrade() > 80.0;
Function<Student, String> nameExtractor = Student::getName;
Function<String, String> upperCaseTransformer = String::toUpperCase;

List<String> topStudentNames = new ArrayList<>();
for (Student student : students) {
    if (highGradeFilter.test(student)) {
        String name = nameExtractor.apply(student);
        topStudentNames.add(upperCaseTransformer.apply(name));
    }
}
```

### 2. Composing Functions
```java
// Combine multiple transformations
Function<Student, String> getUpperCaseName = 
    Student::getName.andThen(String::toUpperCase);

Function<Student, String> getFormattedName = 
    getUpperCaseName.andThen(name -> "Student: " + name);

String result = getFormattedName.apply(student);
```

### 3. Conditional Processing
```java
// Create flexible condition checkers
Predicate<Student> isAdult = student -> student.getAge() >= 18;
Predicate<Student> hasGoodGrade = student -> student.getGrade() >= 85.0;
Predicate<Student> isExcellent = isAdult.and(hasGoodGrade);

// Use in processing
for (Student student : students) {
    if (isExcellent.test(student)) {
        System.out.println("Excellent student: " + student.getName());
    }
}
```

### 4. Reusable Operations
```java
// Create reusable functional components
Consumer<Student> printStudent = student -> 
    System.out.println("Student: " + student.getName());

Consumer<Student> printGrade = student -> 
    System.out.println("Grade: " + student.getGrade());

// Combine operations
Consumer<Student> printFull = printStudent.andThen(printGrade);

// Apply to all students
students.forEach(printFull);
```

## Best Practices

### 1. Use Method References When Possible
```java
// Good - Method reference
Function<String, String> toUpperCase = String::toUpperCase;
Consumer<String> print = System.out::println;

// Less good - Lambda expression
Function<String, String> toUpperCase2 = name -> name.toUpperCase();
Consumer<String> print2 = name -> System.out.println(name);
```

### 2. Compose Functions for Readability
```java
// Good - Compose functions
Function<String, String> addPrefix = name -> "Mr. " + name;
Function<String, String> addSuffix = name -> name + " Jr.";
Function<String, String> fullTransform = addPrefix.andThen(addSuffix);

String result = fullTransform.apply("John");

// Less good - Complex lambda
Function<String, String> transform = name -> "Mr. " + name + " Jr.";
```

### 3. Use Descriptive Names for Lambda Variables
```java
// Good
Predicate<Student> isHighPerformer = student -> student.getGrade() > 85.0;
Function<Student, String> getFullName = student -> 
    student.getFirstName() + " " + student.getLastName();

// Less good
Predicate<Student> p = s -> s.getGrade() > 85.0;
Function<Student, String> f = s -> s.getFirstName() + " " + s.getLastName();
```

### 4. Keep Lambda Expressions Simple
```java
// Good - Simple lambda
Consumer<String> simpleLogger = message -> System.out.println(message);

// Less good - Complex lambda (extract to method)
Consumer<String> complexLogger = message -> {
    String timestamp = LocalDateTime.now().toString();
    String formatted = "[" + timestamp + "] " + message.toUpperCase();
    System.out.println(formatted);
    logToFile(formatted);
};
```

## Summary

Functional programming in Java makes code:
- **More concise** - Less boilerplate code
- **More readable** - Intent is clearer
- **More reusable** - Functions can be composed and reused
- **Easier to test** - Pure functions are predictable

### Key Concepts to Remember:
1. **Lambda expressions** - Anonymous functions for concise code
2. **Functional interfaces** - Single-method interfaces (Predicate, Function, Consumer, Supplier)
3. **Method references** - Shorthand notation for lambdas
4. **Function composition** - Combining functions with `andThen` and `compose`
5. **Pure functions** - Functions that don't have side effects

### When to Use Functional Programming:
- **Data transformations** - Converting one type to another
- **Filtering operations** - Selecting elements based on conditions
- **Event handling** - Responding to user actions or system events
- **Callback mechanisms** - Passing behavior as parameters
- **Configuration** - Defining behavior through functions

Start with simple lambda expressions and functional interfaces, then gradually learn to compose them for more complex operations. The key is to think in terms of functions that transform data rather than imperative loops that modify state. 
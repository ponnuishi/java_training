# Java Annotations Tutorial

## What are Java Annotations?

Java Annotations are a form of metadata that provide information about the code to the compiler, runtime, or other tools. They start with the `@` symbol and can be applied to classes, methods, fields, parameters, and other program elements.

### Key Concepts:
1. **Metadata**: Annotations provide data about the code, not the code itself
2. **Compile-time Processing**: Some annotations are processed during compilation
3. **Runtime Reflection**: Some annotations can be accessed at runtime
4. **Tool Integration**: Annotations help tools understand and process code

## Simple Example: Why We Need Annotations

### Without Annotations (Old Way)
```java
// Without annotations, we need comments and naming conventions
public class User {
    // This field is required and must not be null
    private String name;
    
    // This method should be called before saving
    public void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
    }
    
    // This method is deprecated - use newSave() instead
    public void save() {
        // Old implementation
    }
    
    // This is the new save method
    public void newSave() {
        // New implementation
    }
}

// Usage - developer must remember all the rules
public class OldWayExample {
    public void demonstrateOldWay() {
        User user = new User();
        // Must remember to call validate first
        user.validate();
        // Must remember this is deprecated
        // user.save();  // Should not use this
        user.newSave();  // Should use this instead
    }
}
```

### With Annotations (Modern Way)
```java
// With annotations, the rules are explicit and enforceable
public class User {
    @NotNull  // Compiler and tools know this field cannot be null
    private String name;
    
    @PreSave  // Custom annotation - tools know to call this before saving
    public void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
    }
    
    @Deprecated  // Compiler warns when this method is used
    public void save() {
        // Old implementation
    }
    
    public void newSave() {
        // New implementation
    }
}

// Usage - compiler and tools enforce the rules
public class ModernExample {
    public void demonstrateModernWay() {
        User user = new User();
        // Tools automatically call @PreSave methods
        // Compiler warns about @Deprecated methods
        // @NotNull validation happens automatically
    }
}
```

### Benefits Demonstrated
1. **Compile-time Safety**: Compiler catches violations
```java
@NotNull
private String name;

// This would cause a compile error with proper annotation processing
// name = null;  // Error: cannot assign null to @NotNull field
```

2. **Tool Integration**: IDEs and build tools understand the code better
```java
@Deprecated
public void oldMethod() { }

// IDE shows warning and strikethrough
// oldMethod();  // IDE warns: "Method is deprecated"
```

3. **Self-Documenting Code**: Annotations make intent explicit
```java
@Override  // Clearly shows this method overrides a parent method
public String toString() {
    return "User: " + name;
}
```

## Built-in Annotations

### 1. @Override
Indicates that a method is intended to override a method in a superclass.

```java
public class Animal {
    public void makeSound() {
        System.out.println("Some sound");
    }
}

public class Dog extends Animal {
    @Override  // Ensures we're actually overriding a method
    public void makeSound() {
        System.out.println("Woof!");
    }
    
    // This would cause a compile error because there's no such method in Animal
    // @Override
    // public void makeNoise() { }  // Error: method does not override
}
```

### 2. @Deprecated
Marks a method, class, or field as deprecated (no longer recommended for use).

```java
public class Calculator {
    @Deprecated
    public int addOld(int a, int b) {
        return a + b;
    }
    
    public int add(int a, int b) {
        return a + b;
    }
    
    @Deprecated(since = "2.0", forRemoval = true)
    public int multiplyOld(int a, int b) {
        return a * b;
    }
}

// Usage
public class DeprecationExample {
    public void demonstrate() {
        Calculator calc = new Calculator();
        
        // IDE shows warning and strikethrough
        calc.addOld(5, 3);      // Deprecated method
        calc.add(5, 3);         // Current method
        
        // This will be removed in future versions
        calc.multiplyOld(4, 5); // Deprecated with removal notice
    }
}
```

### 3. @SuppressWarnings
Suppresses compiler warnings for a specific element.

```java
public class WarningExample {
    @SuppressWarnings("unchecked")
    public void demonstrateSuppression() {
        // This would normally generate an unchecked warning
        List<String> list = new ArrayList();
        list.add("Hello");
        
        // But the warning is suppressed
    }
    
    @SuppressWarnings({"unchecked", "deprecation"})
    public void suppressMultipleWarnings() {
        // Suppresses both unchecked and deprecation warnings
        List list = new ArrayList();
        // Some deprecated method call
    }
}
```

### 4. @FunctionalInterface
Indicates that an interface is a functional interface (has exactly one abstract method).

```java
@FunctionalInterface
public interface MathOperation {
    int operate(int a, int b);
    
    // Can have default methods
    default int add(int a, int b) {
        return a + b;
    }
    
    // Can have static methods
    static MathOperation getDefault() {
        return (a, b) -> a + b;
    }
    
    // Cannot have more than one abstract method
    // int anotherMethod(int x);  // This would cause compile error
}

// Usage
public class FunctionalInterfaceExample {
    public void demonstrate() {
        MathOperation add = (a, b) -> a + b;
        MathOperation multiply = (a, b) -> a * b;
        
        System.out.println(add.operate(5, 3));      // 8
        System.out.println(multiply.operate(4, 5)); // 20
    }
}
```

## Custom Annotations

### 1. Simple Custom Annotation
```java
// Define a simple annotation
public @interface Author {
    String name();
    String email() default "unknown@example.com";
    int version() default 1;
}

// Use the annotation
@Author(name = "John Doe", email = "john@example.com", version = 2)
public class MyClass {
    @Author(name = "Jane Smith")
    public void myMethod() {
        // Method implementation
    }
}
```

### 2. Annotation with No Elements
```java
// Marker annotation (no elements)
public @interface Test {
}

// Single-element annotation
public @interface Version {
    int value();  // Special element name 'value'
}

// Usage
@Test
@Version(1)
public class SimpleTest {
    // Class implementation
}
```

### 3. Annotation with Arrays
```java
public @interface Tags {
    String[] value();
    int[] numbers() default {1, 2, 3};
}

// Usage
@Tags({"java", "tutorial", "annotations"})
@Tags(value = {"important"}, numbers = {5, 10, 15})
public class TaggedClass {
    // Class implementation
}
```

## Annotation Retention and Target

### Retention Policy
Controls how long the annotation is retained.

```java
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// SOURCE: Discarded during compilation
@Retention(RetentionPolicy.SOURCE)
public @interface SourceOnly {
    String value();
}

// CLASS: Stored in .class file but not available at runtime
@Retention(RetentionPolicy.CLASS)
public @interface CompileTime {
    String value();
}

// RUNTIME: Available at runtime through reflection
@Retention(RetentionPolicy.RUNTIME)
public @interface RuntimeVisible {
    String value();
}
```

### Target
Specifies where the annotation can be applied.

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

// Can be applied to classes and interfaces
@Target({ElementType.TYPE})
public @interface ClassOnly {
    String value();
}

// Can be applied to methods only
@Target(ElementType.METHOD)
public @interface MethodOnly {
    String value();
}

// Can be applied to fields only
@Target(ElementType.FIELD)
public @interface FieldOnly {
    String value();
}

// Can be applied to multiple targets
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface MethodOrField {
    String value();
}

// Can be applied anywhere
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, 
         ElementType.PARAMETER, ElementType.CONSTRUCTOR})
public @interface Anywhere {
    String value();
}
```

## Practical Examples

### 1. Validation Annotations
```java
// Custom validation annotations
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotNull {
    String message() default "Field cannot be null";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MinLength {
    int value();
    String message() default "Field is too short";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Email {
    String message() default "Invalid email format";
}

// User class with validation annotations
public class User {
    @NotNull(message = "Name is required")
    private String name;
    
    @MinLength(value = 6, message = "Password must be at least 6 characters")
    private String password;
    
    @Email(message = "Please provide a valid email address")
    private String email;
    
    // Constructor, getters, setters...
    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
    
    // Getters
    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
}

// Validation processor
public class Validator {
    public static List<String> validate(Object obj) {
        List<String> errors = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            
            // Check @NotNull
            if (field.isAnnotationPresent(NotNull.class)) {
                try {
                    Object value = field.get(obj);
                    if (value == null) {
                        NotNull annotation = field.getAnnotation(NotNull.class);
                        errors.add(annotation.message());
                    }
                } catch (IllegalAccessException e) {
                    errors.add("Validation error for " + field.getName());
                }
            }
            
            // Check @MinLength
            if (field.isAnnotationPresent(MinLength.class)) {
                try {
                    Object value = field.get(obj);
                    if (value instanceof String) {
                        String strValue = (String) value;
                        MinLength annotation = field.getAnnotation(MinLength.class);
                        if (strValue.length() < annotation.value()) {
                            errors.add(annotation.message());
                        }
                    }
                } catch (IllegalAccessException e) {
                    errors.add("Validation error for " + field.getName());
                }
            }
            
            // Check @Email
            if (field.isAnnotationPresent(Email.class)) {
                try {
                    Object value = field.get(obj);
                    if (value instanceof String) {
                        String email = (String) value;
                        if (!email.contains("@")) {
                            Email annotation = field.getAnnotation(Email.class);
                            errors.add(annotation.message());
                        }
                    }
                } catch (IllegalAccessException e) {
                    errors.add("Validation error for " + field.getName());
                }
            }
        }
        
        return errors;
    }
}

// Usage
public class ValidationExample {
    public void demonstrate() {
        User user1 = new User("John", "123", "john@example.com");
        List<String> errors1 = Validator.validate(user1);
        System.out.println("Errors for user1: " + errors1);
        // Output: [Password must be at least 6 characters]
        
        User user2 = new User(null, "password123", "invalid-email");
        List<String> errors2 = Validator.validate(user2);
        System.out.println("Errors for user2: " + errors2);
        // Output: [Name is required, Please provide a valid email address]
    }
}
```

### 2. Database Mapping Annotations
```java
// Database mapping annotations
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {
    String name();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    String name();
    boolean primaryKey() default false;
    boolean nullable() default true;
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Id {
    boolean autoIncrement() default true;
}

// Entity class with database annotations
@Table(name = "users")
public class User {
    @Id(autoIncrement = true)
    @Column(name = "id", primaryKey = true, nullable = false)
    private Long id;
    
    @Column(name = "username", nullable = false)
    private String username;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "created_at")
    private String createdAt;
    
    // Constructor, getters, setters...
    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.createdAt = java.time.LocalDateTime.now().toString();
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}

// Simple ORM processor
public class SimpleORM {
    public static String generateCreateTable(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new IllegalArgumentException("Class must have @Table annotation");
        }
        
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ").append(tableAnnotation.name()).append(" (");
        
        boolean first = true;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                if (!first) {
                    sql.append(", ");
                }
                first = false;
                
                Column columnAnnotation = field.getAnnotation(Column.class);
                sql.append(columnAnnotation.name()).append(" ");
                
                // Simple type mapping
                if (field.getType() == Long.class || field.getType() == long.class) {
                    sql.append("BIGINT");
                } else if (field.getType() == String.class) {
                    sql.append("VARCHAR(255)");
                } else {
                    sql.append("VARCHAR(255)");
                }
                
                if (columnAnnotation.primaryKey()) {
                    sql.append(" PRIMARY KEY");
                }
                
                if (!columnAnnotation.nullable()) {
                    sql.append(" NOT NULL");
                }
            }
        }
        
        sql.append(")");
        return sql.toString();
    }
    
    public static String generateInsert(Object obj) {
        Class<?> clazz = obj.getClass();
        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new IllegalArgumentException("Class must have @Table annotation");
        }
        
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(tableAnnotation.name()).append(" (");
        
        List<String> columns = new ArrayList<>();
        List<String> values = new ArrayList<>();
        
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                Column columnAnnotation = field.getAnnotation(Column.class);
                columns.add(columnAnnotation.name());
                
                try {
                    Object value = field.get(obj);
                    if (value instanceof String) {
                        values.add("'" + value + "'");
                    } else {
                        values.add(String.valueOf(value));
                    }
                } catch (IllegalAccessException e) {
                    values.add("NULL");
                }
            }
        }
        
        sql.append(String.join(", ", columns));
        sql.append(") VALUES (");
        sql.append(String.join(", ", values));
        sql.append(")");
        
        return sql.toString();
    }
}

// Usage
public class ORMExample {
    public void demonstrate() {
        // Generate CREATE TABLE SQL
        String createTableSQL = SimpleORM.generateCreateTable(User.class);
        System.out.println("CREATE TABLE SQL:");
        System.out.println(createTableSQL);
        
        // Generate INSERT SQL
        User user = new User("john_doe", "john@example.com");
        String insertSQL = SimpleORM.generateInsert(user);
        System.out.println("\nINSERT SQL:");
        System.out.println(insertSQL);
    }
}
```

### 3. Logging Annotations
```java
// Logging annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogMethod {
    String level() default "INFO";
    boolean logParameters() default true;
    boolean logReturnValue() default false;
}

// Logging aspect (simplified)
public class LoggingAspect {
    public static void logMethodCall(Method method, Object[] args, Object result) {
        if (method.isAnnotationPresent(LogMethod.class)) {
            LogMethod annotation = method.getAnnotation(LogMethod.class);
            String level = annotation.level();
            
            System.out.println("[" + level + "] Calling method: " + method.getName());
            
            if (annotation.logParameters()) {
                System.out.println("  Parameters: " + java.util.Arrays.toString(args));
            }
            
            if (annotation.logReturnValue()) {
                System.out.println("  Return value: " + result);
            }
        }
    }
}

// Service class with logging
public class UserService {
    @LogMethod(level = "INFO", logParameters = true, logReturnValue = true)
    public User createUser(String username, String email) {
        System.out.println("Creating user: " + username);
        return new User(username, email);
    }
    
    @LogMethod(level = "DEBUG", logParameters = false)
    public void deleteUser(Long id) {
        System.out.println("Deleting user with ID: " + id);
    }
    
    @LogMethod
    public List<User> getAllUsers() {
        System.out.println("Getting all users");
        return new ArrayList<>();
    }
}

// Usage
public class LoggingExample {
    public void demonstrate() {
        UserService service = new UserService();
        
        // These calls would be intercepted by the logging aspect
        User user = service.createUser("john", "john@example.com");
        service.deleteUser(1L);
        List<User> users = service.getAllUsers();
    }
}
```


## Common Use Cases

### 1. Framework Integration
```java
// Spring-like annotations
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {
    String value() default "";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Bean {
    String name() default "";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Autowired {
}
```

### 2. Testing Annotations
```java
// JUnit-like annotations
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
    String description() default "";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Before {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface After {
}
```

### 3. API Documentation
```java
// Swagger-like annotations
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiOperation {
    String value();
    String notes() default "";
    String[] tags() default {};
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ApiParam {
    String name();
    String description() default "";
    boolean required() default false;
}
```


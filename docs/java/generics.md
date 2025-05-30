# Java Generics Guide

Java Generics provide type safety and code reusability by allowing you to write classes, interfaces, and methods that can work with different types while maintaining type safety at compile time.

## Simple Example: Why We Need Generics

### Without Generics (Old Way)
```java
// Without generics, we need separate classes for different types
public class IntegerBox {
    private Integer value;
    
    public void set(Integer value) { this.value = value; }
    public Integer get() { return value; }
}

public class StringBox {
    private String value;
    
    public void set(String value) { this.value = value; }
    public String get() { return value; }
}

// Usage
public class OldWayExample {
    public void demonstrateOldWay() {
        IntegerBox intBox = new IntegerBox();
        intBox.set(42);
        Integer intValue = intBox.get();
        
        StringBox strBox = new StringBox();
        strBox.set("Hello");
        String strValue = strBox.get();
        
        // Need different classes for each type!
        // Lots of duplicate code...
    }
}
```

### With Generics 
```java
// One class works for all types
public class Box<T> {
    private T value;
    
    public void set(T value) { this.value = value; }
    public T get() { return value; }
}

// Usage
public class ModernExample {
    public void demonstrateModernWay() {
        // For integers
        Box<Integer> intBox = new Box<>();
        intBox.set(42);
        Integer intValue = intBox.get();  // No casting needed!
        
        // For strings
        Box<String> strBox = new Box<>();
        strBox.set("Hello");
        String strValue = strBox.get();  // Type-safe!
        
        // For custom types
        Box<Person> personBox = new Box<>();
        personBox.set(new Person("Alice"));
        Person person = personBox.get();
        
        // Even for collections
        Box<List<String>> listBox = new Box<>();
        listBox.set(Arrays.asList("a", "b", "c"));
        List<String> list = listBox.get();
    }
}
```

### Benefits Demonstrated
1. **Type Safety**: Compiler catches type errors
```java
Box<String> strBox = new Box<>();
strBox.set("Hello");
// This won't compile - type safety!
// strBox.set(123);  // Error: incompatible types

// Without generics, this would compile but fail at runtime
Object obj = new Object();
String str = (String) obj;  // Runtime ClassCastException!
```

2. **Code Reusability**: One class, many types
```java
// Same Box class works with any type
Box<Integer> numbers = new Box<>();
Box<String> texts = new Box<>();
Box<Double> decimals = new Box<>();
Box<List<String>> lists = new Box<>();
```

3. **Better IDE Support**: Auto-completion and error detection
```java
Box<Person> personBox = new Box<>();
Person person = personBox.get();  // IDE knows the exact type
// person.  // IDE shows all Person methods
```

## Real-World Simple Examples

### 1. Simple Generic Container
```java
// A basic container that can hold any type
public class Container<T> {
    private T item;
    
    public void store(T item) {
        this.item = item;
    }
    
    public T retrieve() {
        return item;
    }
}

// Usage
public class ContainerExample {
    public void demonstrate() {
        // Store and retrieve a String
        Container<String> stringContainer = new Container<>();
        stringContainer.store("Hello Java");
        String text = stringContainer.retrieve();  // Returns "Hello Java"
        
        // Store and retrieve an Integer
        Container<Integer> intContainer = new Container<>();
        intContainer.store(100);
        int number = intContainer.retrieve();  // Returns 100
        
        // Store and retrieve a custom object
        Container<Student> studentContainer = new Container<>();
        studentContainer.store(new Student("Alice"));
        Student student = studentContainer.retrieve();
    }
}
```

### 2. Simple Generic List
```java
// A basic list implementation
public class SimpleList<E> {
    private E[] elements;
    private int size;
    
    @SuppressWarnings("unchecked")
    public SimpleList(int capacity) {
        elements = (E[]) new Object[capacity];
        size = 0;
    }
    
    public void add(E element) {
        if (size < elements.length) {
            elements[size++] = element;
        }
    }
    
    public E get(int index) {
        if (index >= 0 && index < size) {
            return elements[index];
        }
        throw new IndexOutOfBoundsException("Index: " + index);
    }
    
    public int size() {
        return size;
    }
}

// Usage
public class ListExample {
    public void demonstrate() {
        // List of strings
        SimpleList<String> names = new SimpleList<>(3);
        names.add("Alice");
        names.add("Bob");
        names.add("Charlie");
        
        String firstPerson = names.get(0);  // Returns "Alice"
        
        // List of integers
        SimpleList<Integer> scores = new SimpleList<>(4);
        scores.add(95);
        scores.add(87);
        scores.add(91);
        
        int topScore = scores.get(0);  // Returns 95
    }
}
```

### 3. Simple Pair Class
```java
// A class to hold two related values of any types
public class Pair<K, V> {
    private K first;
    private V second;
    
    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }
    
    public K getFirst() {
        return first;
    }
    
    public V getSecond() {
        return second;
    }
    
    public void setFirst(K first) {
        this.first = first;
    }
    
    public void setSecond(V second) {
        this.second = second;
    }
}

// Usage
public class PairExample {
    public void demonstrate() {
        // Name and age pair
        Pair<String, Integer> person = new Pair<>("Alice", 25);
        String name = person.getFirst();    // Returns "Alice"
        int age = person.getSecond();       // Returns 25
        
        // Student and grade pair
        Pair<Student, Double> studentGrade = new Pair<>(new Student("Bob"), 95.5);
        Student student = studentGrade.getFirst();
        double grade = studentGrade.getSecond();
        
        // City and population pair
        Pair<String, Integer> cityData = new Pair<>("New York", 8400000);
        System.out.println(cityData.getFirst() + " has " + 
                         cityData.getSecond() + " people");
    }
}
```

### 4. Simple Number Calculator
```java
// A calculator that works with any number type
public class Calculator<N extends Number> {
    private N value;
    
    public Calculator(N startValue) {
        this.value = startValue;
    }
    
    public double square() {
        return value.doubleValue() * value.doubleValue();
    }
    
    public boolean isPositive() {
        return value.doubleValue() > 0;
    }
    
    public N getValue() {
        return value;
    }
}

// Usage
public class CalculatorExample {
    public void demonstrate() {
        // With Integer
        Calculator<Integer> intCalc = new Calculator<>(5);
        double intSquare = intCalc.square();    // Returns 25.0
        boolean isIntPositive = intCalc.isPositive();  // Returns true
        
        // With Double
        Calculator<Double> doubleCalc = new Calculator<>(3.14);
        double doubleSquare = doubleCalc.square();  // Returns 9.8596
        boolean isDoublePositive = doubleCalc.isPositive();  // Returns true
    }
}
```

### 5. Simple Stack
```java
// A basic stack implementation
public class Stack<E> {
    private ArrayList<E> elements;
    
    public Stack() {
        elements = new ArrayList<>();
    }
    
    public void push(E element) {
        elements.add(element);
    }
    
    public E pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elements.remove(elements.size() - 1);
    }
    
    public boolean isEmpty() {
        return elements.isEmpty();
    }
    
    public int size() {
        return elements.size();
    }
}

// Usage
public class StackExample {
    public void demonstrate() {
        // Stack of Strings
        Stack<String> bookStack = new Stack<>();
        bookStack.push("Book 1");
        bookStack.push("Book 2");
        bookStack.push("Book 3");
        
        String topBook = bookStack.pop();  // Returns "Book 3"
        
        // Stack of Integers
        Stack<Integer> numberStack = new Stack<>();
        numberStack.push(10);
        numberStack.push(20);
        numberStack.push(30);
        
        int topNumber = numberStack.pop();  // Returns 30
    }
}
```

### 6. Shopping Cart Example (Practical Use Case)
```java
// Product class representing items that can be purchased
public class Product {
    private String name;
    private double price;
    
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
    
    public String getName() { return name; }
    public double getPrice() { return price; }
}

// Discount interface with generic type to handle different discount calculations
public interface Discount<T> {
    double calculateDiscount(T item);
}

// Specific discount implementation for products
public class PercentageDiscount implements Discount<Product> {
    private double percentage;
    
    public PercentageDiscount(double percentage) {
        this.percentage = percentage;
    }
    
    @Override
    public double calculateDiscount(Product item) {
        return item.getPrice() * (percentage / 100.0);
    }
}

// Generic shopping cart that can work with any type of item
public class ShoppingCart<T> {
    private List<T> items;
    private Discount<T> discount;
    
    public ShoppingCart(Discount<T> discount) {
        this.items = new ArrayList<>();
        this.discount = discount;
    }
    
    public void addItem(T item) {
        items.add(item);
    }
    
    public void removeItem(T item) {
        items.remove(item);
    }
    
    public List<T> getItems() {
        return new ArrayList<>(items);
    }
    
    public double calculateTotal() {
        double total = 0.0;
        for (T item : items) {
            if (item instanceof Product) {
                Product product = (Product) item;
                double itemPrice = product.getPrice();
                double itemDiscount = discount.calculateDiscount(item);
                total += (itemPrice - itemDiscount);
            }
        }
        return total;
    }
}

// Usage demonstration
public class ShoppingExample {
    public void demonstrateShoppingCart() {
        // Create a discount strategy - 10% off
        Discount<Product> tenPercentOff = new PercentageDiscount(10.0);
        
        // Create a shopping cart with the discount
        ShoppingCart<Product> cart = new ShoppingCart<>(tenPercentOff);
        
        // Add some products
        cart.addItem(new Product("Laptop", 999.99));
        cart.addItem(new Product("Mouse", 29.99));
        cart.addItem(new Product("Keyboard", 59.99));
        
        // Calculate total with discounts
        double total = cart.calculateTotal();
        System.out.println("Total after 10% discount: $" + String.format("%.2f", total));
        
        // View all items
        System.out.println("\nCart Contents:");
        for (Product item : cart.getItems()) {
            System.out.println(item.getName() + ": $" + item.getPrice());
        }
    }
}
```

This practical example demonstrates several key concepts of generics:

1. **Generic Classes**: The `ShoppingCart<T>` class can work with any type of item, though in this example we use it with `Product`.
2. **Generic Interfaces**: The `Discount<T>` interface shows how to create generic contracts.
3. **Type Safety**: The cart ensures type safety at compile time.
4. **Code Reusability**: The same cart implementation could be used with different types of items and discount strategies.
5. **Design Patterns**: Shows how generics can be used with the Strategy pattern (discount calculation).

When you run this code:
```java
public class Main {
    public static void main(String[] args) {
        ShoppingExample example = new ShoppingExample();
        example.demonstrateShoppingCart();
    }
}
```

The output would look something like:
```
Total after 10% discount: $981.97

Cart Contents:
Laptop: $999.99
Mouse: $29.99
Keyboard: $59.99
```

This example shows how generics can be used in a real-world scenario to create flexible, type-safe, and reusable code.






# Java Access Modifiers: Package-Private (Default) vs Private

This document explains the differences between Java packages, and two important access modifiers: package-private
(default) and private, using simple banking examples.

## 1. What is a Java Package?

A package in Java is a namespace that organizes a set of related classes and interfaces. For example:

```java
package com.bank;  // A simple bank package
```

Java uses packages to avoid class name conflicts and to control access with access modifiers.

## 2. Access Modifiers Overview

Java provides four levels of access control:

| Modifier | Class | Package | Subclass | World |
|----------|--------|----------|-----------|--------|
| public | Yes | Yes | Yes | Yes |
| (default) | Yes | Yes | No | No |
| protected | Yes | Yes | Yes | No |
| private | Yes | No | No | No |

## 3. Default (Package-Private) Access

- No modifier: If you don't specify any access modifier, it's considered package-private.
- Accessible only within the same package.
- Not accessible from classes in other packages, even if they're subclasses.

Example from Banking:
```java
// file: com/bank/Account.java
package com.bank;

class Calculator {  // default (package-private) access
    int calculateInterest(int amount) {
        return amount * 5 / 100;  // Simple 5% interest
    }
}

class Account {
    Calculator calc = new Calculator();
    int balance = 1000;
    
    void addInterest() {
        balance += calc.calculateInterest(balance);
    }
}

// file: com/otherbank/InterestService.java
package com.otherbank;

public class InterestService {
    public void processInterest() {
        Calculator calc = new Calculator();  // Error: Calculator is not visible
    }
}
```

## 4. Private Access

- Accessible only within the same class.
- Not accessible by any other class, even in the same package.

Example from Banking:
```java
public class BankAccount {
    private int balance;      // private field
    
    private void updateBalance(int amount) {  // private method
        balance += amount;
    }
    
    public void deposit(int amount) {
        if (amount > 0) {
            updateBalance(amount);  // Can call private method here
        }
    }
}

public class Bank {
    public void checkAccount(BankAccount account) {
        // These will cause errors:
        // int money = account.balance;         // Can't access private field
        // account.updateBalance(1000);         // Can't access private method
    }
}
```

## Summary Table

| Feature | Package-Private (default) | Private |
|---------|-------------------------|---------|
| Keyword | (none) | private |
| Accessible in same class | Yes | Yes |
| Accessible in same package | Yes | No |
| Accessible in other package | No | No |
| Accessible in subclass in other package | No | No |
| Typical usage | Helper classes like Calculator | Sensitive data like balance |

## Best Practices

### 1. Encapsulation
- Always make instance variables private
- Provide public getter/setter methods only when necessary
- Keep implementation details hidden

```java
// Good Practice
public class BankAccount {
    private double balance;  // encapsulated
    
    public double getBalance() {
        return balance;
    }
    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
}
```

### 2. Package Organization
- Group related classes in the same package
- Use package-private for classes that work together
- Keep packages small and focused
```java
// Good package structure
com.bank.account     // Account-related classes
com.bank.transaction // Transaction-related classes
com.bank.customer    // Customer-related classes
```

### 3. Access Level Selection
- Start with most restrictive access level (private)
- Increase access only when needed
- Use package-private for classes that work together within a module
- Document your access level decisions in comments

## Common Pitfalls

### 1. Public Fields
```java
// Bad Practice
public class Account {
    public double balance;  // Exposed implementation
}

// Good Practice
public class Account {
    private double balance;
    public double getBalance() { return balance; }
}
```

### 2. Excessive Public Methods
```java
// Bad Practice
public class BankAccount {
    public void updateInternalRecord() {}  // Should be private
    public void validateTransaction() {}   // Should be private
    public void deposit(double amount) {}  // OK to be public
}

// Good Practice
public class BankAccount {
    private void updateInternalRecord() {}
    private void validateTransaction() {}
    public void deposit(double amount) {}
}
```

### 3. Forgetting Package-Private is Default
```java
// Easy to forget this is package-private
class Helper {
    void helperMethod() {}  // Implicitly package-private
}

// Better - Explicit comment
/* package-private */ class Helper {
    /* package-private */ void helperMethod() {}
}
```

### 4. Package Sprawl
```java
// Bad Practice - Everything in one package
package com.bank;
class Account {}
class Transaction {}
class Customer {}
class Employee {}

// Good Practice - Organized packages
package com.bank.accounts;
class Account {}

package com.bank.transactions;
class Transaction {}
```

## Guidelines for Choosing Access Levels

1. **Use Private When**:
   - For fields that should not be directly accessed
   - For helper methods used only within the class
   - For implementation details that might change

2. **Use Package-Private When**:
   - Classes need to work together in the same package
   - Implementation details should be shared between related classes
   - Functionality should be hidden from external classes

3. **Consider These Questions**:
   - "Does this need to be accessed outside the class?" → If no, make it private
   - "Does this need to be accessed outside the package?" → If no, make it package-private
   - "Could this implementation change later?" → If yes, restrict access


# Advanced Java OOP Concepts

## 📑 Table of Contents

- [Overview](#overview)
- [1. Access Modifiers](#1-access-modifiers)
- [2. Static Members](#2-static-members)
- [3. Final Keyword](#3-final-keyword)
- [4. Package and Import](#4-package-and-import)
- [5. Inner Classes](#5-inner-classes)
- [Best Practices](#best-practices)
- [Common Pitfalls](#common-pitfalls)
- [Programming Exercises](#programming-exercises)

## Overview

Java provides several unique object-oriented features that enhance code organization, security, and maintainability. This guide covers Java-specific OOP concepts with practical examples from a banking domain.

## 1. Access Modifiers

### 📌 Overview
- `private`: Class-only access
- `default` (package-private): Package-level access
- `protected`: Package + Child classes access
- `public`: Global access

### 🧪 Code Example

```java
package com.bank.accounts;

public class BankAccount {
    // Private: Only accessible within this class
    private String accountNumber;
    
    // Default: Accessible within the same package
    String bankBranch;
    
    // Protected: Accessible in same package and subclasses
    protected double balance;
    
    // Public: Accessible everywhere
    public String accountType;

    // Implementation showing access levels
    public class AccountDetails {
        public void displayDetails() {
            System.out.println(accountNumber);  // Can access private
            System.out.println(bankBranch);     // Can access default
            System.out.println(balance);        // Can access protected
            System.out.println(accountType);    // Can access public
        }
    }
}

// In different package: com.bank.transactions
package com.bank.transactions;

public class TransactionProcessor extends BankAccount {
    public void processTransaction() {
        // System.out.println(accountNumber); // Cannot access private
        // System.out.println(bankBranch);    // Cannot access default
        System.out.println(balance);        // Can access protected
        System.out.println(accountType);    // Can access public
    }
}
```

## 2. Static Members

### 📌 Overview
- Static Fields (Class Variables)
- Static Methods (Class Methods)
- Static Initialization Blocks
- Static Import

### 🧪 Code Example

```java
public class BankingConstants {
    // Static Fields
    public static final double MINIMUM_BALANCE = 100.00;
    public static final double OVERDRAFT_FEE = 35.00;
    
    // Static counter for account numbers
    private static int accountCounter = 1000;
    
    // Static Initialization Block
    static {
        // Load configuration or initialize static resources
        loadBankingConfiguration();
    }
    
    // Static Method
    public static String generateAccountNumber(String accountType) {
        return accountType + "-" + (++accountCounter);
    }
    
    private static void loadBankingConfiguration() {
        // Load configuration logic
    }
}

// Usage with Static Import
import static com.bank.utils.BankingConstants.*;

public class Account {
    private double balance = MINIMUM_BALANCE; // Using static field
    private String accountNumber = generateAccountNumber("SAV"); // Using static method
}
```

## 3. Final Keyword

### 📌 Uses of Final
1. Final Classes (Cannot be inherited)
2. Final Methods (Cannot be overridden)
3. Final Variables (Cannot be modified)

### 🧪 Code Example

```java
// Final class - cannot be inherited
public final class SecurityUtils {
    // Final variable - cannot be modified
    private final String ENCRYPTION_KEY;
    
    public SecurityUtils(String key) {
        this.ENCRYPTION_KEY = key;
    }
    
    // Final method - cannot be overridden
    public final String encryptData(String data) {
        // Encryption logic
        return encryptedData;
    }
}

public class TransactionProcessor {
    // Final parameter
    public void processPayment(final double amount) {
        // amount = amount + fee; // Would cause compilation error
        
        // Final local variable
        final double transactionFee = calculateFee(amount);
        // transactionFee = 0; // Would cause compilation error
    }
}
```

## 4. Package and Import

### 📌 Overview
- Package Declaration
- Import Statements
- Static Imports
- Package Organization

### 🧪 Code Example

```java
// File: com/bank/accounts/SavingsAccount.java
package com.bank.accounts;

import java.time.LocalDateTime;
import java.util.List;
import static java.lang.Math.*;
import com.bank.transactions.Transaction;
import com.bank.utils.InterestCalculator;

public class SavingsAccount {
    private List<Transaction> transactions;
    private double interestRate;

    public double calculateInterest() {
        return round(InterestCalculator.compute(getBalance(), interestRate));
    }
}
```

## 5. Inner Classes

### 📌 Types of Inner Classes and When to Use Them

#### 1. Member Inner Classes

### Simple Examples of Inner Classes

```java
public class Outer {
    private int outerField = 1;  // Simple outer class field

    // Public inner class example
    public class PublicInner {
        private int innerField = 2;  // Inner class field

        public void accessOuter() {
            // Access outer class field
            System.out.println("Outer field: " + outerField);
            // Access inner class field
            System.out.println("Inner field: " + innerField);
        }
    }

    // Private inner class example
    private class PrivateInner {
        public void doSomething() {
            outerField = 3;  // Can modify outer class field
        }
    }

    // Method to work with private inner class
    public void usePrivateInner() {
        PrivateInner inner = new PrivateInner();
        inner.doSomething();
    }
}

// Usage examples
public class InnerClassDemo {
    public static void main(String[] args) {
        // 1. Create outer class instance
        Outer outer = new Outer();

        // 2. Create public inner class instance
        Outer.PublicInner publicInner = outer.new PublicInner();
        publicInner.accessOuter();

        // 3. Cannot create private inner class instance here
        // Outer.PrivateInner privateInner = outer.new PrivateInner(); // Won't compile
    }
}
```

### How Inner Classes Access Outer Class

```java
public class Outer {
    private int value = 10;

    public class Inner {
        private int value = 20;  // Same name as outer field

        public void printValues() {
            // Local variable with same name
            int value = 30;

            System.out.println("Local value: " + value);           // 30
            System.out.println("Inner value: " + this.value);      // 20
            System.out.println("Outer value: " + Outer.this.value); // 10
        }
    }
}
```

### Practical Example

```java
public class Calculator {
    private int baseValue;

    public Calculator(int baseValue) {
        this.baseValue = baseValue;
    }

    // Public inner class for addition
    public class Adder {
        public int add(int value) {
            return baseValue + value;  // Access outer class field
        }
    }

    // Private inner class for multiplication
    private class Multiplier {
        public int multiply(int value) {
            return baseValue * value;
        }
    }

    // Method using private inner class
    public int multiplyValue(int value) {
        Multiplier multiplier = new Multiplier();
        return multiplier.multiply(value);
    }

    // Method to get public inner class
    public Adder getAdder() {
        return new Adder();
    }
}

// Usage
public class Main {
    public static void main(String[] args) {
        Calculator calc = new Calculator(10);
        
        // Using public inner class
        Calculator.Adder adder = calc.new Adder();
        System.out.println(adder.add(5));  // 15
        
        // Using private inner class through public method
        System.out.println(calc.multiplyValue(5));  // 50
    }
}
```

### Key Points to Remember

1. **Basic Structure**
   ```java
   public class Outer {
       private int field;

       public class PublicInner {
           // Can access field
       }

       private class PrivateInner {
           // Can access field
       }
   }
   ```

2. **Instantiation**
   ```java
   Outer outer = new Outer();
   Outer.PublicInner inner = outer.new PublicInner();  // Public
   // Private inner class can only be instantiated inside Outer
   ```

3. **Access**
   ```java
   public class Outer {
       private int x = 1;
       
       class Inner {
           void access() {
               x = 2;  // Direct access
               Outer.this.x = 3;  // Explicit access
           }
       }
   }
   ```

#### 2. Static Nested Classes
**When to Use:**
- When the nested class doesn't need access to outer class instance members
- For grouping classes that are only used in one place
- To increase encapsulation
- For helper classes that are closely related to their outer class

**When Not to Use:**
- When the nested class needs access to non-static members of the outer class
- When the class could be useful as a top-level class

```java
public class PaymentProcessor {
    private String merchantId;

    // Good use of Static Nested Class
    public static class PaymentConfig {
        private static final String API_VERSION = "v1";
        private final String apiKey;
        private final String endpoint;

        public PaymentConfig(String apiKey, String endpoint) {
            this.apiKey = apiKey;
            this.endpoint = endpoint;
        }

        public static boolean validateApiKey(String key) {
            return key != null && key.startsWith("pk_");
        }
    }

    // Another Static Nested Class for constants
    public static class PaymentType {
        public static final String CREDIT = "CREDIT";
        public static final String DEBIT = "DEBIT";
        public static final String CRYPTO = "CRYPTO";
    }
}

// Usage
PaymentProcessor.PaymentConfig config = new PaymentProcessor.PaymentConfig("pk_123", "api.payment.com");
boolean isValid = PaymentProcessor.PaymentConfig.validateApiKey("pk_123");
```

#### 3. Local Classes
**When to Use:**
- When you need a class only within a single method
- When the class needs to access method's local variables
- For one-off implementations that are method-specific

**When Not to Use:**
- When the class needs to be used outside the method
- When the class implementation is reusable

```java
public class NotificationService {
    public void sendNotifications(List<String> recipients, String message) {
        // Local class - only used within this method
        class EmailValidator {
            private final String email;

            EmailValidator(String email) {
                this.email = email;
            }

            boolean isValid() {
                return email != null && 
                       email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
            }
        }

        for (String recipient : recipients) {
            EmailValidator validator = new EmailValidator(recipient);
            if (validator.isValid()) {
                // Send notification
                System.out.println("Sending to: " + recipient);
            }
        }
    }
}
```

#### 4. Anonymous Classes
**When to Use:**
- For one-time implementation of an interface or abstract class
- When you need a quick override of a few methods
- For event handlers or callbacks that are used only once

**When Not to Use:**
- When you need to reuse the implementation
- When you need to implement multiple methods
- When the implementation is complex or long
- When you need to reference the class type explicitly

```java
public class UIComponent {
    public interface ClickListener {
        void onClick(int x, int y);
        void onDoubleClick(int x, int y);
    }

    // Good use of Anonymous Class
    public void addSimpleClickListener() {
        addListener(new ClickListener() {
            @Override
            public void onClick(int x, int y) {
                System.out.println("Clicked at: " + x + "," + y);
            }

            @Override
            public void onDoubleClick(int x, int y) {
                System.out.println("Double clicked at: " + x + "," + y);
            }
        });
    }

    // Bad use of Anonymous Class (should be a named class instead)
    public void addComplexListener() {
        addListener(new ClickListener() {
            private int clickCount = 0;
            private long lastClickTime = 0;
            
            @Override
            public void onClick(int x, int y) {
                // Complex logic here...
                clickCount++;
                long currentTime = System.currentTimeMillis();
                // More complex logic...
            }

            @Override
            public void onDoubleClick(int x, int y) {
                // More complex logic...
            }
        });
    }
}
```

### 💡 Best Practices for Inner Classes

1. **Encapsulation**
   ```java
   // Good: Inner class encapsulates related functionality
   public class EmailService {
       private String smtpServer;
       
       private class EmailBuilder {
           private String content;
           private String subject;
           
           public EmailBuilder withContent(String content) {
               this.content = content;
               return this;
           }
       }
   }
   ```

2. **Access Level**
   ```java
   public class UserManager {
       // Good: Private inner class - implementation detail
       private class UserCache {
           // Implementation
       }
       
       // Good: Public static nested class - part of API
       public static class UserCredentials {
           // Implementation
       }
   }
   ```

3. **Naming Conventions**
   ```java
   public class Order {
       // Good: Clear relationship between classes
       public class OrderItem {
           // Implementation
       }
       
       // Bad: Unclear relationship
       public class Item {
           // Implementation
       }
   }
   ```

### ⚠️ Common Pitfalls

1. **Memory Leaks**
   ```java
   // Bad: Potential memory leak
   public class OuterClass {
       private List<InnerClass> instances = new ArrayList<>();
       
       public class InnerClass {
           // Each instance holds a reference to OuterClass
       }
   }

   // Good: Use static nested class if no need for outer reference
   public class OuterClass {
       private List<StaticNestedClass> instances = new ArrayList<>();
       
       public static class StaticNestedClass {
           // No implicit reference to OuterClass
       }
   }
   ```

2. **Overuse of Anonymous Classes**
   ```java
   // Bad: Complex anonymous class
   button.setOnClickListener(new OnClickListener() {
       private int clickCount;
       private long lastClickTime;
       
       @Override
       public void onClick() {
           // Complex logic with multiple methods and state
       }
   });

   // Good: Named class for complex implementation
   public class ButtonClickHandler implements OnClickListener {
       private int clickCount;
       private long lastClickTime;
       
       @Override
       public void onClick() {
           // Complex logic
       }
   }
   ```

## Best Practices

1. **Inner Classes**
   ```java
   // ✅ Good: Use inner class for related functionality
   public class BankAccount {
       public class Transaction {
           // Related to BankAccount
       }
   }

   // ❌ Bad: Unrelated inner class
   public class BankAccount {
       public class Logger {
           // Should be separate class
       }
   }
   ```

2. **Access Modifiers**
   ```java
   // ✅ Good: Proper encapsulation
   public class Account {
       private double balance;
       public double getBalance() {
           return balance;
       }
   }

   // ❌ Bad: Exposed internals
   public class Account {
       public double balance;
   }
   ```

3. **Static Usage**
   ```java
   // ✅ Good: Proper static usage
   public class MathUtils {
       public static double calculateInterest(double principal, double rate) {
           return principal * rate;
       }
   }

   // ❌ Bad: Instance method should be static
   public class MathUtils {
       public double calculateInterest(double principal, double rate) {
           return principal * rate;
       }
   }
   ```

## Common Pitfalls

1. **Inner Class Memory Leaks**
   ```java
   // ❌ Bad: Potential memory leak
   public class BankAccount {
       private List<Transaction> transactions = new ArrayList<>();
       
       public Transaction createTransaction() {
           return new Transaction(); // Holds reference to outer instance
       }
   }

   // ✅ Good: Use static nested class if no need for outer instance
   public class BankAccount {
       private List<Transaction> transactions = new ArrayList<>();
       
       public static class Transaction {
           // No reference to outer instance
       }
   }
   ```

2. **Static Abuse**
   ```java
   // ❌ Bad: Abuse of static
   public class AccountManager {
       private static double totalBalance;
       private static List<Account> accounts;
       
       public static void addAccount(Account account) {
           accounts.add(account);
       }
   }

   // ✅ Good: Instance-based management
   public class AccountManager {
       private double totalBalance;
       private List<Account> accounts;
       
       public void addAccount(Account account) {
           accounts.add(account);
       }
   }
   ```


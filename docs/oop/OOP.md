# Object-Oriented Programming in Java: Deep Dive

## 📑 Table of Contents

- [Overview](#overview)
- [Key OOP Principles](#key-oop-principles)
- [1. Encapsulation](#1-encapsulation)
- [2. Inheritance](#2-inheritance)
- [3. Polymorphism](#3-polymorphism)
- [4. Abstraction](#4-abstraction)
- [Best Practices](#best-practices)
- [Common Pitfalls](#common-pitfalls)
- [Programming Exercises](#programming-exercises)

## Overview

Object-Oriented Programming (OOP) is a programming paradigm that organizes code into objects, which are instances of classes containing both data and code. Java is a pure object-oriented programming language that implements all core OOP concepts.

## Key OOP Principles

| Principle     | Description                                    | Key Features                              | Real-World Analogy        |
|--------------|------------------------------------------------|-------------------------------------------|--------------------------|
| Encapsulation | Bundling data and methods that operate on that data | Private fields, getter/setter methods     | Bank vault with controlled access    |
| Inheritance   | Creating new classes based on existing ones    | Extends keyword, super/sub classes         | Different types of bank accounts |
| Polymorphism  | Objects taking multiple forms                  | Method overriding, interface implementation| ATM handling different card types |
| Abstraction   | Hiding complex implementation details          | Abstract classes, interfaces               | Banking app interface     |

## 1. Encapsulation

### 📌 Overview
- Bundles related data and methods together
- Controls access to internal details
- Provides data security and maintainability

### 🧪 Code Example

```java
public class BankAccount {
    // Private fields (data hiding)
    private double balance;
    private String accountNumber;
    private String accountHolder;

    // Constructor
    public BankAccount(String accountHolder, String accountNumber) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.balance = 0.0;
    }

    // Public methods (controlled access)
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
            return true;
        }
        return false;
    }

    // Getter methods
    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
```

## 2. Inheritance

### 📌 Overview
- Allows creation of new classes based on existing ones
- Promotes code reuse and establishes relationships between classes
- Supports method overriding and extension

### 🧪 Code Example

```java
// Base class
public abstract class BankAccount {
    protected String accountNumber;
    protected String accountHolder;
    protected double balance;
    protected double interestRate;

    public BankAccount(String accountHolder, String accountNumber, double interestRate) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.interestRate = interestRate;
        this.balance = 0.0;
    }

    public abstract void calculateInterest();
    
    public void displayInfo() {
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Current Balance: $" + balance);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
            return true;
        }
        return false;
    }
}

// Derived class
public class SavingsAccount extends BankAccount {
    private double minimumBalance;
    private int withdrawalLimit;
    private int withdrawalsThisMonth;

    public SavingsAccount(String accountHolder, String accountNumber, 
                         double interestRate, double minimumBalance) {
        super(accountHolder, accountNumber, interestRate);
        this.minimumBalance = minimumBalance;
        this.withdrawalLimit = 3;
        this.withdrawalsThisMonth = 0;
    }

    @Override
    public void calculateInterest() {
        double interest = balance * (interestRate / 100) / 12;
        balance += interest;
        System.out.println("Monthly interest added: $" + interest);
    }

    @Override
    public boolean withdraw(double amount) {
        if (withdrawalsThisMonth >= withdrawalLimit) {
            System.out.println("Monthly withdrawal limit exceeded!");
            return false;
        }
        if (balance - amount < minimumBalance) {
            System.out.println("Withdrawal would breach minimum balance!");
            return false;
        }
        boolean success = super.withdraw(amount);
        if (success) withdrawalsThisMonth++;
        return success;
    }
}

// Another derived class
public class CheckingAccount extends BankAccount {
    private double overdraftLimit;
    private double overdraftFee;

    public CheckingAccount(String accountHolder, String accountNumber, 
                         double interestRate, double overdraftLimit) {
        super(accountHolder, accountNumber, interestRate);
        this.overdraftLimit = overdraftLimit;
        this.overdraftFee = 35.0;
    }

    @Override
    public void calculateInterest() {
        if (balance > 1000) {
            double interest = balance * (interestRate / 100) / 12;
            balance += interest;
            System.out.println("Monthly interest added: $" + interest);
        }
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= balance + overdraftLimit) {
            if (amount > balance) {
                balance -= (amount + overdraftFee);
                System.out.println("Overdraft fee charged: $" + overdraftFee);
            } else {
                balance -= amount;
            }
            System.out.println("Withdrawn: $" + amount);
            return true;
        }
        System.out.println("Amount exceeds overdraft limit!");
        return false;
    }
}
```

## 3. Polymorphism

### 📌 Overview
- Allows objects to take multiple forms
- Includes method overriding and method overloading
- Enables flexible and reusable code

### 🧪 Code Example

```java
// Interface defining banking transactions
public interface BankingTransaction {
    boolean execute();
    void rollback();
    String getTransactionDetails();
}

// Deposit transaction implementation
public class DepositTransaction implements BankingTransaction {
    private BankAccount account;
    private double amount;
    private boolean executed;

    public DepositTransaction(BankAccount account, double amount) {
        this.account = account;
        this.amount = amount;
        this.executed = false;
    }

    @Override
    public boolean execute() {
        if (!executed && amount > 0) {
            account.deposit(amount);
            executed = true;
            return true;
        }
        return false;
    }

    @Override
    public void rollback() {
        if (executed) {
            account.withdraw(amount);
            executed = false;
        }
    }

    @Override
    public String getTransactionDetails() {
        return "Deposit Transaction - Amount: $" + amount;
    }
}

// Transfer transaction implementation
public class TransferTransaction implements BankingTransaction {
    private BankAccount fromAccount;
    private BankAccount toAccount;
    private double amount;
    private boolean executed;

    public TransferTransaction(BankAccount fromAccount, BankAccount toAccount, double amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.executed = false;
    }

    @Override
    public boolean execute() {
        if (!executed && fromAccount.withdraw(amount)) {
            toAccount.deposit(amount);
            executed = true;
            return true;
        }
        return false;
    }

    @Override
    public void rollback() {
        if (executed) {
            toAccount.withdraw(amount);
            fromAccount.deposit(amount);
            executed = false;
        }
    }

    @Override
    public String getTransactionDetails() {
        return "Transfer Transaction - Amount: $" + amount;
    }
}

// Usage example demonstrating polymorphism
public class TransactionProcessor {
    public static void main(String[] args) {
        List<BankingTransaction> transactions = new ArrayList<>();
        
        BankAccount savingsAcc = new SavingsAccount("John Doe", "SAV001", 2.5, 100);
        BankAccount checkingAcc = new CheckingAccount("John Doe", "CHK001", 1.0, 500);

        transactions.add(new DepositTransaction(savingsAcc, 1000));
        transactions.add(new TransferTransaction(savingsAcc, checkingAcc, 500));

        // Process all transactions polymorphically
        for (BankingTransaction transaction : transactions) {
            System.out.println("Processing: " + transaction.getTransactionDetails());
            if (transaction.execute()) {
                System.out.println("Transaction successful!");
            } else {
                System.out.println("Transaction failed!");
                transaction.rollback();
            }
        }
    }
}
```

## 4. Abstraction

### 📌 Overview
- Hides complex implementation details
- Focuses on what an object does rather than how it does it
- Implemented using abstract classes and interfaces

### 🧪 Code Example

```java
// Abstract class representing a banking service
public abstract class BankingService {
    protected String serviceId;
    protected BankAccount account;

    public BankingService(String serviceId, BankAccount account) {
        this.serviceId = serviceId;
        this.account = account;
    }

    // Abstract methods - must be implemented by concrete classes
    public abstract boolean processService();
    public abstract double getServiceCharge();

    // Concrete method shared by all services
    public void displayServiceInfo() {
        System.out.println("Service ID: " + serviceId);
        System.out.println("Service Charge: $" + getServiceCharge());
    }
}

// Concrete implementation for wire transfer service
public class WireTransferService extends BankingService {
    private String beneficiaryAccount;
    private String beneficiaryBank;
    private double transferAmount;

    public WireTransferService(String serviceId, BankAccount account,
                             String beneficiaryAccount, String beneficiaryBank,
                             double transferAmount) {
        super(serviceId, account);
        this.beneficiaryAccount = beneficiaryAccount;
        this.beneficiaryBank = beneficiaryBank;
        this.transferAmount = transferAmount;
    }

    @Override
    public boolean processService() {
        double totalAmount = transferAmount + getServiceCharge();
        if (account.withdraw(totalAmount)) {
            System.out.println("Wire transfer processed to " + beneficiaryBank);
            return true;
        }
        return false;
    }

    @Override
    public double getServiceCharge() {
        return 25.0; // Fixed wire transfer fee
    }
}

// Concrete implementation for check book request
public class CheckBookRequest extends BankingService {
    private int numberOfChecks;
    private boolean isUrgent;

    public CheckBookRequest(String serviceId, BankAccount account,
                          int numberOfChecks, boolean isUrgent) {
        super(serviceId, account);
        this.numberOfChecks = numberOfChecks;
        this.isUrgent = isUrgent;
    }

    @Override
    public boolean processService() {
        if (account.withdraw(getServiceCharge())) {
            System.out.println("Checkbook request processed for " + numberOfChecks + " checks");
            return true;
        }
        return false;
    }

    @Override
    public double getServiceCharge() {
        double baseCharge = numberOfChecks * 0.25;
        return isUrgent ? baseCharge + 15.0 : baseCharge;
    }
}
```

## Best Practices

1. **Class Design**
   - Keep classes focused and single-responsibility
   - Use meaningful names for classes and methods
   - Follow encapsulation principles consistently

2. **Inheritance Usage**
   - Favor composition over inheritance when possible
   - Use inheritance only for "is-a" relationships
   - Keep inheritance hierarchies shallow

3. **Interface Design**
   - Keep interfaces focused and cohesive
   - Use interface segregation principle
   - Design for extension

## Common Pitfalls

1. **Inheritance Misuse**
   ```java
   // ❌ Bad: Forcing inheritance where composition is better
   public class ElectricCar extends Battery { } // Wrong!

   // ✅ Good: Using composition
   public class ElectricCar {
       private Battery battery;
   }
   ```

2. **Poor Encapsulation**
   ```java
   // ❌ Bad: Public fields
   public class User {
       public String password; // Exposed sensitive data
   }

   // ✅ Good: Proper encapsulation
   public class User {
       private String password;
       public void setPassword(String password) {
           // Validate and encrypt password
       }
   }
   ```

3. **Overcomplicating Polymorphism**
   ```java
   // ❌ Bad: Unnecessary type checking
   if (shape instanceof Circle) {
       ((Circle) shape).calculateArea();
   }

   // ✅ Good: Using polymorphism properly
   shape.calculateArea(); // Let polymorphism do its job
   ```

## Programming Exercises

1. **Advanced Banking System**
   - Implement different types of accounts (Savings, Checking, Fixed Deposit)
   - Add support for interest calculation and account statements
   - Implement transaction history and account summary
   - Create service charges and fee calculation system

2. **ATM Simulator**
   - Create different types of ATM transactions (Withdrawal, Deposit, Balance Check)
   - Implement card validation and PIN verification
   - Add support for multiple banks and card types
   - Include receipt generation functionality

3. **Online Banking Portal**
   - Implement user authentication and authorization
   - Create dashboard for account overview
   - Add bill payment and scheduled transfers
   - Implement transaction limits and security features 
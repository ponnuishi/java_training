# Java JDBC Tutorial

## JDBC Architecture and Driver Overview

JDBC (Java Database Connectivity) is a Java API that enables Java applications to interact with databases. 
The JDBC architecture consists of several key components:

1. **JDBC API**: The interface that Java applications use to interact with databases
2. **JDBC Driver Manager**: Manages database drivers and handles connections
3. **JDBC Drivers**: Database-specific implementations that handle the actual communication

### Types of JDBC Drivers:
1. **Type 1**: JDBC-ODBC Bridge driver (legacy, deprecated)
2. **Type 2**: Native-API/partly Java driver
3. **Type 3**: Network-protocol/all-Java driver
4. **Type 4**: Native-protocol/all-Java driver (most common, what we'll use)

## Setting Up JDBC Driver

### Step 1: Download PostgreSQL JDBC Driver
Download the PostgreSQL JDBC driver JAR file from:
- Official: https://jdbc.postgresql.org/download/
- Or use Maven: https://mvnrepository.com/artifact/org.postgresql/postgresql/42.7.7

### Step 2: Add Driver to Your Project
Create a `lib` folder in your project root and place the JAR file there:

```
project-root/
├── lib/
│   └── postgresql-42.6.0.jar
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── example/
└── README.md
```

### Step 3: Compile and Run with Classpath
When compiling and running your Java application, include the JAR file in the classpath:

```bash
# Compile
javac -cp "lib/*" src/main/java/com/example/*.java

# Run
java -cp "lib/*:src/main/java" com.example.Main
```

## Simple Example: Reading Data from a Table

Let's start with the simplest possible example - creating a table and reading data from it.

### Step 1: Create the Database Table

First, connect to your PostgreSQL database and create a simple table:

```sql
-- Connect to your database
-- psql -U your_username -d your_database

-- Create a simple table
CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(50),
    salary DECIMAL(10,2)
);

-- Insert some sample data
INSERT INTO employees (name, department, salary) VALUES 
    ('Arjun', 'Engineering', 75000.00),
    ('Lakshmi', 'Marketing', 65000.00),
    ('Karthik', 'Engineering', 80000.00),
    ('Anjali', 'HR', 60000.00),
    ('Suresh', 'Marketing', 70000.00);
```

### Step 2: Simple Java Program to Read Data

Create a simple Java program that connects to the database and reads all employees:

```java
import java.sql.*;

public class SimpleJdbcExample {
    // Database connection details
    private static final String URL = "jdbc:postgresql://localhost:5432/your_database";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";
    
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            // Step 1: Register the PostgreSQL driver
            Class.forName("org.postgresql.Driver");
            
            // Step 2: Create a connection
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected successfully!");
            
            // Step 3: Create a statement
            statement = connection.createStatement();
            
            // Step 4: Execute a query
            String sql = "SELECT id, name, department, salary FROM employees";
            resultSet = statement.executeQuery(sql);
            
            // Step 5: Process the results
            System.out.println("\nEmployee List:");
            System.out.println("ID | Name           | Department   | Salary");
            System.out.println("---+----------------+--------------+--------");
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String department = resultSet.getString("department");
                double salary = resultSet.getDouble("salary");
                
                System.out.printf("%-2d | %-14s | %-12s | $%.2f%n", 
                    id, name, department, salary);
            }
            
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } finally {
            // Step 6: Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
                System.out.println("\nDatabase connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
```

### Step 3: Run the Program

```bash
# Compile
javac -cp "lib/*" src/main/java/com/example/SimpleJdbcExample.java

# Run
java -cp "lib/*:src/main/java" com.example.SimpleJdbcExample
```

### Expected Output:
```
Connecting to database...
Connected successfully!

Employee List:
ID | Name           | Department   | Salary
---+----------------+--------------+--------
1  | Arjun Reddy    | Engineering  | $75000.00
2  | Lakshmi Devi    | Marketing    | $65000.00
3  | Karthik Iyer    | Engineering  | $80000.00
4  | Anjali Nair     | HR           | $60000.00
5  | Suresh Menon    | Marketing    | $70000.00

Database connection closed.
```

## Enhanced Example: Using Prepared Statements

Now let's create a more advanced example that uses prepared statements and allows searching:

```java
import java.sql.*;

public class EmployeeSearchExample {
    private static final String URL = "jdbc:postgresql://localhost:5432/your_database";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";
    
    public static void main(String[] args) {
        // Search for employees in Engineering department
        searchEmployeesByDepartment("Engineering");
        
        // Search for employees with salary above 70000
        searchEmployeesBySalary(70000.0);
    }
    
    public static void searchEmployeesByDepartment(String department) {
        String sql = "SELECT id, name, department, salary FROM employees WHERE department = ?";
        
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            // Set the parameter
            preparedStatement.setString(1, department);
            
            // Execute query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("\nEmployees in " + department + " department:");
                System.out.println("ID | Name           | Department   | Salary");
                System.out.println("---+----------------+--------------+--------");
                
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String dept = resultSet.getString("department");
                    double salary = resultSet.getDouble("salary");
                    
                    System.out.printf("%-2d | %-14s | %-12s | $%.2f%n", 
                        id, name, dept, salary);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
    
    public static void searchEmployeesBySalary(double minSalary) {
        String sql = "SELECT id, name, department, salary FROM employees WHERE salary > ? ORDER BY salary DESC";
        
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            // Set the parameter
            preparedStatement.setDouble(1, minSalary);
            
            // Execute query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("\nEmployees with salary above $" + minSalary + ":");
                System.out.println("ID | Name           | Department   | Salary");
                System.out.println("---+----------------+--------------+--------");
                
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String department = resultSet.getString("department");
                    double salary = resultSet.getDouble("salary");
                    
                    System.out.printf("%-2d | %-14s | %-12s | $%.2f%n", 
                        id, name, department, salary);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}
```

## What is a PreparedStatement?

A `PreparedStatement` is an interface that represents a pre-compiled SQL statement. Unlike a regular `Statement`, a `PreparedStatement` allows you to create SQL statements with placeholders (represented by `?`) that can be filled in with values later.

### Key Features of PreparedStatement:

1. **Pre-compiled SQL**: The SQL statement is compiled once and can be executed multiple times with different parameters
2. **Parameter Placeholders**: Uses `?` as placeholders for values that will be set later
3. **Type Safety**: Provides type-safe methods to set parameters (`setString()`, `setInt()`, `setDouble()`, etc.)
4. **Performance**: Better performance for repeated executions of the same query with different parameters

### When to Use PreparedStatement:

- **Dynamic Queries**: When you need to execute the same query with different values
- **User Input**: When accepting input from users (prevents SQL injection)
- **Security**: When you want to protect against SQL injection attacks

### Comparison: Statement vs PreparedStatement

| Feature | Statement | PreparedStatement |
|---------|-----------|-------------------|
| **SQL Construction** | SQL is built as a string | SQL uses placeholders (`?`) |
| **Parameter Setting** | Values are concatenated into SQL string | Values are set using `setXxx()` methods |
| **Security** | Vulnerable to SQL injection | Protected against SQL injection |
| **Performance** | SQL is parsed each time | SQL is pre-compiled |
| **Use Case** | Simple, static queries | Dynamic queries with parameters |

### Example: Statement vs PreparedStatement

**Using Statement (vulnerable to SQL injection):**
```java
// DANGEROUS - vulnerable to SQL injection
String department = userInput; // Could be "Engineering'; DROP TABLE employees; --"
String sql = "SELECT * FROM employees WHERE department = '" + department + "'";
Statement stmt = connection.createStatement();
ResultSet rs = stmt.executeQuery(sql);
```

**Using PreparedStatement (secure):**
```java
// SAFE - protected against SQL injection
String department = userInput; // Same malicious input
String sql = "SELECT * FROM employees WHERE department = ?";
PreparedStatement pstmt = connection.prepareStatement(sql);
pstmt.setString(1, department); // Parameter is safely escaped
ResultSet rs = pstmt.executeQuery();
```

### Parameter Setting Methods:

PreparedStatement provides type-safe methods to set parameters:

```java
PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM employees WHERE id = ? AND salary > ?");

// Set parameters by position (1-based indexing)
pstmt.setInt(1, 5);           // Set first parameter as int
pstmt.setDouble(2, 50000.0);  // Set second parameter as double

// Other common setter methods:
pstmt.setString(1, "value");     // String
pstmt.setBoolean(1, true);       // Boolean
pstmt.setDate(1, date);          // Date
pstmt.setTimestamp(1, timestamp); // Timestamp
pstmt.setNull(1, Types.VARCHAR); // NULL value
```

### Benefits of PreparedStatement:

1. **Security**: Prevents SQL injection attacks by properly escaping user input
2. **Performance**: SQL is compiled once and reused, reducing parsing overhead
3. **Type Safety**: Compile-time checking of parameter types
4. **Maintainability**: Easier to read and maintain parameterized queries
5. **Database Independence**: Works consistently across different database systems


Now let's create a more advanced example that uses prepared statements and allows searching:

```java
import java.sql.*;

public class EmployeeSearchExample {
    private static final String URL = "jdbc:postgresql://localhost:5432/your_database";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";
    
    public static void main(String[] args) {
        // Search for employees in Engineering department
        searchEmployeesByDepartment("Engineering");
        
        // Search for employees with salary above 70000
        searchEmployeesBySalary(70000.0);
    }
    
    public static void searchEmployeesByDepartment(String department) {
        String sql = "SELECT id, name, department, salary FROM employees WHERE department = ?";
        
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            // Set the parameter
            preparedStatement.setString(1, department);
            
            // Execute query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("\nEmployees in " + department + " department:");
                System.out.println("ID | Name           | Department   | Salary");
                System.out.println("---+----------------+--------------+--------");
                
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String dept = resultSet.getString("department");
                    double salary = resultSet.getDouble("salary");
                    
                    System.out.printf("%-2d | %-14s | %-12s | $%.2f%n", 
                        id, name, dept, salary);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
    
    public static void searchEmployeesBySalary(double minSalary) {
        String sql = "SELECT id, name, department, salary FROM employees WHERE salary > ? ORDER BY salary DESC";
        
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            // Set the parameter
            preparedStatement.setDouble(1, minSalary);
            
            // Execute query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("\nEmployees with salary above $" + minSalary + ":");
                System.out.println("ID | Name           | Department   | Salary");
                System.out.println("---+----------------+--------------+--------");
                
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String department = resultSet.getString("department");
                    double salary = resultSet.getDouble("salary");
                    
                    System.out.printf("%-2d | %-14s | %-12s | $%.2f%n", 
                        id, name, department, salary);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}
```

## Another Simple Example: Using Statement for Basic Queries

Here's another simple example using Statement for basic queries without parameters:

```java
import java.sql.*;

public class BasicStatementExample {
    private static final String URL = "jdbc:postgresql://localhost:5432/your_database";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";
    
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            // Register the PostgreSQL driver
            Class.forName("org.postgresql.Driver");
            
            // Create a connection
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected successfully!");
            
            // Create a statement
            statement = connection.createStatement();
            
            // Example 1: Get all employees
            System.out.println("\n=== All Employees ===");
            resultSet = statement.executeQuery("SELECT * FROM employees");
            printEmployees(resultSet);
            
            // Example 2: Get employees from Engineering department
            System.out.println("\n=== Engineering Employees ===");
            resultSet = statement.executeQuery("SELECT * FROM employees WHERE department = 'Engineering'");
            printEmployees(resultSet);
            
            // Example 3: Get employees with salary > 70000
            System.out.println("\n=== High Salary Employees (>$70000) ===");
            resultSet = statement.executeQuery("SELECT * FROM employees WHERE salary > 70000 ORDER BY salary DESC");
            printEmployees(resultSet);
            
            // Example 4: Count employees by department
            System.out.println("\n=== Employee Count by Department ===");
            resultSet = statement.executeQuery("SELECT department, COUNT(*) as count FROM employees GROUP BY department");
            while (resultSet.next()) {
                String department = resultSet.getString("department");
                int count = resultSet.getInt("count");
                System.out.println(department + ": " + count + " employees");
            }
            
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
                System.out.println("\nDatabase connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    private static void printEmployees(ResultSet resultSet) throws SQLException {
        System.out.println("ID | Name           | Department   | Salary");
        System.out.println("---+----------------+--------------+--------");
        
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String department = resultSet.getString("department");
            double salary = resultSet.getDouble("salary");
            
            System.out.printf("%-2d | %-14s | %-12s | $%.2f%n", 
                id, name, department, salary);
        }
    }
}
```

## Basic Connection Example (Without Connection Pool)

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/your_database";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";
    
    public static Connection getConnection() throws SQLException {
        try {
            // Register PostgreSQL driver
            Class.forName("org.postgresql.Driver");
            
            // Create connection
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found.", e);
        }
    }
    
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
```

## CRUD Operations Using JDBC (Basic Version)

Let's create a complete example using a `User` entity:

```java
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    
    // Constructor
    public User(Long id, String username, String email, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', email='" + email + "', createdAt=" + createdAt + "}";
    }
}

public class UserDAO {
    
    // CREATE
    public User createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, email, created_at) VALUES (?, ?, ?) RETURNING id";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setTimestamp(3, Timestamp.valueOf(user.getCreatedAt()));
            
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user.setId(rs.getLong("id"));
                return user;
            }
        } finally {
            // Close resources in reverse order
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) DatabaseConnection.closeConnection(conn);
        }
        
        throw new SQLException("Creating user failed, no ID obtained.");
    }
    
    // READ
    public User getUserById(Long id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getTimestamp("created_at").toLocalDateTime()
                );
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) DatabaseConnection.closeConnection(conn);
        }
        return null;
    }
    
    // UPDATE
    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, email = ? WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setLong(3, user.getId());
            
            return pstmt.executeUpdate() > 0;
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) DatabaseConnection.closeConnection(conn);
        }
    }
    
    // DELETE
    public boolean deleteUser(Long id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            
            return pstmt.executeUpdate() > 0;
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) DatabaseConnection.closeConnection(conn);
        }
    }
    
    // LIST ALL
    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM users ORDER BY created_at DESC";
        List<User> users = new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                users.add(new User(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) DatabaseConnection.closeConnection(conn);
        }
        return users;
    }
}
```

## Main Application Example

```java
public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        
        try {
            // Create a new user
            User newUser = new User(null, "john_doe", "john@example.com", LocalDateTime.now());
            User createdUser = userDAO.createUser(newUser);
            System.out.println("Created user: " + createdUser);
            
            // Get user by ID
            User retrievedUser = userDAO.getUserById(createdUser.getId());
            System.out.println("Retrieved user: " + retrievedUser);
            
            // Update user
            retrievedUser.setEmail("john.updated@example.com");
            boolean updated = userDAO.updateUser(retrievedUser);
            System.out.println("User updated: " + updated);
            
            // Get all users
            List<User> allUsers = userDAO.getAllUsers();
            System.out.println("All users:");
            for (User user : allUsers) {
                System.out.println("  " + user);
            }
            
            // Delete user
            boolean deleted = userDAO.deleteUser(createdUser.getId());
            System.out.println("User deleted: " + deleted);
            
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

## Database Schema

Create the users table in PostgreSQL:

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Optional: Create an index for better performance
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
```



Example with complex queries:

```java
public class AdvancedUserDAO {
    
    // Search users with pagination and sorting
    public List<User> searchUsers(String searchTerm, int page, int pageSize, String sortBy) 
            throws SQLException {
        String sql = """
            SELECT * FROM users 
            WHERE username LIKE ? OR email LIKE ?
            ORDER BY CASE 
                WHEN ? = 'username' THEN username
                WHEN ? = 'email' THEN email
                ELSE created_at
            END
            LIMIT ? OFFSET ?
            """;
            
        List<User> users = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            
            String searchPattern = "%" + searchTerm + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, sortBy);
            pstmt.setString(4, sortBy);
            pstmt.setInt(5, pageSize);
            pstmt.setInt(6, (page - 1) * pageSize);
            
            rs = pstmt.executeQuery();
            while (rs.next()) {
                users.add(new User(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) DatabaseConnection.closeConnection(conn);
        }
        return users;
    }
}
```

## Error Handling and Transaction Management

### Why Do We Need Transaction Management?

Transaction management is crucial when you need to perform multiple database operations that must succeed or fail together as a single unit. Without proper transaction management, you risk data inconsistency and partial updates.

### Comparison: Without Transaction vs With Transaction

| Aspect | Without Transaction | With Transaction |
|--------|-------------------|------------------|
| **Commit Behavior** | Each operation commits instantly | You control when to commit |
| **Error Recovery** | Hard to roll back on errors | Easy to roll back if something fails |
| **Data Consistency** | Risk of partial updates | Guarantees atomicity and consistency |

### Transaction Management Example

```java
public class UserService {
    private final UserDAO userDAO;
    
    public UserService() {
        this.userDAO = new UserDAO();
    }
    
    public void transferUserData(Long sourceUserId, Long targetUserId) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);  // Start transaction
            
            // Perform multiple operations
            User sourceUser = userDAO.getUserById(sourceUserId);
            User targetUser = userDAO.getUserById(targetUserId);
            
            if (sourceUser == null || targetUser == null) {
                throw new SQLException("One or both users not found");
            }
            
            // Perform complex business logic
            targetUser.setEmail(sourceUser.getEmail());
            userDAO.updateUser(targetUser);
            userDAO.deleteUser(sourceUserId);
            
            conn.commit();  // Commit transaction
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();  // Rollback on error
                } catch (SQLException ex) {
                    throw new SQLException("Error rolling back transaction", ex);
                }
            }
            throw new SQLException("Transaction failed", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);  // Reset auto-commit
                    DatabaseConnection.closeConnection(conn);
                } catch (SQLException e) {
                    System.err.println("Error resetting auto-commit: " + e.getMessage());
                }
            }
        }
    }
}
```

### Custom Exception Handling

```java
public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
    
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class UserRepository {
    private final UserDAO userDAO;
    
    public UserRepository() {
        this.userDAO = new UserDAO();
    }
    
    public User findUserByIdSafe(Long id) {
        try {
            User user = userDAO.getUserById(id);
            if (user == null) {
                throw new DatabaseException("User not found with ID: " + id);
            }
            return user;
        } catch (SQLException e) {
            throw new DatabaseException("Database error while fetching user", e);
        }
    }
    
    public void createUserSafe(User user) {
        try {
            validateUser(user);
            userDAO.createUser(user);
        } catch (SQLException e) {
            throw new DatabaseException("Failed to create user", e);
        }
    }
    
    private void validateUser(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }
}
```

## Connection Pooling (Advanced Section)

For production applications, you should use connection pooling. Here's how to add HikariCP:

### Step 1: Download HikariCP JAR
Download these JAR files and add them to your `lib` folder:
- `HikariCP-5.1.0.jar`
- `slf4j-api-2.0.9.jar`
- `logback-classic-1.4.11.jar`

### Step 2: Updated Classpath
```bash
# Compile with all dependencies
javac -cp "lib/*" src/main/java/com/example/*.java

# Run with all dependencies
java -cp "lib/*:src/main/java" com.example.Main
```

### Step 3: Connection Pool Implementation

```java
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public class DatabaseConnectionPool {
    private static DataSource dataSource;
    
    public static DataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:postgresql://localhost:5432/your_database");
            config.setUsername("your_username");
            config.setPassword("your_password");
            config.setMaximumPoolSize(10);
            config.setAutoCommit(false);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }
    
    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }
}
```

## Best Practices

1. **Always use prepared statements** instead of raw SQL queries
2. **Properly manage resources** - always close connections, statements, and result sets
3. **Implement proper transaction management**
4. **Handle exceptions appropriately**
5. **Validate input** before executing queries
6. **Use connection pooling** in production applications
7. **Use appropriate isolation levels** for transactions
8. **Implement proper logging**
9. **Use batch operations** for bulk updates
10. **Monitor database performance**

### Example Configuration Properties
```properties
# database.properties
db.url=jdbc:postgresql://localhost:5432/mydb
db.username=myuser
db.password=mypassword
db.pool.initialSize=5
db.pool.maxSize=20
db.pool.connectionTimeout=30000
db.pool.idleTimeout=600000
db.pool.maxLifetime=1800000
```
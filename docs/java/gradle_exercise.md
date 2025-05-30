# Gradle PostgreSQL Database Exercise

## Exercise: Employee Management System with PostgreSQL

### Objective
Create a Gradle project that connects to a PostgreSQL database, creates tables, inserts data, and retrieves employee information.

### Prerequisites
- PostgreSQL database server installed and running
- Database created (e.g., `employee_db`)
- Database user with appropriate permissions

### Requirements

#### 1. Project Setup
- Create a new Gradle project with the following structure:
```
employee-management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── company/
│   │   │           ├── Main.java
│   │   │           ├── model/
│   │   │           │   └── Employee.java
│   │   │           ├── dao/
│   │   │           │   └── EmployeeDAO.java
│   │   │           └── util/
│   │   │               └── DatabaseConnection.java
│   │   └── resources/
│   │       └── database.properties
│   └── test/
│       └── java/
│           └── com/
│               └── company/
│                   └── EmployeeDAOTest.java
├── build.gradle
└── settings.gradle
```

#### 2. Dependencies
Configure your `build.gradle` to include:
- PostgreSQL JDBC driver
- JUnit for testing
- Logging framework (SLF4J + Logback)

#### 3. Database Schema
Create the following PostgreSQL table:
```sql
CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    department VARCHAR(50) NOT NULL,
    salary DECIMAL(10,2) NOT NULL
);
```

#### 4. Java Classes to Implement

**Employee.java (Model)**
- Create a POJO with fields: id, firstName, lastName, email, department, salary
- Include constructors, getters, setters, toString(), equals(), and hashCode()

**DatabaseConnection.java (Utility)**
- Create a singleton class for database connection
- Use properties file for PostgreSQL configuration
- Handle connection lifecycle (open, close, get connection)

**EmployeeDAO.java (Data Access Object)**
- Implement basic CRUD operations:
  - `insert(Employee employee)` - Insert new employee (return generated ID)
  - `findById(int id)` - Find employee by ID
  - `findAll()` - Get all employees
  - `findByDepartment(String department)` - Find employees by department
  - `update(Employee employee)` - Update employee
  - `delete(int id)` - Delete employee by ID
- Use PreparedStatement for all database operations
- Handle SQL exceptions appropriately

**Main.java (Application)**
- Initialize database and create table
- Insert sample data (at least 5 employees from different departments)
- Demonstrate all DAO operations:
  - Insert a new employee
  - Find employee by ID
  - List all employees
  - Find employees by department
  - Update an employee's salary
  - Delete an employee
- Display results in a formatted way

#### 5. Sample Data
Insert the following employees:
- Arjun Reddy, arjun.reddy@company.com, Engineering, 75000
- Lakshmi Devi, lakshmi.devi@company.com, Marketing, 65000
- Karthik Kumar, karthik.kumar@company.com, Engineering, 80000
- Priya Sharma, priya.sharma@company.com, HR, 55000
- Rajesh Patel, rajesh.patel@company.com, Finance, 70000


#### 6. Configuration
Create `database.properties` with PostgreSQL-specific settings:
```properties
# PostgreSQL Database Configuration
db.url=jdbc:postgresql://localhost:5432/employee_db
db.username=your_username
db.password=your_password
db.driver=org.postgresql.Driver
```

### Expected Output
When running the application, you should see:
```
=== Employee Management System ===

1. All Employees:
   ID: 1, Name: Arjun Reddy, Department: Engineering, Salary: 75000.00
   ID: 2, Name: Lakshmi Devi, Department: Marketing, Salary: 65000.00
   ...

2. Employees in Engineering:
   ID: 1, Name: Arjun Reddy, Salary: 75000.00
   ID: 3, Name: Karthik Kumar, Salary: 80000.00

3. After updating salary:
   Employee 1 salary updated to: 80000.00

4. After deleting employee:
   Employee 5 deleted successfully
```

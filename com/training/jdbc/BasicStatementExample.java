package com.training.jdbc;

import java.sql.*;

public class BasicStatementExample {
    private static final String URL = "jdbc:postgresql://localhost:5432/training";
    private static final String USER = null;
    private static final String PASSWORD = null;

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

            // Create a statem
            // ent
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
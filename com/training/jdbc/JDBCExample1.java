package com.training.jdbc;
import java.sql.*;

public class JDBCExample1 {
    // Database connection details
    private static final String URL = "jdbc:postgresql://localhost:5432/training";
    private static final String USER = null;
    private static final String PASSWORD = null;

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
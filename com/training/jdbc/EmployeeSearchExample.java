package com.training.jdbc;

import java.sql.*;

public class EmployeeSearchExample {
    private static final String URL = "jdbc:postgresql://localhost:5432/training";
    private static final String USER = null;
    private static final String PASSWORD = null;

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
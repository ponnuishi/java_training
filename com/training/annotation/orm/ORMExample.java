package com.training.annotation.orm;

/**
 * Demonstrates ORM usage
 */
public class ORMExample {
    
    public static void main(String[] args) {
        // Generate CREATE TABLE SQL
//        String createTableSQL = SimpleORM.generateCreateTable(User.class);
//        System.out.println("CREATE TABLE SQL:");
//        System.out.println(createTableSQL);
        
        // Generate INSERT SQL
        User user = new User("john_doe", "john@example.com");
        String insertSQL = SimpleORM.generateInsert(user);
        System.out.println("\nINSERT SQL:");
        System.out.println(insertSQL);
    }
} 
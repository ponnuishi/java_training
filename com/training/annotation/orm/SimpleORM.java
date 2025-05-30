package com.training.annotation.orm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple ORM processor that generates SQL from annotations
 */
public class SimpleORM {
    
    public static String generateCreateTable(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new IllegalArgumentException("Class must have @Table annotation");
        }
        
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ").append(tableAnnotation.name()).append(" (");
        
        boolean first = true;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                if (!first) {
                    sql.append(", ");
                }
                first = false;
                
                Column columnAnnotation = field.getAnnotation(Column.class);
                sql.append(columnAnnotation.name()).append(" ");
                
                // Simple type mapping
                if (field.getType() == Long.class || field.getType() == long.class) {
                    sql.append("BIGINT");
                } else if (field.getType() == String.class) {
                    sql.append("VARCHAR(255)");
                } else {
                    sql.append("VARCHAR(255)");
                }
                
                if (columnAnnotation.primaryKey()) {
                    sql.append(" PRIMARY KEY");
                }
                
                if (!columnAnnotation.nullable()) {
                    sql.append(" NOT NULL");
                }
            }
        }
        
        sql.append(")");
        return sql.toString();
    }
    
    public static String generateInsert(Object obj) {
        Class<?> clazz = obj.getClass();
        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new IllegalArgumentException("Class must have @Table annotation");
        }
        
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(tableAnnotation.name()).append(" (");
        
        List<String> columns = new ArrayList<>();
        List<String> values = new ArrayList<>();
        
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                Column columnAnnotation = field.getAnnotation(Column.class);
                columns.add(columnAnnotation.name());
                
                try {
                    Object value = field.get(obj);
                    if (value instanceof String) {
                        values.add("'" + value + "'");
                    } else {
                        values.add(String.valueOf(value));
                    }
                } catch (IllegalAccessException e) {
                    values.add("NULL");
                }
            }
        }
        
        sql.append(String.join(", ", columns));
        sql.append(") VALUES (");
        sql.append(String.join(", ", values));
        sql.append(")");
        
        return sql.toString();
    }
} 
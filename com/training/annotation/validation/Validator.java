package com.training.annotation.validation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Validation processor that uses reflection to validate objects
 */
public class Validator {
    public static List<String> validate(Object obj) {
        List<String> errors = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            
            // Check @NotNull
            if (field.isAnnotationPresent(NotNull.class)) {
                try {
                    Object value = field.get(obj);
                    if (value == null) {
                        NotNull annotation = field.getAnnotation(NotNull.class);
                        errors.add(annotation.message());
                    }
                } catch (IllegalAccessException e) {
                    errors.add("Validation error for " + field.getName());
                }
            }
            
            // Check @MinLength
            if (field.isAnnotationPresent(MinLength.class)) {
                try {
                    Object value = field.get(obj);
                    if (value instanceof String) {
                        String strValue = (String) value;
                        MinLength annotation = field.getAnnotation(MinLength.class);
                        if (strValue.length() < annotation.value()) {
                            errors.add(annotation.message());
                        }
                    }
                } catch (IllegalAccessException e) {
                    errors.add("Validation error for " + field.getName());
                }
            }
            
            // Check @Email
            if (field.isAnnotationPresent(Email.class)) {
                try {
                    Object value = field.get(obj);
                    if (value instanceof String) {
                        String email = (String) value;
                        if (!email.contains("@")) {
                            Email annotation = field.getAnnotation(Email.class);
                            errors.add(annotation.message());
                        }
                    }
                } catch (IllegalAccessException e) {
                    errors.add("Validation error for " + field.getName());
                }
            }
        }
        
        return errors;
    }
} 
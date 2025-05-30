package com.training.annotation.processing;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Simplified annotation processor that demonstrates how to process the GenerateBuilder annotation
 * Note: This is a simplified version. Real annotation processors extend AbstractProcessor
 * and are used during compilation.
 */
public class BuilderProcessor {
    
    /**
     * Process a class and generate builder code
     * This is a demonstration of what an annotation processor would do
     */
    public static String generateBuilderCode(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(GenerateBuilder.class)) {
            throw new IllegalArgumentException("Class must have @GenerateBuilder annotation");
        }
        
        StringBuilder builderCode = new StringBuilder();
        String className = clazz.getSimpleName();
        String packageName = clazz.getPackage().getName();
        
        // Generate package declaration
        builderCode.append("package ").append(packageName).append(";\n\n");
        
        // Generate builder class
        builderCode.append("public class ").append(className).append("Builder {\n");
        
        // Generate fields
        for (Field field : clazz.getDeclaredFields()) {
            builderCode.append("    private ").append(field.getType().getSimpleName())
                      .append(" ").append(field.getName()).append(";\n");
        }
        builderCode.append("\n");
        
        // Generate setter methods
        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();
            String capitalizedFieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            
            builderCode.append("    public ").append(className).append("Builder ")
                      .append(fieldName).append("(").append(field.getType().getSimpleName())
                      .append(" ").append(fieldName).append(") {\n");
            builderCode.append("        this.").append(fieldName).append(" = ").append(fieldName).append(";\n");
            builderCode.append("        return this;\n");
            builderCode.append("    }\n\n");
        }
        
        // Generate build method
        builderCode.append("    public ").append(className).append(" build() {\n");
        builderCode.append("        return new ").append(className).append("(");
        
        boolean first = true;
        for (Field field : clazz.getDeclaredFields()) {
            if (!first) {
                builderCode.append(", ");
            }
            builderCode.append(field.getName());
            first = false;
        }
        builderCode.append(");\n");
        builderCode.append("    }\n");
        builderCode.append("}\n");
        
        return builderCode.toString();
    }
    
    /**
     * Demonstrate the builder generation
     */
    public static void demonstrateBuilderGeneration() {
        System.out.println("=== Generated Builder Code ===");
        String generatedCode = generateBuilderCode(Person.class);
        System.out.println(generatedCode);
        
        System.out.println("=== Usage Example ===");
        System.out.println("// This is how the generated builder would be used:");
        System.out.println("Person person = new PersonBuilder()");
        System.out.println("    .name(\"John Doe\")");
        System.out.println("    .age(30)");
        System.out.println("    .email(\"john@example.com\")");
        System.out.println("    .build();");
    }
} 
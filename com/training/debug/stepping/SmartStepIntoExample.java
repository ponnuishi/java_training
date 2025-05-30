package com.training.debug.stepping;

/**
 * Smart Step Into Example
 * Demonstrates smart step into functionality
 */
public class SmartStepIntoExample {
    public static void main(String[] args) {
        System.out.println("=== Smart Step Into Example ===");
        
        String result = processData(getInput(), validateInput());  // ← Smart Step Into
        System.out.println("Result: " + result);
        
        // Another example with multiple method calls
        String finalResult = formatOutput(processData("test", true), getTimestamp());  // ← Smart Step Into
        System.out.println("Final result: " + finalResult);
        
        System.out.println("Smart step into example completed.");
    }
    
    public static String getInput() {
        return "test input";
    }
    
    public static boolean validateInput() {
        return true;
    }
    
    public static String processData(String input, boolean valid) {
        return input.toUpperCase();
    }
    
    public static String formatOutput(String data, String timestamp) {
        return "[" + timestamp + "] " + data;
    }
    
    public static String getTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }
} 
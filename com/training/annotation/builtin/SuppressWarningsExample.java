package com.training.annotation.builtin;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates the usage of @SuppressWarnings annotation
 */
public class SuppressWarningsExample {
    
    public static void main(String[] args) {
        WarningExample example = new WarningExample();
        example.demonstrateSuppression();
        example.suppressMultipleWarnings();
    }
}

class WarningExample {
    @SuppressWarnings("unchecked")
    public void demonstrateSuppression() {
        // This would normally generate an unchecked warning
        List<String> list = new ArrayList();
        list.add("Hello");
        
        // But the warning is suppressed
        System.out.println("Suppressed unchecked warning: " + list);
    }
    
    @SuppressWarnings({"unchecked", "deprecation"})
    public void suppressMultipleWarnings() {
        // Suppresses both unchecked and deprecation warnings
        List list = new ArrayList();
        list.add("Test");
        
        // Some deprecated method call
        String deprecatedString = new String("test".getBytes());
        
        System.out.println("Suppressed multiple warnings: " + list + ", " + deprecatedString);
    }
} 
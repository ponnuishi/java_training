package com.training.debug.scenarios;

import java.util.ArrayList;
import java.util.List;

/**
 * Memory Leak Example
 * Demonstrates potential memory leak scenarios
 */
public class MemoryLeakExample {
    private static List<String> cache = new ArrayList<>();
    
    public static void main(String[] args) {
        System.out.println("=== Memory Leak Example ===");
        
        for (int i = 0; i < 1000; i++) {
            addToCache("Item " + i);  // ← Potential memory leak
        }
        
        // ← Set breakpoint here to inspect memory usage
        System.out.println("Cache size: " + cache.size());
        
        // Simulate some cache operations
        for (int i = 0; i < 100; i++) {
            addToCache("New Item " + i);
        }
        
        System.out.println("Final cache size: " + cache.size());
        System.out.println("Memory leak example completed.");
    }
    
    public static void addToCache(String item) {
        cache.add(item);  // ← Items never removed
    }
    
    // This method should be called to prevent memory leaks
    public static void clearCache() {
        cache.clear();
        System.out.println("Cache cleared");
    }
} 
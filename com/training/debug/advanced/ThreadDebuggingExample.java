package com.training.debug.advanced;

/**
 * Thread Debugging Example
 * Demonstrates debugging multi-threaded applications
 */
public class ThreadDebuggingExample {
    public static void main(String[] args) {
        System.out.println("=== Thread Debugging Example ===");
        
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread 1: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread 2: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        thread1.start();
        thread2.start();

        System.out.println("test application");
        
        // ← Set breakpoint here to inspect threads
        // Use Frames panel to switch between threads
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Thread debugging example completed.");
    }
} 
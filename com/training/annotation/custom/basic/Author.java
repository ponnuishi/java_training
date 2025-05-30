package com.training.annotation.custom.basic;

/**
 * Simple custom annotation for author information
 */
public @interface Author {
    String name();
    String email() default "unknown@example.com";
    int version() default 1;
} 
package com.training.annotation.custom.basic;

/**
 * Annotation with arrays for tagging
 */
public @interface Tags {
    String[] value();
    int[] numbers() default {1, 2, 3};
} 
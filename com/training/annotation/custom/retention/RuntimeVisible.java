package com.training.annotation.custom.retention;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// RUNTIME: Available at runtime through reflection
@Retention(RetentionPolicy.RUNTIME)
public @interface RuntimeVisible {
    String value();
} 
package com.training.annotation.custom.retention;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// CLASS: Stored in .class file but not available at runtime
@Retention(RetentionPolicy.CLASS)
public @interface CompileTime {
    String value();
} 
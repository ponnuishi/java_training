package com.training.annotation.custom.target;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
public @interface ClassOnly {
    String value();
} 
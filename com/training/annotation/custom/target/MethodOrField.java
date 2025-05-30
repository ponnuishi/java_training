package com.training.annotation.custom.target;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
public @interface MethodOrField {
    String value();
} 
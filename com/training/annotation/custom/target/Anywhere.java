package com.training.annotation.custom.target;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, 
         ElementType.PARAMETER, ElementType.CONSTRUCTOR})
public @interface Anywhere {
    String value();
} 
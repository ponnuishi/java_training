package com.training.annotation.logging;

import java.lang.reflect.Method;

/**
 * Logging aspect (simplified) that processes logging annotations
 */
public class LoggingAspect {
    public static void logMethodCall(Method method, Object[] args, Object result) {
        if (method.isAnnotationPresent(LogMethod.class)) {
            LogMethod annotation = method.getAnnotation(LogMethod.class);
            String level = annotation.level();
            
            System.out.println("[" + level + "] Calling method: " + method.getName());
            
            if (annotation.logParameters()) {
                System.out.println("  Parameters: " + java.util.Arrays.toString(args));
            }
            
            if (annotation.logReturnValue()) {
                System.out.println("  Return value: " + result);
            }
        }
    }
} 
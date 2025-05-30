package com.training.annotation.logging;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Demonstrates logging usage
 */
public class LoggingExample {
    
    public static void main(String[] args) {
        UserService service = new UserService();
        
        try {
            // These calls would be intercepted by the logging aspect
            Method createMethod = UserService.class.getMethod("createUser", String.class, String.class);
            UserService.User user = service.createUser("john", "john@example.com");
            LoggingAspect.logMethodCall(createMethod, new Object[]{"john", "john@example.com"}, user);
            
            Method deleteMethod = UserService.class.getMethod("deleteUser", Long.class);
            service.deleteUser(1L);
            LoggingAspect.logMethodCall(deleteMethod, new Object[]{1L}, null);
            
            Method getAllMethod = UserService.class.getMethod("getAllUsers");
            List<UserService.User> users = service.getAllUsers();
            LoggingAspect.logMethodCall(getAllMethod, new Object[]{}, users);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 
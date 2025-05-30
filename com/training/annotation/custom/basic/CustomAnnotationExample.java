package com.training.annotation.custom.basic;

/**
 * Demonstrates the usage of custom annotations
 */
@Author(name = "John Doe", email = "john@example.com", version = 2)
@Test
@Version(1)
@Tags({"java", "tutorial", "annotations"})
public class CustomAnnotationExample {
    
    @Author(name = "Jane Smith")
    @Tags(value = {"important"}, numbers = {5, 10, 15})
    public void myMethod() {
        System.out.println("Method with custom annotations");
    }
    
    public static void main(String[] args) {
        CustomAnnotationExample example = new CustomAnnotationExample();
        example.myMethod();
    }
} 
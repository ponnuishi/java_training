package com.training.annotation.orm;

/**
 * Entity class with database annotations
 */
@Table(name = "users")
public class User {
    @Id(autoIncrement = true)
    @Column(name = "id", primaryKey = true, nullable = false)
    private Long id;
    
    @Column(name = "username", nullable = false)
    private String username;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "created_at")
    private String createdAt;
    
    // Constructor
    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.createdAt = java.time.LocalDateTime.now().toString();
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
} 
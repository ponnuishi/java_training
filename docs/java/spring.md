# Spring Framework Tutorial for Intermediate Developers

## What is Spring Framework?

Spring Framework is a comprehensive, lightweight framework for building enterprise Java applications. It addresses the complexity of enterprise application development by providing a cohesive platform that covers multiple layers of application architecture.

### Core Philosophy: Inversion of Control (IoC)

Spring's fundamental principle is **Inversion of Control (IoC)**, which means that instead of your code controlling the flow of the program, the framework controls the flow and calls your code when needed. This is achieved through **Dependency Injection (DI)**, where dependencies are provided to objects rather than objects creating their own dependencies.

### Key Features and Benefits

1. **Dependency Injection (DI)**: Automatically wires components together, reducing coupling
2. **Aspect-Oriented Programming (AOP)**: Handles cross-cutting concerns like logging, security, and transactions
3. **Data Access Abstraction**: Simplified database operations with JDBC, JPA, and other ORM frameworks
4. **Web Development**: MVC framework for building web applications and REST APIs
5. **Testing Support**: Comprehensive testing framework with mock objects and integration testing
6. **Transaction Management**: Declarative transaction management
7. **Security**: Built-in security framework for authentication and authorization

## Understanding the Problem Spring Solves

### Traditional Java Development Problems

Before Spring, enterprise Java development faced several challenges:

```java
// Problem 1: Tight Coupling
public class UserService {
    private UserRepository userRepository;
    private EmailService emailService;
    private Logger logger;
    
    public UserService() {
        // Hard-coded dependencies - tight coupling
        this.userRepository = new UserRepositoryImpl();
        this.emailService = new EmailServiceImpl();
        this.logger = new LoggerImpl();
    }
    
    public void createUser(User user) {
        logger.log("Creating user: " + user.getName());
        userRepository.save(user);
        emailService.sendWelcomeEmail(user.getEmail());
    }
}

// Problem 2: Difficult Testing
public class UserServiceTest {
    @Test
    public void testCreateUser() {
        // Can't easily mock dependencies
        UserService userService = new UserService();
        // Test is tightly coupled to real implementations
    }
}
```

### How Spring Solves These Problems

```java
// Solution: Loose Coupling through DI
@Service
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final Logger logger;
    
    // Dependencies injected by Spring
    @Autowired
    public UserService(UserRepository userRepository, 
                      EmailService emailService, 
                      Logger logger) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.logger = logger;
    }
    
    public void createUser(User user) {
        logger.log("Creating user: " + user.getName());
        userRepository.save(user);
        emailService.sendWelcomeEmail(user.getEmail());
    }
}

// Easy Testing with Mocks
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock private UserRepository userRepository;
    @Mock private EmailService emailService;
    @Mock private Logger logger;
    @InjectMocks private UserService userService;
    
    @Test
    void testCreateUser() {
        User user = new User("John", "john@example.com");
        userService.createUser(user);
        
        verify(userRepository).save(user);
        verify(emailService).sendWelcomeEmail(user.getEmail());
    }
}
```

## Spring Core Concepts Explained

### 1. Application Context and Bean Container

The **ApplicationContext** is Spring's IoC container that manages the lifecycle of beans (Spring-managed objects). It's responsible for:

- Creating and managing beans
- Wiring dependencies between beans
- Managing bean lifecycle (creation, initialization, destruction)
- Providing configuration metadata

```java
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        // This creates and starts the ApplicationContext
        ApplicationContext context = SpringApplication.run(MyApplication.class, args);
        
        // You can retrieve beans from the context
        UserService userService = context.getBean(UserService.class);
        userService.createUser(new User("Alice", "alice@example.com"));
    }
}
```

### 2. Dependency Injection Types

Spring supports three types of dependency injection:

#### Constructor Injection (Recommended)
```java
@Service
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    
    // Constructor injection - dependencies are final and immutable
    @Autowired
    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }
}
```

#### Setter Injection
```java
@Service
public class UserService {
    private UserRepository userRepository;
    private EmailService emailService;
    
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}
```

#### Field Injection (Not Recommended)
```java
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;  // Harder to test
    
    @Autowired
    private EmailService emailService;      // Can't make final
}
```

## Spring Annotations Deep Dive

### Stereotype Annotations

These annotations mark classes as Spring-managed components and provide semantic meaning:

#### @Component
The base annotation for any Spring-managed component.

```java
@Component
public class EmailValidator {
    public boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }
}
```

#### @Service
Indicates that the class is a service layer component (business logic).

```java
@Service
public class UserService {
    private final UserRepository userRepository;
    private final EmailValidator emailValidator;
    
    @Autowired
    public UserService(UserRepository userRepository, EmailValidator emailValidator) {
        this.userRepository = userRepository;
        this.emailValidator = emailValidator;
    }
    
    public void createUser(User user) {
        if (!emailValidator.isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email address");
        }
        userRepository.save(user);
    }
}
```

#### @Repository
Indicates that the class is a data access layer component.

```java
@Repository
public class JpaUserRepository implements UserRepository {
    private final EntityManager entityManager;
    
    @Autowired
    public JpaUserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public void save(User user) {
        entityManager.persist(user);
    }
    
    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                          .getResultList();
    }
}
```

#### @Controller
Indicates that the class is a web controller (handles HTTP requests).

```java
@Controller
public class UserController {
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/list";  // View name
    }
    
    @PostMapping("/users")
    public String createUser(@ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) {
            return "users/create";
        }
        userService.createUser(user);
        return "redirect:/users";
    }
}
```

### Configuration Annotations

#### @Configuration
Marks a class as a source of bean definitions.

```java
@Configuration
public class AppConfiguration {
    
    @Bean
    public UserRepository userRepository() {
        return new InMemoryUserRepository();
    }
    
    @Bean
    public EmailService emailService() {
        return new SmtpEmailService();
    }
    
    @Bean
    public Logger logger() {
        return new ConsoleLogger();
    }
}
```

#### @Bean
Defines a bean in a @Configuration class.

```java
@Configuration
public class DatabaseConfiguration {
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/mydb");
        dataSource.setUsername("username");
        dataSource.setPassword("password");
        return dataSource;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
```

### Dependency Injection Annotations

#### @Autowired
Injects dependencies automatically. Can be used on constructors, setters, or fields.

```java
@Service
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    
    // Constructor injection (preferred)
    @Autowired
    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }
}
```

#### @Qualifier
Specifies which bean to inject when multiple implementations exist.

```java
// Multiple implementations of Logger
@Component
@Qualifier("console")
public class ConsoleLogger implements Logger {
    public void log(String message) {
        System.out.println("[CONSOLE] " + message);
    }
}

@Component
@Qualifier("file")
public class FileLogger implements Logger {
    public void log(String message) {
        // Write to file
    }
}

// Service using specific implementation
@Service
public class UserService {
    private final Logger logger;
    
    @Autowired
    public UserService(@Qualifier("console") Logger logger) {
        this.logger = logger;
    }
}
```

#### @Value
Injects values from properties files or environment variables.

```java
@Component
public class DatabaseService {
    private final String databaseUrl;
    private final int maxConnections;
    
    @Autowired
    public DatabaseService(@Value("${database.url}") String databaseUrl,
                          @Value("${database.max-connections:10}") int maxConnections) {
        this.databaseUrl = databaseUrl;
        this.maxConnections = maxConnections;
    }
}
```

## Practical Spring Boot Application

Let's build a complete Spring Boot application that demonstrates all the concepts:

### Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/example/demo/
│   │       ├── DemoApplication.java
│   │       ├── controller/
│   │       │   └── UserController.java
│   │       ├── service/
│   │       │   └── UserService.java
│   │       ├── repository/
│   │       │   └── UserRepository.java
│   │       ├── model/
│   │       │   └── User.java
│   │       └── config/
│   │           └── AppConfiguration.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/example/demo/
            └── UserServiceTest.java
```

### Main Application Class
```java
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

### Model Class
```java
public class User {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    
    // Constructors
    public User() {}
    
    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}
```

### Repository Interface and Implementation
```java
// Repository interface
public interface UserRepository {
    void save(User user);
    List<User> findAll();
    Optional<User> findById(Long id);
    void deleteById(Long id);
}

// In-memory implementation
@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    @Override
    public void save(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.getAndIncrement());
        }
        users.put(user.getId(), user);
    }
    
    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
    
    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }
    
    @Override
    public void deleteById(Long id) {
        users.remove(id);
    }
}
```

### Service Layer
```java
@Service
public class UserService {
    private final UserRepository userRepository;
    private final Logger logger;
    
    @Autowired
    public UserService(UserRepository userRepository, Logger logger) {
        this.userRepository = userRepository;
        this.logger = logger;
    }
    
    public User createUser(User user) {
        logger.log("Creating user: " + user.getName());
        
        // Validate user data
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be empty");
        }
        
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        
        userRepository.save(user);
        logger.log("User created successfully: " + user.getId());
        return user;
    }
    
    public List<User> getAllUsers() {
        logger.log("Retrieving all users");
        return userRepository.findAll();
    }
    
    public Optional<User> getUserById(Long id) {
        logger.log("Retrieving user with ID: " + id);
        return userRepository.findById(id);
    }
    
    public void deleteUser(Long id) {
        logger.log("Deleting user with ID: " + id);
        userRepository.deleteById(id);
    }
}
```

### Controller Layer
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
```

### Configuration
```java
@Configuration
public class AppConfiguration {
    
    @Bean
    public Logger logger() {
        return new ConsoleLogger();
    }
}

@Component
public class ConsoleLogger implements Logger {
    public void log(String message) {
        System.out.println("[LOG] " + LocalDateTime.now() + " - " + message);
    }
}

public interface Logger {
    void log(String message);
}
```

### Application Properties
```properties
# Server configuration
server.port=8080
server.servlet.context-path=/demo

# Logging configuration
logging.level.com.example.demo=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n

# Custom properties
app.name=Spring Demo Application
app.version=1.0.0
```

### Testing
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private Logger logger;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    void testCreateUser_Success() {
        // Arrange
        User user = new User("John Doe", "john@example.com");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return null;
        });
        
        // Act
        User result = userService.createUser(user);
        
        // Assert
        assertThat(result.getId()).isEqualTo(1L);
        verify(userRepository).save(user);
        verify(logger).log("Creating user: John Doe");
        verify(logger).log("User created successfully: 1");
    }
    
    @Test
    void testCreateUser_EmptyName_ThrowsException() {
        // Arrange
        User user = new User("", "john@example.com");
        
        // Act & Assert
        assertThatThrownBy(() -> userService.createUser(user))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("User name cannot be empty");
        
        verify(userRepository, never()).save(any());
    }
}
```

## Advanced Spring Concepts

### 1. Profiles for Environment-Specific Configuration

```java
@Configuration
public class DatabaseConfiguration {
    
    @Bean
    @Profile("dev")
    public DataSource devDataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:schema.sql")
            .addScript("classpath:data.sql")
            .build();
    }
    
    @Bean
    @Profile("prod")
    public DataSource prodDataSource(
            @Value("${database.url}") String url,
            @Value("${database.username}") String username,
            @Value("${database.password}") String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
```

### 2. AOP (Aspect-Oriented Programming)

```java
@Aspect
@Component
public class LoggingAspect {
    
    private final Logger logger;
    
    @Autowired
    public LoggingAspect(Logger logger) {
        this.logger = logger;
    }
    
    @Before("execution(* com.example.demo.service.*.*(..))")
    public void logBeforeServiceMethods(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.log("Executing " + className + "." + methodName);
    }
    
    @AfterThrowing(pointcut = "execution(* com.example.demo.service.*.*(..))", 
                   throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        String methodName = joinPoint.getSignature().getName();
        logger.log("Exception in " + methodName + ": " + error.getMessage());
    }
}
```

## Best Practices and Common Patterns

### 1. Use Constructor Injection
```java
// ✅ Good - Constructor injection
@Service
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    
    @Autowired
    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }
}

// ❌ Avoid - Field injection
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;  // Harder to test
}
```

### 2. Keep Services Stateless
```java
// ✅ Good - Stateless service
@Service
public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public void createUser(User user) {
        userRepository.save(user);
    }
}

// ❌ Avoid - Stateful service (unless necessary)
@Service
public class UserService {
    private int userCount = 0;  // State that could cause issues
}
```

### 3. Use Appropriate Exception Handling
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException e) {
        ErrorResponse error = new ErrorResponse("BAD_REQUEST", e.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        ErrorResponse error = new ErrorResponse("INTERNAL_ERROR", "An unexpected error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
```

## Common Use Cases

### 1. REST API with Spring Boot
```java
@RestController
@RequestMapping("/api/v1")
public class UserRestController {
    private final UserService userService;
    
    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/users")
    public ResponseEntity<Page<User>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<User> users = userService.getUsers(PageRequest.of(page, size));
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/api/v1/users/" + createdUser.getId())
                .body(createdUser);
    }
}
```

### 2. Scheduled Tasks
```java
@Configuration
@EnableScheduling
public class SchedulingConfiguration { }

@Component
public class ScheduledTasks {
    private final Logger logger;
    
    @Autowired
    public ScheduledTasks(Logger logger) {
        this.logger = logger;
    }
    
    @Scheduled(fixedRate = 60000) // Every minute
    public void performPeriodicTask() {
        logger.log("Performing periodic task");
    }
    
    @Scheduled(cron = "0 0 2 * * ?") // Every day at 2 AM
    public void performDailyTask() {
        logger.log("Performing daily task");
    }
}
```

## Summary

Spring Framework provides a comprehensive solution for building enterprise Java applications by addressing common development challenges:

1. **Dependency Injection** eliminates tight coupling and makes code more testable
2. **Inversion of Control** lets Spring manage object lifecycle and wiring
3. **Aspect-Oriented Programming** handles cross-cutting concerns cleanly
4. **Rich ecosystem** provides solutions for web development, data access, security, and more

Key takeaways for intermediate Java developers:
- Use constructor injection for required dependencies
- Keep services stateless and focused on single responsibilities
- Leverage Spring's testing support for comprehensive test coverage
- Use appropriate stereotype annotations for clear component roles
- Externalize configuration for environment-specific settings
- Follow Spring Boot conventions for rapid development
- Implement proper exception handling and validation

Spring's philosophy of "convention over configuration" combined with its powerful DI container makes it an excellent choice for building maintainable, scalable enterprise applications. 
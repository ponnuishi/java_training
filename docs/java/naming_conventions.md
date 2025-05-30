# Java Naming Conventions

Java naming conventions are essential for writing clean, readable, and maintainable code. Following these conventions helps make your code more understandable to other developers and maintains consistency across Java projects.

## 1. Package Names

- **Convention**: All lowercase letters
- **Pattern**: `com.organization.project.module`
- **Examples**:
  ```java
  // E-commerce application packages
  com.amazon.retail.order
  com.amazon.retail.product
  com.amazon.retail.inventory
  
  // Banking application packages
  com.bankofamerica.accounts.savings
  com.bankofamerica.transactions.payment
  
  // Social media application packages
  com.facebook.newsfeed
  com.facebook.messenger.chat
  ```
- **Rules**:
    - Use reverse domain name notation
    - All lowercase letters
    - Separate words using dots
    - Avoid special characters

## 2. Class Names

- **Convention**: PascalCase (UpperCamelCase)
- **Pattern**: Noun or noun phrase
- **Examples**:
  ```java
  // E-commerce domain classes
  public class ProductCatalog { }
  public class ShoppingCart { }
  public class OrderConfirmation { }
  
  // Banking domain classes
  public class TransactionProcessor { }
  public class AccountStatement { }
  public class CreditCardValidator { }
  
  // Authentication domain classes
  public class JwtTokenProvider { }
  public class OAuth2Client { }
  ```
- **Rules**:
    - Start with uppercase letter
    - Use nouns
    - Be descriptive and clear
    - Avoid abbreviations unless widely known

### Special Class Types

#### Interfaces
```java
// Spring Data repositories
public interface CustomerRepository extends JpaRepository<Customer, Long> { }
public interface OrderRepository extends MongoRepository<Order, String> { }

// Service interfaces
public interface PaymentGateway { }
public interface NotificationService { }

// Event interfaces
public interface OrderCreatedEventListener { }
```

#### Abstract Classes
```java
// Common abstract classes in Spring
public abstract class AbstractAuditingEntity {
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}

// Abstract payment processor
public abstract class AbstractPaymentProcessor {
    protected abstract void validatePayment();
    protected abstract void processPayment();
    protected abstract void notifyUser();
}
```

#### Exception Classes
```java
// Domain-specific exceptions
public class InsufficientBalanceException extends RuntimeException { }
public class PaymentDeclinedException extends RuntimeException { }
public class ProductOutOfStockException extends RuntimeException { }
```

## 3. Method Names

- **Convention**: camelCase (lowerCamelCase)
- **Pattern**: Verb or verb phrase
- **Examples**:
  ```java
  // E-commerce service methods
  public Order processCheckout(ShoppingCart cart) { }
  public List<Product> searchProductsByCategory(String category) { }
  public void applyDiscountCode(String code) { }
  
  // Banking service methods
  public void transferFunds(Account from, Account to, BigDecimal amount) { }
  public boolean validateTransactionLimit(BigDecimal amount) { }
  
  // User management methods
  public User authenticateUser(String username, String password) { }
  public void resetPasswordWithToken(String token, String newPassword) { }
  ```

### Special Method Types

#### Getters and Setters
```java
// Modern entity class with proper naming
public class Customer {
    private String emailAddress;
    private boolean premiumMember;
    private LocalDate subscriptionEndDate;

    public String getEmailAddress() { }
    public void setEmailAddress(String emailAddress) { }
    public boolean isPremiumMember() { }
    public void setPremiumMember(boolean premiumMember) { }
    public LocalDate getSubscriptionEndDate() { }
    public void setSubscriptionEndDate(LocalDate date) { }
}
```

#### Static Factory Methods
```java
// DTO creation methods
public class UserDTO {
    public static UserDTO fromEntity(User user) { }
    public static UserDTO fromRequest(RegistrationRequest request) { }
}

// Builder pattern factories
public class PaymentRequest {
    public static PaymentRequest createCreditCardPayment(String cardNumber) { }
    public static PaymentRequest createPayPalPayment(String email) { }
}
```

## 4. Variable Names

- **Convention**: camelCase (lowerCamelCase)
- **Pattern**: Noun or noun phrase
- **Examples**:
  ```java
  // E-commerce variables
  List<OrderItem> cartItems;
  BigDecimal totalAmount;
  Customer currentCustomer;
  
  // Payment processing
  PaymentStatus transactionStatus;
  String merchantReferenceNumber;
  boolean paymentSuccessful;
  
  // API related
  HttpHeaders requestHeaders;
  ResponseEntity<User> apiResponse;
  String bearerToken;
  ```

### Special Variable Types

#### Constants
```java
// Application constants
public static final int MAX_LOGIN_ATTEMPTS = 3;
public static final String JWT_SECRET_KEY = "${jwt.secret}";
public static final long TOKEN_VALIDITY_SECONDS = 86400;

// Business rule constants
public static final BigDecimal MINIMUM_ORDER_AMOUNT = new BigDecimal("10.00");
public static final int MAXIMUM_ITEMS_PER_ORDER = 50;
public static final String DEFAULT_TIMEZONE = "UTC";
```

#### Generic Type Parameters
```java
// Common Spring repository usage
public interface Repository<T extends BaseEntity, ID extends Serializable> { }

// Service layer generics
public class CrudService<T, ID> {
    public Optional<T> findById(ID id) { }
    public List<T> findAll() { }
}

// Generic response wrapper
public class ApiResponse<T> {
    private T data;
    private String message;
    private boolean success;
}
```

## 5. Enum Names

- **Convention**: PascalCase for enum name, UPPERCASE for enum constants
- **Examples**:
  ```java
  // Order status tracking
  public enum OrderStatus {
      PENDING_PAYMENT,
      PAYMENT_CONFIRMED,
      PROCESSING,
      SHIPPED,
      DELIVERED,
      CANCELLED,
      REFUNDED
  }

  // User roles in authentication
  public enum UserRole {
      ROLE_ADMIN,
      ROLE_MANAGER,
      ROLE_USER,
      ROLE_GUEST
  }
  
  // Payment methods
  public enum PaymentMethod {
      CREDIT_CARD,
      DEBIT_CARD,
      PAYPAL,
      BANK_TRANSFER,
      CRYPTO
  }
  ```

## 6. Annotation Names

- **Convention**: PascalCase
- **Examples**:
  ```java
  // Spring framework annotations
  @RestController
  @RequestMapping("/api/v1/users")
  @Transactional(readOnly = true)
  
  // Custom annotations
  @Auditable(module = "orders")
  @RateLimited(maxRequests = 100)
  @RequiresPermission("ADMIN")
  ```

## 7. Common Prefixes and Suffixes

### Common Prefixes
- `get` - Accessor methods
  ```java
  public List<Order> getCurrentUserOrders()
  public Optional<User> getAuthenticatedUser()
  ```
- `set` - Mutator methods
  ```java
  public void setDeliveryAddress(Address address)
  public void setPreferredLanguage(Locale locale)
  ```
- `is` - Boolean accessor methods
  ```java
  public boolean isEligibleForDiscount()
  public boolean isAccountLocked()
  ```
- `has` - Boolean accessor methods
  ```java
  public boolean hasPermission(String permission)
  public boolean hasSubscription()
  ```
- `calc` or `calculate` - Methods that compute values
  ```java
  public BigDecimal calculateOrderTotal()
  public int calculateLoyaltyPoints()
  ```
- `find` - Methods that search for something
  ```java
  public List<Product> findProductsByCategory(String category)
  public Optional<User> findByEmail(String email)
  ```
- `init` or `initialize` - Initialization methods
  ```java
  public void initializePaymentGateway()
  public void initializeCache()
  ```

### Common Suffixes
- `Service` - Service classes
  ```java
  public class PaymentProcessingService
  public class UserNotificationService
  ```
- `Repository` - Data access classes
  ```java
  public interface OrderRepository extends JpaRepository<Order, Long>
  public interface ProductRepository extends MongoRepository<Product, String>
  ```
- `Controller` - Controller classes
  ```java
  public class OrderManagementController
  public class UserAuthenticationController
  ```
- `Factory` - Factory classes
  ```java
  public class PaymentProviderFactory
  public class DocumentGeneratorFactory
  ```
- `Builder` - Builder pattern classes
  ```java
  public class OrderBuilder
  public class EmailTemplateBuilder
  ```
- `Exception` - Exception classes
  ```java
  public class PaymentValidationException
  public class UserAuthenticationException
  ```
- `Impl` - Implementation classes
  ```java
  public class CacheServiceImpl implements CacheService
  public class AuditLoggerImpl implements AuditLogger
  ```

## 8. Best Practices

1. **Be Descriptive**
    ```java
    // Bad
    public void proc(Order o) { }
    private String fn;
    
    // Good
    public void processOrder(Order order) { }
    private String firstName;
    ```

2. **Use Meaningful Names**
   ```java
   // Bad
   int d; // days since last login
   boolean chk; // check if user is active
   
   // Good
   int daysSinceLastLogin;
   boolean isUserActive;
   
   // Real-world example
   public class UserActivityTracker {
       private int loginAttempts;
       private LocalDateTime lastSuccessfulLogin;
       private boolean accountLocked;
       private List<SecurityEvent> securityEvents;
   }
   ```

3. **Use Domain Language**
    ```java
    // Banking domain example
    public class Transaction {
        private BigDecimal principal;
        private BigDecimal interestRate;
        private int termInMonths;
        private TransactionType type;
        
        public BigDecimal calculateCompoundInterest() { }
    }
    
    // E-commerce domain example
    public class ShoppingCart {
        private List<LineItem> lineItems;
        private BigDecimal subtotal;
        private BigDecimal taxAmount;
        private List<DiscountCode> appliedDiscounts;
        
        public void addToCart(Product product, int quantity) { }
    }
    ```

## 9. Examples in Context

### Complete Class Example
```java
@Service
@Transactional
public class OrderProcessingService {
    private static final BigDecimal MINIMUM_ORDER_AMOUNT = new BigDecimal("10.00");
    private static final int MAX_RETRY_ATTEMPTS = 3;
    
    private final OrderRepository orderRepository;
    private final PaymentGateway paymentGateway;
    private final NotificationService notificationService;
    private final UserActivityTracker userActivityTracker;
    
    public OrderProcessingService(
            OrderRepository orderRepository,
            PaymentGateway paymentGateway,
            NotificationService notificationService,
            UserActivityTracker userActivityTracker) {
        this.orderRepository = orderRepository;
        this.paymentGateway = paymentGateway;
        this.notificationService = notificationService;
        this.userActivityTracker = userActivityTracker;
    }
    
    public OrderResponse processOrder(OrderRequest orderRequest) {
        validateOrderRequest(orderRequest);
        Order order = createOrder(orderRequest);
        
        try {
            PaymentResult paymentResult = processPayment(order);
            if (paymentResult.isSuccessful()) {
                order.setStatus(OrderStatus.PAYMENT_CONFIRMED);
                orderRepository.save(order);
                notificationService.sendOrderConfirmation(order);
                userActivityTracker.trackSuccessfulPurchase(order);
                return OrderResponse.success(order);
            } else {
                handleFailedPayment(order, paymentResult);
                return OrderResponse.failure(paymentResult.getErrorMessage());
            }
        } catch (PaymentProcessingException e) {
            handlePaymentError(order, e);
            return OrderResponse.error("Payment processing failed");
        }
    }
    
    private void validateOrderRequest(OrderRequest orderRequest) {
        if (orderRequest.getTotalAmount().compareTo(MINIMUM_ORDER_AMOUNT) < 0) {
            throw new InvalidOrderException("Order amount below minimum threshold");
        }
    }
    
    private Order createOrder(OrderRequest orderRequest) {
        return Order.builder()
                .items(orderRequest.getItems())
                .totalAmount(orderRequest.getTotalAmount())
                .shippingAddress(orderRequest.getShippingAddress())
                .status(OrderStatus.PENDING_PAYMENT)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
```

## 10. Enforcing Naming Conventions

Several tools are available to help enforce Java naming conventions:

### Checkstyle
- A development tool that helps programmers write Java code that adheres to a coding standard
- Can be configured to check naming conventions for:
  ```xml
  <module name="TypeName"/>           <!-- Class names -->
  <module name="MethodName"/>         <!-- Method names -->
  <module name="ParameterName"/>      <!-- Method parameters -->
  <module name="ConstantName"/>       <!-- Constants -->
  <module name="LocalVariableName"/>  <!-- Local variables -->
  ```
- Integrates with build tools like Maven and Gradle
- Provides IDE plugins for real-time feedback

### PMD
- Static code analyzer that includes naming convention rules
- Offers rules like:
  - ShortVariable
  - LongVariable
  - ShortMethodName
  - ClassNamingConventions
- Can be customized through ruleset XML files
- Supports CI/CD pipeline integration

### SonarQube
- Continuous code quality platform
- Provides out-of-the-box rules for Java naming conventions
- Offers detailed reports and metrics
- Tracks naming convention violations over time
- Supports custom quality gates and rules

This setup helps teams maintain consistent naming conventions across their codebase by:
- Automatically checking code during builds
- Preventing commits that violate naming conventions
- Providing immediate feedback in the IDE
- Enforcing team-wide standards consistently

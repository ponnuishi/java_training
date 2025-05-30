package com.training.optional.patterns;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Examples demonstrating common Optional patterns and real-world usage
 */
public class OptionalPatternsExample {
    public static void main(String[] args) {
        System.out.println("=== Optional Patterns Examples ===");
        
        repositoryPattern();
        configurationPattern();
        nestedObjectAccess();
        collectionProcessing();
        cachePattern();
        validationPattern();
    }
    
    private static void repositoryPattern() {
        System.out.println("\n--- Repository Pattern ---");
        
        UserRepository repository = new UserRepository();
        
        // Traditional approach vs Optional approach
        System.out.println("Finding existing user:");
        Optional<User> user1 = repository.findById(1L);
        user1.ifPresentOrElse(
            user -> System.out.println("Found: " + user.getName()),
            () -> System.out.println("User not found")
        );
        
        System.out.println("Finding non-existing user:");
        Optional<User> user2 = repository.findById(999L);
        user2.ifPresentOrElse(
            user -> System.out.println("Found: " + user.getName()),
            () -> System.out.println("User not found")
        );
        
        // Using Optional in service layer
        UserService service = new UserService(repository);
        String userInfo = service.getUserInfo(1L);
        System.out.println("User info: " + userInfo);
        
        String missingUserInfo = service.getUserInfo(999L);
        System.out.println("Missing user info: " + missingUserInfo);
        
        // Finding by email
        Optional<User> userByEmail = repository.findByEmail("alice@example.com");
        String emailResult = userByEmail
            .map(User::getName)
            .orElse("User not found");
        System.out.println("User by email: " + emailResult);
    }
    
    private static void configurationPattern() {
        System.out.println("\n--- Configuration Pattern ---");
        
        ConfigurationService config = new ConfigurationService();
        
        // Database URL configuration
        String dbUrl = config.getDatabaseUrl()
            .orElse("jdbc:h2:mem:testdb");
        System.out.println("Database URL: " + dbUrl);
        
        // Port configuration with validation
        int port = config.getPort()
            .filter(p -> p > 0 && p < 65536)
            .orElse(8080);
        System.out.println("Server port: " + port);
        
        // Debug mode configuration
        boolean debugMode = config.getDebugMode().orElse(false);
        System.out.println("Debug mode: " + debugMode);
        
        // Timeout configuration with transformation
        int timeoutMs = config.getTimeoutSeconds()
            .map(seconds -> seconds * 1000)
            .orElse(30000);
        System.out.println("Timeout (ms): " + timeoutMs);
        
        // Feature flags
        if (config.isFeatureEnabled("NEW_UI").orElse(false)) {
            System.out.println("New UI feature is enabled");
        } else {
            System.out.println("Using legacy UI");
        }
    }
    
    private static void nestedObjectAccess() {
        System.out.println("\n--- Nested Object Access ---");
        
        // Traditional null checking vs Optional chaining
        Person person1 = new Person("Alice", 
            new Address("123 Main St", new City("New York", "NY")));
        Person person2 = new Person("Bob", null);
        Person person3 = new Person("Charlie", 
            new Address("456 Oak Ave", null));
        
        // Using Optional chaining
        System.out.println("Person 1 city: " + getPersonCity(person1));
        System.out.println("Person 2 city: " + getPersonCity(person2));
        System.out.println("Person 3 city: " + getPersonCity(person3));
        
        // Getting state with fallback
        System.out.println("Person 1 state: " + getPersonState(person1));
        System.out.println("Person 2 state: " + getPersonState(person2));
        System.out.println("Person 3 state: " + getPersonState(person3));
        
        // Complex nested access
        String fullLocation = getFullLocation(person1);
        System.out.println("Full location: " + fullLocation);
    }
    
    private static void collectionProcessing() {
        System.out.println("\n--- Collection Processing ---");
        
        List<User> users = Arrays.asList(
            new User(1L, "Alice", "alice@example.com"),
            new User(2L, "Bob", "bob@example.com"),
            new User(3L, "Charlie", null)
        );
        
        // Find first user with email
        Optional<User> userWithEmail = users.stream()
            .filter(user -> user.getEmail().isPresent())
            .findFirst();
        
        System.out.println("First user with email: " + 
            userWithEmail.map(User::getName).orElse("None"));
        
        // Collect all emails (non-empty)
        List<String> emails = users.stream()
            .map(User::getEmail)
            .flatMap(Optional::stream)  // Java 9+ feature
            .collect(Collectors.toList());
        
        System.out.println("All emails: " + emails);
        
        // Find user by ID
        Optional<User> foundUser = users.stream()
            .filter(user -> user.getId().equals(2L))
            .findFirst();
        
        foundUser.ifPresent(user -> System.out.println("Found user: " + user.getName()));
        
        // Get email domains
        List<String> domains = users.stream()
            .map(User::getEmail)
            .flatMap(Optional::stream)
            .map(email -> email.substring(email.indexOf('@') + 1))
            .distinct()
            .collect(Collectors.toList());
        
        System.out.println("Email domains: " + domains);
    }
    
    private static void cachePattern() {
        System.out.println("\n--- Cache Pattern ---");
        
        Cache cache = new Cache();
        
        // Cache miss and hit
        String value1 = cache.get("key1").orElseGet(() -> {
            System.out.println("Cache miss - loading from database");
            String value = "Loaded value for key1";
            cache.put("key1", value);
            return value;
        });
        System.out.println("Value 1: " + value1);
        
        // Cache hit
        String value2 = cache.get("key1").orElseGet(() -> {
            System.out.println("This shouldn't print - cache hit");
            return "Fallback value";
        });
        System.out.println("Value 2: " + value2);
        
        // Cache with expiration
        cache.put("temp_key", "Temporary value");
        Optional<String> tempValue = cache.get("temp_key");
        System.out.println("Temp value: " + tempValue.orElse("Expired"));
    }
    
    private static void validationPattern() {
        System.out.println("\n--- Validation Pattern ---");
        
        ValidationService validator = new ValidationService();
        
        // Validate email addresses
        List<String> emails = Arrays.asList(
            "valid@example.com",
            "invalid-email",
            "another@test.org",
            "bad@",
            "good@domain.co.uk"
        );
        
        emails.forEach(email -> {
            Optional<String> validEmail = validator.validateEmail(email);
            System.out.println(email + " -> " + validEmail.orElse("INVALID"));
        });
        
        // Validate and transform user input
        List<String> userInputs = Arrays.asList(
            "  John Doe  ",
            "",
            "   ",
            "Alice Smith",
            "a"
        );
        
        System.out.println("\nUser input validation:");
        userInputs.forEach(input -> {
            Optional<String> validName = validator.validateAndCleanName(input);
            System.out.println("'" + input + "' -> " + validName.orElse("INVALID"));
        });
    }
    
    // Helper methods
    private static String getPersonCity(Person person) {
        return Optional.ofNullable(person)
            .flatMap(Person::getAddress)
            .flatMap(Address::getCity)
            .map(City::getName)
            .orElse("Unknown");
    }
    
    private static String getPersonState(Person person) {
        return Optional.ofNullable(person)
            .flatMap(Person::getAddress)
            .flatMap(Address::getCity)
            .map(City::getState)
            .orElse("Unknown");
    }
    
    private static String getFullLocation(Person person) {
        return Optional.ofNullable(person)
            .flatMap(Person::getAddress)
            .map(address -> {
                String street = address.getStreet();
                String city = address.getCity()
                    .map(City::getName)
                    .orElse("Unknown City");
                String state = address.getCity()
                    .map(City::getState)
                    .orElse("Unknown State");
                return street + ", " + city + ", " + state;
            })
            .orElse("Address not available");
    }
    
    // Helper classes
    static class User {
        private Long id;
        private String name;
        private String email;
        
        public User(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
        
        public Long getId() { return id; }
        public String getName() { return name; }
        public Optional<String> getEmail() { return Optional.ofNullable(email); }
    }
    
    static class Person {
        private String name;
        private Address address;
        
        public Person(String name, Address address) {
            this.name = name;
            this.address = address;
        }
        
        public String getName() { return name; }
        public Optional<Address> getAddress() { return Optional.ofNullable(address); }
    }
    
    static class Address {
        private String street;
        private City city;
        
        public Address(String street, City city) {
            this.street = street;
            this.city = city;
        }
        
        public String getStreet() { return street; }
        public Optional<City> getCity() { return Optional.ofNullable(city); }
    }
    
    static class City {
        private String name;
        private String state;
        
        public City(String name, String state) {
            this.name = name;
            this.state = state;
        }
        
        public String getName() { return name; }
        public String getState() { return state; }
    }
    
    static class UserRepository {
        private Map<Long, User> users = new HashMap<>();
        
        public UserRepository() {
            users.put(1L, new User(1L, "Alice", "alice@example.com"));
            users.put(2L, new User(2L, "Bob", "bob@example.com"));
            users.put(3L, new User(3L, "Charlie", null));
        }
        
        public Optional<User> findById(Long id) {
            return Optional.ofNullable(users.get(id));
        }
        
        public Optional<User> findByEmail(String email) {
            return users.values().stream()
                .filter(user -> user.getEmail().map(e -> e.equals(email)).orElse(false))
                .findFirst();
        }
    }
    
    static class UserService {
        private UserRepository repository;
        
        public UserService(UserRepository repository) {
            this.repository = repository;
        }
        
        public String getUserInfo(Long id) {
            return repository.findById(id)
                .map(user -> "User: " + user.getName() + 
                    ", Email: " + user.getEmail().orElse("No email"))
                .orElse("User not found");
        }
    }
    
    static class ConfigurationService {
        private Map<String, String> properties = new HashMap<>();
        
        public ConfigurationService() {
            properties.put("server.port", "9090");
            properties.put("debug.enabled", "true");
            properties.put("timeout.seconds", "60");
            properties.put("feature.NEW_UI", "true");
        }
        
        public Optional<String> getDatabaseUrl() {
            return Optional.ofNullable(properties.get("database.url"));
        }
        
        public Optional<Integer> getPort() {
            return Optional.ofNullable(properties.get("server.port"))
                .map(Integer::parseInt);
        }
        
        public Optional<Boolean> getDebugMode() {
            return Optional.ofNullable(properties.get("debug.enabled"))
                .map(Boolean::parseBoolean);
        }
        
        public Optional<Integer> getTimeoutSeconds() {
            return Optional.ofNullable(properties.get("timeout.seconds"))
                .map(Integer::parseInt);
        }
        
        public Optional<Boolean> isFeatureEnabled(String featureName) {
            return Optional.ofNullable(properties.get("feature." + featureName))
                .map(Boolean::parseBoolean);
        }
    }
    
    static class Cache {
        private Map<String, String> cache = new HashMap<>();
        
        public Optional<String> get(String key) {
            return Optional.ofNullable(cache.get(key));
        }
        
        public void put(String key, String value) {
            cache.put(key, value);
        }
    }
    
    static class ValidationService {
        public Optional<String> validateEmail(String email) {
            if (email != null && email.contains("@") && email.contains(".") && 
                email.indexOf("@") < email.lastIndexOf(".")) {
                return Optional.of(email);
            }
            return Optional.empty();
        }
        
        public Optional<String> validateAndCleanName(String name) {
            return Optional.ofNullable(name)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .filter(s -> s.length() >= 2)
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase());
        }
    }
} 
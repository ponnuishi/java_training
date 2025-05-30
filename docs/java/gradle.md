# Gradle Tutorial for Windows

## What is Gradle?

Gradle is a modern build automation tool that uses a flexible, domain-specific language (DSL) based on Groovy or Kotlin. It's designed to automate the building, testing, and deployment of software projects.

### Key Features of Gradle:

1. **Flexible Build System**: Supports multiple programming languages and platforms
2. **Dependency Management**: Automatically downloads and manages project dependencies
3. **Incremental Builds**: Only rebuilds what has changed, making builds faster
4. **Plugin Ecosystem**: Rich ecosystem of plugins for various technologies
5. **Multi-project Support**: Manages complex projects with multiple modules

## Setting Up Gradle on Windows

### Step 1: Install Gradle

#### Option 1: Using Chocolatey (Recommended)
Open Command Prompt or PowerShell as Administrator and run:
```cmd
choco install gradle
```

#### Option 2: Using Scoop
If you have Scoop installed, run:
```cmd
scoop install gradle
```

#### Option 3: Manual Installation
1. Download Gradle from: https://gradle.org/releases/
2. Extract the ZIP file to a directory (e.g., `C:\gradle`)
3. Add to PATH:
   - Open System Properties → Advanced → Environment Variables
   - Add `C:\gradle\bin` to the PATH variable
   - Or add this to your PATH: `C:\gradle\bin`

### Step 2: Verify Installation
Open Command Prompt and run:
```cmd
gradle --version
```

Expected output:
```
------------------------------------------------------------
Gradle 8.5
------------------------------------------------------------

Build time:   2023-11-28 08:20:44 UTC
Revision:    963f41c2c2dd9d1f1f4b4c5c5c5c5c5c5c5c5c5c5

Kotlin:       1.9.21
Groovy:       3.0.17
Ant:          Apache Ant(TM) version 1.10.14
JVM:          17.0.9 (Oracle Corporation 17.0.9+9-LTS-21)
OS:           Windows 10 10.0 x86_64
```

## Simple Example: Basic Java Project

Let's start with the simplest possible Gradle project - a basic Java application.

### Step 1: Create Project Structure

Open Command Prompt and create a new directory:
```cmd
mkdir gradle-demo
cd gradle-demo
```

Create the following directory structure:
```
gradle-demo\
├── src\
│   ├── main\
│   │   ├── java\
│   │   │   └── com\
│   │   │       └── example\
│   │   │           └── HelloWorld.java
│   │   └── resources\
│   └── test\
│       ├── java\
│       │   └── com\
│       │       └── example\
│       │           └── HelloWorldTest.java
│       └── resources\
├── build.gradle
└── settings.gradle
```

**Why this structure?**
- `src/main/java`: Contains your application source code
- `src/main/resources`: Contains configuration files, properties, etc.
- `src/test/java`: Contains test code
- `src/test/resources`: Contains test-specific resources
- `build.gradle`: Defines how to build your project (dependencies, tasks, etc.)
- `settings.gradle`: Defines project name and structure

You can create this structure using these commands:
```cmd
mkdir src\main\java\com\example
mkdir src\main\resources
mkdir src\test\java\com\example
mkdir src\test\resources
```

### Step 2: Create the Main Java Class

Create `src\main\java\com\example\HelloWorld.java`:

```java
package com.example;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, Gradle World!");
        
        Calculator calc = new Calculator();
        int result = calc.add(5, 3);
        System.out.println("5 + 3 = " + result);
    }
}

class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
    
    public int subtract(int a, int b) {
        return a - b;
    }
}
```

### Step 3: Create Basic build.gradle

**Why do we need build.gradle?**
This file tells Gradle:
- What type of project this is (Java application)
- Where to find dependencies (Maven Central)
- What dependencies to include
- How to compile and run the code
- What tasks are available

Create `build.gradle`:

```groovy
plugins {
    id 'java'           // Enables Java compilation, testing, and packaging
    id 'application'    // Enables running the application with 'gradle run'
}

repositories {
    mavenCentral()      // Where to download dependencies from
}

dependencies {
    // No external dependencies for this simple example
}

java {
    sourceCompatibility = JavaVersion.VERSION_17    // Java version for source code
    targetCompatibility = JavaVersion.VERSION_17    // Java version for compiled bytecode
}

application {
    mainClass = 'com.example.HelloWorld'    // Which class contains the main method
}

// Custom task to print project info
task printProjectInfo {
    doLast {
        println "Project: ${project.name}"
        println "Version: ${project.version}"
        println "Java Version: ${java.sourceCompatibility}"
    }
}
```

### Step 4: Create settings.gradle

**Why do we need settings.gradle?**
This file tells Gradle:
- What the project is called
- Whether this is a single project or multi-project build
- Which modules are included (for multi-project builds)

Create `settings.gradle`:

```groovy
rootProject.name = 'gradle-demo'    // Sets the project name
```

### Step 5: Run the Project

Open Command Prompt in the project directory and run:
```cmd
# Build the project
gradle build

# Run the application
gradle run

# Clean the build
gradle clean

# Run custom task
gradle printProjectInfo
```

**What each command does:**
- `gradle build`: Compiles code, runs tests, creates JAR file
- `gradle run`: Compiles and runs the application
- `gradle clean`: Removes build artifacts
- `gradle printProjectInfo`: Runs our custom task

### Expected Output:
```
> Task :run
Hello, Gradle World!
5 + 3 = 8

BUILD SUCCESSFUL in 2s
```

## Enhanced Example: Project with Dependencies

Now let's create a more advanced example that uses external dependencies.

### Step 1: Create Enhanced Project Structure

```
gradle-advanced\
├── src\
│   ├── main\
│   │   ├── java\
│   │   │   └── com\
│   │   │       └── example\
│   │   │           ├── Main.java
│   │   │           ├── model\
│   │   │           │   └── User.java
│   │   │           └── service\
│   │   │               └── UserService.java
│   │   └── resources\
│   │       └── application.properties
│   └── test\
│       ├── java\
│       │   └── com\
│       │       └── example\
│       │           └── UserServiceTest.java
│       └── resources\
├── build.gradle
└── settings.gradle
```

Create the directory structure:
```cmd
mkdir gradle-advanced
cd gradle-advanced
mkdir src\main\java\com\example\model
mkdir src\main\java\com\example\service
mkdir src\main\resources
mkdir src\test\java\com\example
mkdir src\test\resources
```

### Step 2: Create Enhanced Java Classes

Create `src\main\java\com\example\model\User.java`:

```java
package com.example.model;

import java.time.LocalDateTime;

public class User {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    
    public User(Long id, String name, String email, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }
    
    // Getters and setters
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
        return String.format("User{id=%d, name='%s', email='%s', createdAt=%s}", 
            id, name, email, createdAt);
    }
}
```

Create `src\main\java\com\example\service\UserService.java`:

```java
package com.example.service;

import com.example.model.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private List<User> users = new ArrayList<>();
    private Long nextId = 1L;
    
    public User createUser(String name, String email) {
        User user = new User(nextId++, name, email, LocalDateTime.now());
        users.add(user);
        return user;
    }
    
    public Optional<User> findUserById(Long id) {
        return users.stream()
                   .filter(user -> user.getId().equals(id))
                   .findFirst();
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
    
    public boolean deleteUser(Long id) {
        return users.removeIf(user -> user.getId().equals(id));
    }
    
    public Optional<User> updateUser(Long id, String name, String email) {
        return findUserById(id).map(user -> {
            user.setName(name);
            user.setEmail(email);
            return user;
        });
    }
}
```

Create `src\main\java\com\example\Main.java`:

```java
package com.example;

import com.example.model.User;
import com.example.service.UserService;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        
        // Create some users
        User user1 = userService.createUser("Arjun", "arjun@example.com");
        User user2 = userService.createUser("Lakshmi", "lakshmi@example.com");
        User user3 = userService.createUser("Karthik", "karthik@example.com");
        
        System.out.println("Created users:");
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user3);
        
        // Find user by ID
        userService.findUserById(2L).ifPresent(user -> {
            System.out.println("\nFound user: " + user);
        });
        
        // Update user
        userService.updateUser(1L, "Arjun Reddy", "arjun.reddy@example.com")
                  .ifPresent(user -> {
                      System.out.println("\nUpdated user: " + user);
                  });
        
        // Get all users
        List<User> allUsers = userService.getAllUsers();
        System.out.println("\nAll users:");
        allUsers.forEach(System.out::println);
        
        // Delete user
        boolean deleted = userService.deleteUser(3L);
        System.out.println("\nUser 3 deleted: " + deleted);
        
        System.out.println("\nRemaining users:");
        userService.getAllUsers().forEach(System.out::println);
    }
}
```

### Step 3: Create Enhanced build.gradle

Create `build.gradle`:

```groovy
plugins {
    id 'java'
    id 'application'
    id 'jacoco'  // For code coverage
}

repositories {
    mavenCentral()
}

dependencies {
    // JUnit for testing
    testImplementation 'org.junit.jupiter:junit-jupiter:5.10.0'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
    
    // Logging
    implementation 'org.slf4j:slf4j-api:2.0.9'
    implementation 'ch.qos.logback:logback-classic:1.4.11'
    
    // JSON processing (optional)
    implementation 'com.fasterxml.jackson.core:jackson-databind:2.15.2'
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application {
    mainClass = 'com.example.Main'
}

// Test configuration
test {
    useJUnitPlatform()
    testLogging {
        events "passed", "skipped", "failed"
    }
}

// Code coverage
jacocoTestReport {
    reports {
        xml.required = true
        html.required = true
    }
}

// Custom tasks
task printDependencies {
    doLast {
        println "Project dependencies:"
        configurations.implementation.allDependencies.each { dep ->
            println "  - ${dep.group}:${dep.name}:${dep.version}"
        }
    }
}

task createJar(type: Jar) {
    archiveClassifier = 'executable'
    manifest {
        attributes 'Main-Class': 'com.example.Main'
    }
    from sourceSets.main.output
    dependsOn classes
}
```

### Step 4: Create Test Class

Create `src\test\java\com\example\UserServiceTest.java`:

```java
package com.example;

import com.example.model.User;
import com.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private UserService userService;
    
    @BeforeEach
    void setUp() {
        userService = new UserService();
    }
    
    @Test
    void testCreateUser() {
        User user = userService.createUser("Test User", "test@example.com");
        
        assertNotNull(user);
        assertEquals("Test User", user.getName());
        assertEquals("test@example.com", user.getEmail());
        assertNotNull(user.getCreatedAt());
    }
    
    @Test
    void testFindUserById() {
        User createdUser = userService.createUser("Test User", "test@example.com");
        var foundUser = userService.findUserById(createdUser.getId());
        
        assertTrue(foundUser.isPresent());
        assertEquals(createdUser.getId(), foundUser.get().getId());
    }
    
    @Test
    void testDeleteUser() {
        User user = userService.createUser("Test User", "test@example.com");
        boolean deleted = userService.deleteUser(user.getId());
        
        assertTrue(deleted);
        assertTrue(userService.findUserById(user.getId()).isEmpty());
    }
}
```

### Step 5: Run the Enhanced Project

Open Command Prompt in the project directory and run:
```cmd
# Build the project
gradle build

# Run the application
gradle run

# Run tests
gradle test

# Generate code coverage report
gradle jacocoTestReport

# Create executable JAR
gradle createJar

# List all available tasks
gradle tasks
```

## Gradle Wrapper for Windows

The Gradle Wrapper is a script that ensures everyone working on a project uses the same version of Gradle.

### Step 1: Generate Gradle Wrapper

Open Command Prompt in your project directory and run:
```cmd
gradle wrapper
```

This creates:
- `gradlew.bat` (Windows batch file)
- `gradle\wrapper\gradle-wrapper.properties`
- `gradle\wrapper\gradle-wrapper.jar`

### Step 2: Use Gradle Wrapper

```cmd
# Instead of 'gradle build', use:
gradlew.bat build

# Or simply:
gradlew build
```

### Step 3: Update Wrapper Version

```cmd
gradlew wrapper --gradle-version 8.5
```

## Common Gradle Tasks for Windows

### Basic Tasks
```cmd
# Build the project
gradlew build

# Clean build directory
gradlew clean

# Run tests
gradlew test

# Run application
gradlew run

# Show project info
gradlew properties

# List all tasks
gradlew tasks
```

### Advanced Tasks
```cmd
# Generate IDE files
gradlew eclipse
gradlew idea

# Generate documentation
gradlew javadoc

# Create distribution
gradlew distZip
gradlew distTar

# Run with debug info
gradlew build --debug

# Run specific task
gradlew test --tests "*UserServiceTest*"
```

## Best Practices for Windows

1. **Use Gradle Wrapper**: Ensures consistent Gradle versions across team
2. **Use Windows Path Separators**: Use backslashes (`\`) in file paths
3. **Set JAVA_HOME**: Ensure JAVA_HOME environment variable is set correctly
4. **Use Command Prompt or PowerShell**: Avoid Git Bash for Gradle commands
5. **Organize Dependencies**: Group dependencies logically
6. **Use Version Catalogs**: Centralize dependency versions
7. **Configure Proper Java Version**: Set source and target compatibility
8. **Add Meaningful Tasks**: Create custom tasks for common operations
9. **Use Appropriate Plugins**: Choose plugins that fit your project needs
10. **Optimize Build Performance**: Use incremental builds and parallel execution


## Troubleshooting Common Windows Issues

### Build Performance
```cmd
# Enable build cache
gradlew build --build-cache

# Enable parallel execution
gradlew build --parallel

# Show build scan
gradlew build --scan
```

### Dependency Issues
```cmd
# Show dependency tree
gradlew dependencies

# Show dependency insight
gradlew dependencyInsight --dependency junit

# Refresh dependencies
gradlew build --refresh-dependencies
```

### Memory Issues
```cmd
# Increase memory
gradlew build -Dorg.gradle.jvmargs="-Xmx2048m"
```

### Path Issues
```cmd
# Use Windows path separators in build files
# Correct: src\main\java
# Incorrect: src/main/java (in some contexts)
```

### Java Version Issues
```cmd
# Check Java version
java -version

# Set JAVA_HOME if needed
set JAVA_HOME=C:\Program Files\Java\jdk-17
```

Remember to:
- Always use the Gradle Wrapper (`gradlew.bat`) for consistency
- Use Windows path separators (`\`) in file operations
- Set JAVA_HOME environment variable correctly
- Use Command Prompt or PowerShell for Gradle commands
- Keep dependencies up to date
- Use appropriate plugins for your project type
- Configure proper Java versions
- Add meaningful custom tasks
- Test your build configuration
- Document complex build logic
- Use build scans for performance analysis 
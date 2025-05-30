# Gradle DSL (Domain Specific Language) Tutorial

## What is Gradle DSL?

Gradle DSL (Domain Specific Language) is a specialized language for configuring Gradle builds. It's based on Groovy or Kotlin and provides a declarative way to define build logic, dependencies, and project configuration.

### Key DSL Concepts:
1. **Plugins**: Extend Gradle's functionality
2. **Dependencies**: Define project dependencies
3. **Tasks**: Define build actions
4. **Properties**: Configure project settings

## Plugins

Plugins are the foundation of Gradle builds. They add functionality and define the project type.

### Core Plugins

#### Java Plugin
The Java plugin adds Java compilation, testing, and packaging capabilities.

```groovy
plugins {
    id 'java'
}

// This plugin automatically adds:
// - compileJava task
// - compileTestJava task
// - test task
// - jar task
// - clean task
```

#### Application Plugin
The Application plugin creates executable applications with a distribution.

```groovy
plugins {
    id 'java'
    id 'application'
}

application {
    mainClass = 'com.example.Main'
}

// This adds:
// - run task (executes the application)
// - distZip task (creates ZIP distribution)
```

## Java Configuration

### Source and Target Compatibility

```groovy
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
```

**What this does:**
- `sourceCompatibility`: Sets the Java version for source code
- `targetCompatibility`: Sets the Java version for compiled bytecode
- Both should typically be the same value

### Available Java Versions
```groovy
// Common Java versions
JavaVersion.VERSION_8    // Java 8
JavaVersion.VERSION_11   // Java 11
JavaVersion.VERSION_17   // Java 17
JavaVersion.VERSION_21   // Java 21
```

## Dependencies

### Dependency Configurations

#### Implementation Dependencies
```groovy
dependencies {
    // Runtime dependencies (not exposed to consumers)
    implementation 'org.springframework.boot:spring-boot-starter-web:3.2.0'
    implementation 'com.fasterxml.jackson.core:jackson-databind:2.15.2'
    implementation 'org.slf4j:slf4j-api:2.0.9'
}
```

#### Test Dependencies
```groovy
dependencies {
    // Test-only dependencies
    testImplementation 'org.junit.jupiter:junit-jupiter:5.10.0'
    testImplementation 'org.mockito:mockito-core:5.7.0'
}
```

### Dependency Types

#### External Dependencies
```groovy
dependencies {
    // Group:Name:Version format
    implementation 'org.springframework:spring-core:6.1.0'
}
```

#### Project Dependencies
```groovy
dependencies {
    // Dependencies on other modules in the same project
    implementation project(':core')
    implementation project(':utils')
}
```

## Repositories

### Repository Configuration
```groovy
repositories {
    // Maven Central (default)
    mavenCentral()
    
    // Maven Local
    mavenLocal()
    
    // Custom Maven repository
    maven {
        url 'https://repo.example.com/maven'
    }
}
```

## Tasks

### Built-in Tasks
```groovy
// Java plugin tasks
tasks.named('compileJava') {
    options.compilerArgs += ['-Xlint:unchecked']
}

tasks.named('test') {
    useJUnitPlatform()
    testLogging {
        events "passed", "skipped", "failed"
    }
}
```

### Custom Tasks
```groovy
// Simple task
task hello {
    doLast {
        println 'Hello, Gradle!'
    }
}

// Task with dependencies
task buildReport {
    dependsOn 'build'
    doLast {
        println "Build completed for ${project.name}"
    }
}

// Task with configuration
task copyDocs(type: Copy) {
    from 'src/docs'
    into 'build/docs'
    include '**/*.md'
}
```

## Properties and Variables

### Project Properties
```groovy
// Define project properties
group = 'com.example'
version = '1.0.0'
description = 'A sample Gradle project'

// Access properties
println "Project: ${project.name}"
println "Version: ${project.version}"
println "Group: ${project.group}"
```

### Extra Properties
```groovy
// Set extra properties
ext {
    springVersion = '3.2.0'
    junitVersion = '5.10.0'
}

// Use in dependencies
dependencies {
    implementation "org.springframework.boot:spring-boot-starter-web:${springVersion}"
    testImplementation "org.junit.jupiter:junit-jupiter:${junitVersion}"
}
```

## Source Sets

### Source Set Configuration
```groovy
sourceSets {
    main {
        java {
            srcDirs = ['src/main/java']
        }
        resources {
            srcDirs = ['src/main/resources']
        }
    }
    
    test {
        java {
            srcDirs = ['src/test/java']
        }
        resources {
            srcDirs = ['src/test/resources']
        }
    }
}
```

## Testing Configuration

### JUnit 5 Configuration
```groovy
test {
    useJUnitPlatform()
    
    // Test logging
    testLogging {
        events "passed", "skipped", "failed"
        showStandardStreams = true
    }
}
```

### Test Dependencies
```groovy
dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter:5.10.0'
    testImplementation 'org.mockito:mockito-core:5.7.0'
}
```

## Complete Example

Here's a complete `build.gradle` file showing all core concepts:

```groovy
plugins {
    id 'java'
    id 'application'
}

group = 'com.example'
version = '1.0.0'

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.slf4j:slf4j-api:2.0.9'
    testImplementation 'org.junit.jupiter:junit-jupiter:5.10.0'
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application {
    mainClass = 'com.example.Main'
}

test {
    useJUnitPlatform()
    testLogging {
        events "passed", "skipped", "failed"
    }
}

task printProjectInfo {
    doLast {
        println "Project: ${project.name}"
        println "Version: ${project.version}"
        println "Java Version: ${java.sourceCompatibility}"
    }
}
```

## Best Practices

### 1. Use Version Variables
```groovy
ext {
    springVersion = '3.2.0'
    junitVersion = '5.10.0'
}

dependencies {
    implementation "org.springframework.boot:spring-boot-starter-web:${springVersion}"
    testImplementation "org.junit.jupiter:junit-jupiter:${junitVersion}"
}
```

### 2. Organize Dependencies
```groovy
dependencies {
    // Core dependencies
    implementation 'org.springframework.boot:spring-boot-starter-web'
    
    // Database
    implementation 'org.postgresql:postgresql'
    
    // Testing
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

### 3. Use Meaningful Task Names
```groovy
task generateDocs {
    group = 'documentation'
    description = 'Generate project documentation'
    
    doLast {
        println 'Generating documentation...'
    }
}
```

### 4. Configure Proper Java Versions
```groovy
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
```

Remember to:
- Use appropriate plugins for your project type
- Configure proper Java versions
- Organize dependencies logically
- Use meaningful task names and descriptions
- Keep dependencies up to date
- Follow Gradle conventions 
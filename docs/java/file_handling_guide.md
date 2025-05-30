# Java File Handling: A Comprehensive Guide

Java provides robust APIs for file handling and directory operations. This training material covers both traditional I/O (java.io) and the modern NIO.2 (java.nio) approaches for working with files and directories.

## Class Hierarchy Overview

### 1. Traditional I/O (java.io) Class Hierarchy

```
java.io
├── File
├── InputStream (abstract)
│   ├── FileInputStream
│   ├── FilterInputStream
│   │   ├── BufferedInputStream
│   │   └── DataInputStream
│   └── ObjectInputStream
├── OutputStream (abstract)
│   ├── FileOutputStream
│   ├── FilterOutputStream
│   │   ├── BufferedOutputStream
│   │   └── DataOutputStream
│   └── ObjectOutputStream
├── Reader (abstract)
│   ├── BufferedReader
│   ├── InputStreamReader
│   │   └── FileReader
│   └── StringReader
└── Writer (abstract)
    ├── BufferedWriter
    ├── OutputStreamWriter
    │   └── FileWriter
    └── StringWriter
```

Key Concepts:
- `InputStream`/`OutputStream`: For byte-based I/O
- `Reader`/`Writer`: For character-based I/O
- `Buffered*` classes: Add buffering capability
- `File`: Represents file and directory paths

### 2. Modern NIO.2 (java.nio) Class Hierarchy

```
java.nio.file
├── Path (interface)
│   └── Files (utility class)
├── FileSystem
│   └── FileSystems (factory)
├── WatchService
└── FileStore

java.nio.channels
├── Channel (interface)
├── FileChannel
├── SeekableByteChannel
└── AsynchronousFileChannel

java.nio.file.attribute
├── BasicFileAttributes
├── PosixFileAttributes
└── FileAttribute
```

Key Concepts:
- `Path`: Modern replacement for `File`
- `Files`: Utility class for file operations
- `FileSystem`: Abstract file system operations
- `Channel`: Direct I/O operations
- `WatchService`: Directory monitoring

### 3. Comparison of Approaches

```
Traditional I/O         Modern NIO.2
---------------        -------------
File                → Path
FileInputStream     → Files.newInputStream()
FileOutputStream    → Files.newOutputStream()
FileReader         → Files.newBufferedReader()
FileWriter         → Files.newBufferedWriter()
RandomAccessFile   → FileChannel
```

Benefits of NIO.2:
- More intuitive API
- Better exception handling
- Symbolic link support
- File system notifications
- Asynchronous I/O operations

## 1. Traditional File Handling (java.io)

The java.io package provides the classic approach to file handling through various classes like File, FileReader, and FileWriter.

### 1.1 Reading Files

#### Using FileReader and BufferedReader
```java
// Basic file reading with FileReader
try (FileReader reader = new FileReader("input.txt")) {
    int character;
    while ((character = reader.read()) != -1) {
        System.out.print((char) character);
    }
} catch (IOException e) {
    e.printStackTrace();
}

// Efficient reading with BufferedReader
try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
} catch (IOException e) {
    e.printStackTrace();
}
```

The BufferedReader approach is more efficient as it:
- Reduces disk access by buffering data
- Provides line-by-line reading capability
- Improves performance for large files

### 1.2 Writing Files

#### Using FileWriter and BufferedWriter
```java
// Basic file writing with FileWriter
try (FileWriter writer = new FileWriter("output.txt")) {
    writer.write("Hello, World!");
} catch (IOException e) {
    e.printStackTrace();
}

// Efficient writing with BufferedWriter
try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
    writer.write("Hello, World!");
    writer.newLine(); // Platform-independent line separator
    writer.write("Another line");
} catch (IOException e) {
    e.printStackTrace();
}
```

BufferedWriter advantages:
- Reduces disk writes through buffering
- Provides platform-independent line separators
- Better performance for frequent writes

## 2. Modern File Handling (java.nio)

The NIO.2 API (java.nio.file) introduced in Java 7 provides more powerful and flexible ways to handle files.

### 2.1 Using Path and Files

```java
import java.nio.file.*;

// Reading a file
try {
    List<String> lines = Files.readAllLines(Paths.get("input.txt"));
    lines.forEach(System.out::println);
} catch (IOException e) {
    e.printStackTrace();
}

// Writing a file
try {
    List<String> lines = Arrays.asList("Line 1", "Line 2");
    Files.write(Paths.get("output.txt"), lines);
} catch (IOException e) {
    e.printStackTrace();
}

// Reading large files with Stream API
try (Stream<String> lines = Files.lines(Paths.get("large-file.txt"))) {
    lines.forEach(System.out::println);
} catch (IOException e) {
    e.printStackTrace();
}
```

### 2.2 File Operations with NIO.2

```java
Path path = Paths.get("example.txt");

// File existence and properties
boolean exists = Files.exists(path);
boolean isDirectory = Files.isDirectory(path);
boolean isRegularFile = Files.isRegularFile(path);

// File attributes
BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
System.out.println("Creation time: " + attrs.creationTime());
System.out.println("Last modified: " + attrs.lastModifiedTime());
System.out.println("Size: " + attrs.size());

// Copy files
Path source = Paths.get("source.txt");
Path target = Paths.get("target.txt");
Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

// Move/Rename files
Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);

// Delete files
Files.delete(path);
// or safely delete
Files.deleteIfExists(path);
```

## 3. Directory Operations

### 3.1 Directory Creation and Listing

```java
// Create directories
Path dir = Paths.get("my/new/directory");
Files.createDirectories(dir);

// List directory contents
try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
    for (Path entry : stream) {
        System.out.println(entry.getFileName());
    }
}

// Filter files in directory
try (DirectoryStream<Path> stream = 
        Files.newDirectoryStream(dir, "*.{txt,doc,pdf}")) {
    for (Path entry : stream) {
        System.out.println(entry.getFileName());
    }
}
```

### 3.2 Directory Walking
```java
// Walk directory tree
try (Stream<Path> walk = Files.walk(Paths.get("root/directory"))) {
    walk.filter(Files::isRegularFile)
        .forEach(System.out::println);
}

// Find files
try (Stream<Path> files = Files.find(Paths.get("root/directory"), 
        Integer.MAX_VALUE,
        (path, attrs) -> attrs.isRegularFile() && 
                         path.toString().endsWith(".txt"))) {
    files.forEach(System.out::println);
}
```

## 4. Best Practices

### 4.1 Resource Management
```java
// Always use try-with-resources
try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"));
     BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
    // File operations
} catch (IOException e) {
    // Exception handling
}
```

### 4.2 Exception Handling
```java
public void handleFile(Path path) {
    try {
        // File operations
    } catch (NoSuchFileException e) {
        // Handle missing file
        logger.error("File not found: " + path, e);
    } catch (AccessDeniedException e) {
        // Handle permission issues
        logger.error("Access denied: " + path, e);
    } catch (IOException e) {
        // Handle other I/O issues
        logger.error("Error processing file: " + path, e);
    }
}
```

### 4.3 Performance Considerations

1. **Buffered Operations**
```java
// Preferred: Use buffered streams for better performance
try (BufferedReader reader = new BufferedReader(
        new FileReader("file.txt"), 8192)) {
    // Operations
}
```

2. **Stream Processing for Large Files**
```java
// Process large files line by line
try (Stream<String> lines = Files.lines(Paths.get("large-file.txt"))) {
    lines.filter(line -> line.contains("keyword"))
         .forEach(System.out::println);
}
```

### 4.4 Security Best Practices

```java
public class SecureFileOperations {
    private static final Path SAFE_DIRECTORY = Paths.get("/safe/directory");

    public void validatePath(Path path) {
        Path normalized = path.normalize();
        if (!normalized.startsWith(SAFE_DIRECTORY)) {
            throw new SecurityException("Access denied to path: " + path);
        }
    }

    public void writeFile(Path path, byte[] content) {
        validatePath(path);
        try {
            Files.write(path, content, 
                StandardOpenOption.CREATE, 
                StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file", e);
        }
    }
}
```

## 5. Common Patterns and Use Cases

### 5.1 File Copy with Progress
```java
public class FileCopyWithProgress {
    public void copyWithProgress(Path source, Path target) 
            throws IOException {
        long size = Files.size(source);
        long[] progress = new long[1];
        
        try (InputStream in = Files.newInputStream(source);
             OutputStream out = Files.newOutputStream(target)) {
            
            byte[] buffer = new byte[8192];
            int n;
            while ((n = in.read(buffer)) > 0) {
                out.write(buffer, 0, n);
                progress[0] += n;
                
                // Report progress
                int percent = (int) (progress[0] * 100 / size);
                System.out.printf("\rProgress: %d%%", percent);
            }
        }
    }
}
```

### 5.2 Directory Backup
```java
public class DirectoryBackup {
    public void backup(Path sourceDir, Path backupDir) 
            throws IOException {
        Files.walk(sourceDir)
             .forEach(source -> {
                 Path target = backupDir.resolve(
                     sourceDir.relativize(source));
                 try {
                     Files.copy(source, target,
                         StandardCopyOption.REPLACE_EXISTING);
                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }
             });
    }
}
```

## Summary

Modern Java file handling offers:
- Robust APIs for file operations
- Efficient streaming for large files
- Comprehensive directory management
- Strong exception handling
- Security features

Choose between:
- Traditional I/O (java.io) for simple operations
- NIO.2 (java.nio) for advanced features and better performance

Remember to:
- Always use try-with-resources
- Implement proper error handling
- Consider security implications
- Use buffered operations for better performance
- Handle large files with streams 
# JVM Architecture and Monitoring with VisualVM Tutorial

## What is the JVM?

The Java Virtual Machine (JVM) is the runtime environment that executes Java bytecode. It's a virtual machine that provides a platform-independent way of executing Java programs, making Java "Write Once, Run Anywhere" possible.

### Key Concepts:
1. **Platform Independence**: Java bytecode runs on any platform with a JVM
2. **Memory Management**: Automatic memory allocation and garbage collection
3. **Security**: Sandboxed execution environment
4. **Performance**: Just-In-Time (JIT) compilation for optimized execution

## Understanding the Problem JVM Solves

### Before JVM: Platform-Specific Compilation

Traditional compiled languages require separate compilation for each platform:

```java
// C/C++ approach - platform specific
// Windows: gcc program.c -o program.exe
// Linux: gcc program.c -o program
// macOS: gcc program.c -o program

// Each platform needs its own executable
// Windows executable won't run on Linux
// Linux executable won't run on macOS
```

### With JVM: Platform Independence

```java
// Java approach - platform independent
// Compile once: javac Program.java -> Program.class
// Run anywhere with JVM:
// Windows: java Program
// Linux: java Program  
// macOS: java Program

// Same .class file runs on all platforms
```

## JVM Architecture Deep Dive

### 1. JVM Memory Structure

The JVM memory is divided into several key areas:

```
┌─────────────────────────────────────────────────────────────┐
│                        JVM Memory                           │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │   Method    │  │    Heap     │  │      Native         │  │
│  │   Area      │  │             │  │      Memory         │  │
│  │             │  │  ┌─────────┐ │  │                     │  │
│  │ • Class     │  │  │  Young  │ │  │ • Direct Memory     │  │
│  │   metadata  │  │  │  Gen    │ │  │ • Native Libraries  │  │
│  │ • Static    │  │  │         │ │  │ • JNI Code          │  │
│  │   variables │  │  │ Eden    │ │  │                     │  │
│  │ • Method    │  │  │ S0/S1   │ │  │                     │  │
│  │   code      │  │  └─────────┘ │  │                     │  │
│  │             │  │  ┌─────────┐ │  │                     │  │
│  │             │  │  │  Old    │ │  │                     │  │
│  │             │  │  │  Gen    │ │  │                     │  │
│  │             │  │  └─────────┘ │  │                     │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
│                                                             │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │   Stack     │  │   PC        │  │   Native Method     │  │
│  │   Memory    │  │   Register  │  │   Stack             │  │
│  │             │  │             │  │                     │  │
│  │ • Method    │  │ • Current   │  │ • Native method     │  │
│  │   frames    │  │   instruction│  │   calls             │  │
│  │ • Local     │  │   pointer   │  │                     │  │
│  │   variables │  │             │  │                     │  │
│  │ • Operand   │  │             │  │                     │  │
│  │   stack     │  │             │  │                     │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

### 2. Detailed Memory Areas

#### Method Area (Metaspace in Java 8+)
```java
// Method Area stores class-level information
public class MemoryExample {
    // Static variables stored in Method Area
    public static final String CONSTANT = "IMMUTABLE";
    public static int counter = 0;
    
    // Class metadata stored in Method Area
    public void method1() {
        // Method bytecode stored in Method Area
    }
    
    public void method2() {
        // Method bytecode stored in Method Area
    }
}
```

#### Heap Memory
```java
// Heap stores all object instances
public class HeapExample {
    public static void main(String[] args) {
        // Objects created on heap
        String str1 = new String("Hello");           // Heap
        Integer num = new Integer(42);               // Heap
        List<String> list = new ArrayList<>();       // Heap
        list.add("item");                           // Heap
        
        // Primitives stored on stack
        int primitive = 100;                         // Stack
        double price = 99.99;                       // Stack
    }
}
```

#### Stack Memory
```java
// Stack stores method frames and local variables
public class StackExample {
    public void methodA() {
        int localVar1 = 10;        // Stack
        String localVar2 = "test"; // Stack (reference)
        methodB(localVar1);        // Stack frame for methodB
    }
    
    public void methodB(int param) {
        double result = param * 2.5;  // Stack
        methodC(result);              // Stack frame for methodC
    }
    
    public void methodC(double value) {
        // Stack frame with value parameter
        System.out.println(value);
    }
}
```

### 3. Memory Allocation Example

```java
public class MemoryAllocationDemo {
    // Static variable - Method Area
    public static final String APP_NAME = "Memory Demo";
    
    // Instance variable - Heap (when object created)
    private String instanceData;
    
    public void demonstrateMemoryAllocation() {
        // Local variables - Stack
        int localInt = 42;
        String localString = "Hello";
        
        // Object creation - Heap
        List<String> list = new ArrayList<>();
        list.add("Item 1");
        list.add("Item 2");
        
        // Method call - creates new stack frame
        processData(list);
    }
    
    private void processData(List<String> data) {
        // New stack frame created
        int count = data.size();
        System.out.println("Processing " + count + " items");
    }
}
```

## Class Loading and Bytecode Execution

### 1. Class Loading Process

The JVM follows a specific process to load and execute classes:

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Loading       │    │   Linking       │    │  Initialization │
│                 │    │                 │    │                 │
│ • Find .class   │    │ • Verification  │    │ • Static        │
│   file          │    │ • Preparation   │    │   initialization│
│ • Load into     │    │ • Resolution    │    │ • Static blocks │
│   Method Area   │    │                 │    │ • Static        │
│                 │    │                 │    │   variables     │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### 2. Class Loader Hierarchy

```java
// Class loader hierarchy
public class ClassLoaderDemo {
    public static void main(String[] args) {
        // Bootstrap Class Loader (JVM internal)
        Class<?> stringClass = String.class;
        System.out.println("String class loader: " + 
            (stringClass.getClassLoader() == null ? "Bootstrap" : stringClass.getClassLoader()));
        
        // Extension Class Loader (deprecated in Java 9+)
        // Platform Class Loader (Java 9+)
        
        // Application Class Loader
        Class<?> thisClass = ClassLoaderDemo.class;
        System.out.println("This class loader: " + thisClass.getClassLoader());
        
        // Custom Class Loader
        CustomClassLoader customLoader = new CustomClassLoader();
        System.out.println("Custom loader parent: " + customLoader.getParent());
    }
}

// Custom class loader example
class CustomClassLoader extends ClassLoader {
    public CustomClassLoader() {
        super(); // Parent is Application Class Loader
    }
    
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // Custom class loading logic
        throw new ClassNotFoundException(name);
    }
}
```

### 3. Bytecode Execution Example

```java
// Simple Java code
public class BytecodeExample {
    public int calculate(int a, int b) {
        int result = a + b;
        return result * 2;
    }
}

// Equivalent bytecode (simplified)
/*
  public int calculate(int, int);
    Code:
       0: iload_1        // Load first parameter (a)
       1: iload_2        // Load second parameter (b)
       2: iadd           // Add them
       3: istore_3       // Store result in local variable
       4: iload_3        // Load result
       5: iconst_2       // Load constant 2
       6: imul           // Multiply
       7: ireturn        // Return result
*/
```

### 4. Class Loading in Action

```java
public class ClassLoadingDemo {
    static {
        System.out.println("ClassLoadingDemo static block executed");
    }
    
    public static void main(String[] args) {
        System.out.println("Main method started");
        
        // Class loaded when first referenced
        System.out.println("About to create MyClass instance");
        MyClass obj = new MyClass();
        
        System.out.println("MyClass instance created");
        obj.doSomething();
    }
}

class MyClass {
    static {
        System.out.println("MyClass static block executed");
    }
    
    public MyClass() {
        System.out.println("MyClass constructor executed");
    }
    
    public void doSomething() {
        System.out.println("MyClass method executed");
    }
}

// Output:
// ClassLoadingDemo static block executed
// Main method started
// About to create MyClass instance
// MyClass static block executed
// MyClass constructor executed
// MyClass instance created
// MyClass method executed
```

## Garbage Collection Basics

### 1. What is Garbage Collection?

Garbage Collection (GC) is the automatic memory management process that reclaims memory from objects that are no longer in use.

```java
public class GarbageCollectionDemo {
    public static void main(String[] args) {
        // Object created on heap
        String obj1 = new String("Object 1");
        
        // Reference to same object
        String obj2 = obj1;
        
        // obj1 now points to null, but obj2 still references the object
        obj1 = null;
        
        // Object is still reachable through obj2
        System.out.println(obj2); // "Object 1"
        
        // obj2 now points to null, object becomes unreachable
        obj2 = null;
        
        // Object is now eligible for garbage collection
        // GC will reclaim the memory when it runs
    }
}
```

### 2. Object Lifecycle

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   Created   │───▶│   Referenced│───▶│ Unreferenced│───▶│  Collected  │
│             │    │             │    │             │    │             │
│ • Allocated │    │ • In use    │    │ • No refs   │    │ • Memory    │
│   on heap   │    │ • Active    │    │ • Eligible  │    │   reclaimed │
│ • Referenced│    │ • Accessible│    │   for GC    │    │ • Finalized │
└─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘
```

### 3. GC Generations

```java
public class GenerationalGCDemo {
    public static void main(String[] args) {
        // Young Generation (Eden)
        for (int i = 0; i < 1000; i++) {
            String temp = new String("Temporary object " + i);
            // Most of these will be short-lived
        }
        
        // Some objects survive and move to Survivor space
        List<String> survivors = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            survivors.add("Survivor " + i);
        }
        
        // Long-lived objects eventually move to Old Generation
        List<String> longLived = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            longLived.add("Long-lived object " + i);
        }
        
        // Force garbage collection (for demonstration only)
        System.gc();
    }
}
```

### 4. Memory Leak Example

```java
public class MemoryLeakDemo {
    // Static list that keeps growing - potential memory leak
    private static final List<Object> cache = new ArrayList<>();
    
    public static void addToCache(Object obj) {
        cache.add(obj); // Objects never removed
    }
    
    public static void demonstrateLeak() {
        for (int i = 0; i < 1000000; i++) {
            // Creating objects that are cached but never used
            addToCache(new byte[1024]); // 1KB objects
        }
        System.out.println("Cache size: " + cache.size());
    }
    
    // Better approach with size limit
    private static final int MAX_CACHE_SIZE = 1000;
    
    public static void addToCacheSafely(Object obj) {
        if (cache.size() >= MAX_CACHE_SIZE) {
            cache.remove(0); // Remove oldest entry
        }
        cache.add(obj);
    }
}
```

### 5. Finalization Example

```java
public class FinalizationDemo {
    public static void main(String[] args) {
        Resource resource = new Resource();
        resource.use();
        
        // Resource becomes eligible for GC
        resource = null;
        
        // Force garbage collection
        System.gc();
        
        // Wait a bit for finalizer to run
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Resource {
    private boolean isClosed = false;
    
    public void use() {
        System.out.println("Resource is being used");
    }
    
    @Override
    protected void finalize() throws Throwable {
        if (!isClosed) {
            System.out.println("Resource is being finalized - cleanup performed");
            isClosed = true;
        }
        super.finalize();
    }
}
```

## Monitoring with VisualVM

### 1. Installing VisualVM

VisualVM is a visual tool that comes with the JDK and provides monitoring capabilities.

#### Installation Steps:
1. **Download**: VisualVM is included with JDK 8-11
2. **Launch**: Run `jvisualvm` from command line or find in JDK bin directory
3. **Alternative**: Download standalone from https://visualvm.github.io/

### 2. Basic VisualVM Usage

```java
// Sample application to monitor
public class VisualVMDemo {
    public static void main(String[] args) {
        System.out.println("Starting VisualVM Demo Application");
        System.out.println("PID: " + ProcessHandle.current().pid());
        
        // Create some memory pressure
        List<String> data = new ArrayList<>();
        
        for (int i = 0; i < 100000; i++) {
            data.add("String " + i);
            
            if (i % 10000 == 0) {
                System.out.println("Created " + i + " strings");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
        System.out.println("Application completed");
    }
}
```

### 3. Monitoring Memory Usage

```java
public class MemoryMonitoringDemo {
    private static final List<byte[]> memoryHogs = new ArrayList<>();
    
    public static void main(String[] args) {
        System.out.println("Memory Monitoring Demo Started");
        System.out.println("PID: " + ProcessHandle.current().pid());
        
        // Simulate memory growth
        for (int i = 0; i < 10; i++) {
            allocateMemory();
            printMemoryStats();
            
            try {
                Thread.sleep(2000); // 2 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // Simulate memory cleanup
        System.out.println("Cleaning up memory...");
        memoryHogs.clear();
        System.gc();
        printMemoryStats();
    }
    
    private static void allocateMemory() {
        // Allocate 10MB
        byte[] chunk = new byte[10 * 1024 * 1024];
        memoryHogs.add(chunk);
        System.out.println("Allocated 10MB chunk. Total chunks: " + memoryHogs.size());
    }
    
    private static void printMemoryStats() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        
        System.out.printf("Memory - Used: %d MB, Free: %d MB, Total: %d MB%n",
            usedMemory / (1024 * 1024),
            freeMemory / (1024 * 1024),
            totalMemory / (1024 * 1024));
    }
}
```

### 4. Thread Monitoring

```java
public class ThreadMonitoringDemo {
    public static void main(String[] args) {
        System.out.println("Thread Monitoring Demo Started");
        System.out.println("PID: " + ProcessHandle.current().pid());
        
        // Create multiple threads
        for (int i = 0; i < 5; i++) {
            final int threadId = i;
            Thread thread = new Thread(() -> {
                System.out.println("Thread " + threadId + " started");
                
                // Simulate work
                for (int j = 0; j < 10; j++) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("Thread " + threadId + " - iteration " + j);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                
                System.out.println("Thread " + threadId + " completed");
            }, "WorkerThread-" + i);
            
            thread.start();
        }
        
        // Main thread waits
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("All threads completed");
    }
}
```

### 5. CPU Profiling

```java
public class CPUProfilingDemo {
    public static void main(String[] args) {
        System.out.println("CPU Profiling Demo Started");
        System.out.println("PID: " + ProcessHandle.current().pid());
        
        // CPU-intensive operations
        for (int i = 0; i < 5; i++) {
            performCPUIntensiveTask();
            performIOTask();
        }
        
        System.out.println("CPU Profiling Demo Completed");
    }
    
    private static void performCPUIntensiveTask() {
        System.out.println("Starting CPU-intensive task...");
        long start = System.currentTimeMillis();
        
        // Simulate CPU-intensive work
        for (int i = 0; i < 1000000; i++) {
            Math.sqrt(i);
            Math.sin(i);
            Math.cos(i);
        }
        
        long end = System.currentTimeMillis();
        System.out.println("CPU-intensive task completed in " + (end - start) + "ms");
    }
    
    private static void performIOTask() {
        System.out.println("Starting I/O task...");
        long start = System.currentTimeMillis();
        
        // Simulate I/O work
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        long end = System.currentTimeMillis();
        System.out.println("I/O task completed in " + (end - start) + "ms");
    }
}
```

### 6. VisualVM Features Overview

#### Overview Tab
- **Memory**: Real-time heap usage graphs
- **Threads**: Active thread count and status
- **Classes**: Loaded class count
- **CPU**: CPU usage percentage

#### Monitor Tab
- **Memory**: Detailed memory usage with GC button
- **Threads**: Thread details with stack traces
- **Classes**: Class loading statistics

#### Threads Tab
- **Thread states**: Running, Sleeping, Waiting, etc.
- **Stack traces**: Current execution point
- **Thread dump**: Snapshot of all threads

#### Sampler Tab
- **CPU sampling**: Method-level CPU usage
- **Memory sampling**: Object allocation statistics

#### Profiler Tab
- **CPU profiling**: Detailed method execution times
- **Memory profiling**: Object allocation and retention

## Practical Examples

### 1. Memory Leak Detection

```java
public class MemoryLeakDetectionDemo {
    private static final Map<String, List<byte[]>> cache = new HashMap<>();
    
    public static void main(String[] args) {
        System.out.println("Memory Leak Detection Demo Started");
        System.out.println("PID: " + ProcessHandle.current().pid());
        
        // Simulate memory leak
        for (int i = 0; i < 20; i++) {
            addToCache("key" + i);
            printMemoryStats();
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // Demonstrate cleanup
        System.out.println("Cleaning up cache...");
        cache.clear();
        System.gc();
        printMemoryStats();
    }
    
    private static void addToCache(String key) {
        List<byte[]> data = new ArrayList<>();
        // Add 5MB of data per key
        for (int i = 0; i < 5; i++) {
            data.add(new byte[1024 * 1024]); // 1MB
        }
        cache.put(key, data);
        System.out.println("Added " + key + " to cache. Cache size: " + cache.size());
    }
    
    private static void printMemoryStats() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        System.out.printf("Used Memory: %d MB%n", usedMemory / (1024 * 1024));
    }
}
```

### 2. Thread Deadlock Detection

```java
public class DeadlockDetectionDemo {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();
    
    public static void main(String[] args) {
        System.out.println("Deadlock Detection Demo Started");
        System.out.println("PID: " + ProcessHandle.current().pid());
        
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 1: Acquired lock1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                System.out.println("Thread 1: Trying to acquire lock2");
                synchronized (lock2) {
                    System.out.println("Thread 1: Acquired lock2");
                }
            }
        }, "DeadlockThread-1");
        
        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Thread 2: Acquired lock2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                System.out.println("Thread 2: Trying to acquire lock1");
                synchronized (lock1) {
                    System.out.println("Thread 2: Acquired lock1");
                }
            }
        }, "DeadlockThread-2");
        
        thread1.start();
        thread2.start();
        
        // Let the deadlock occur
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Check VisualVM Threads tab for deadlock detection");
    }
}
```

### 3. GC Performance Analysis

```java
public class GCPerformanceDemo {
    public static void main(String[] args) {
        System.out.println("GC Performance Demo Started");
        System.out.println("PID: " + ProcessHandle.current().pid());
        
        // JVM arguments to try:
        // -Xms512m -Xmx1024m -XX:+UseG1GC
        // -Xms512m -Xmx1024m -XX:+UseParallelGC
        // -Xms512m -Xmx1024m -XX:+UseConcMarkSweepGC
        
        List<byte[]> objects = new ArrayList<>();
        
        for (int i = 0; i < 50; i++) {
            // Create objects of varying sizes
            int size = (i % 3 == 0) ? 1024 * 1024 : 1024; // 1MB or 1KB
            byte[] obj = new byte[size];
            objects.add(obj);
            
            if (i % 10 == 0) {
                System.out.println("Created " + i + " objects");
                printGCStats();
            }
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // Force garbage collection
        System.out.println("Forcing garbage collection...");
        System.gc();
        printGCStats();
    }
    
    private static void printGCStats() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        
        System.out.printf("Memory - Used: %d MB, Free: %d MB, Total: %d MB%n",
            usedMemory / (1024 * 1024),
            freeMemory / (1024 * 1024),
            totalMemory / (1024 * 1024));
    }
}
```

## Common JVM Tuning Parameters

### 1. Memory Settings

```bash
# Heap size settings
-Xms512m          # Initial heap size
-Xmx2048m         # Maximum heap size
-Xmn256m          # Young generation size

# Metaspace settings (Java 8+)
-XX:MetaspaceSize=64m
-XX:MaxMetaspaceSize=256m
```

### 2. Garbage Collector Settings

```bash
# G1GC (recommended for Java 9+)
-XX:+UseG1GC
-XX:MaxGCPauseMillis=200

# Parallel GC
-XX:+UseParallelGC
-XX:ParallelGCThreads=4

# CMS GC (deprecated in Java 9)
-XX:+UseConcMarkSweepGC
-XX:+UseParNewGC
```

### 3. Monitoring and Debugging

```bash
# GC logging
-XX:+PrintGCDetails
-XX:+PrintGCTimeStamps
-Xloggc:gc.log

# Heap dump on OOM
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=/path/to/dumps

# JMX monitoring
-Dcom.sun.management.jmxremote
-Dcom.sun.management.jmxremote.port=9999
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.ssl=false
```

## Best Practices

### 1. Memory Management

```java
public class MemoryBestPractices {
    
    // Good: Use try-with-resources for auto-closing
    public void readFileGood(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Bad: Manual resource management
    public void readFileBad(String filename) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // Good: Use object pooling for expensive objects
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);
    
    // Bad: Creating new executors frequently
    public void badExecutorUsage() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        // Use executor
        executor.shutdown(); // This is good, but creating new ones is bad
    }
    
    private void processLine(String line) {
        // Process the line
    }
}
```

### 2. Thread Management

```java
public class ThreadBestPractices {
    
    // Good: Use thread pools
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);
    
    public void goodThreadUsage() {
        for (int i = 0; i < 100; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("Processing task " + taskId);
                // Do work
            });
        }
    }
    
    // Bad: Creating threads manually
    public void badThreadUsage() {
        for (int i = 0; i < 100; i++) {
            final int taskId = i;
            new Thread(() -> {
                System.out.println("Processing task " + taskId);
                // Do work
            }).start();
        }
    }
    
    // Good: Proper shutdown
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
```

### 3. Monitoring Best Practices

```java
public class MonitoringBestPractices {
    
    // Good: Add monitoring points
    public void monitoredMethod() {
        long startTime = System.currentTimeMillis();
        try {
            // Method logic
            performWork();
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            if (duration > 1000) { // Log slow operations
                System.out.println("Slow operation detected: " + duration + "ms");
            }
        }
    }
    
    // Good: Memory monitoring
    public void checkMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        
        double memoryUsagePercent = (double) usedMemory / maxMemory * 100;
        
        if (memoryUsagePercent > 80) {
            System.out.println("High memory usage: " + memoryUsagePercent + "%");
        }
    }
    
    private void performWork() {
        // Simulate work
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

This comprehensive JVM tutorial covers all the requested topics with practical examples and best practices for monitoring and optimization using VisualVM. 
# Java Debugging Examples

This package contains comprehensive debugging examples organized into logical sub-packages for better maintainability and learning progression.

## Package Structure

### `basics/` - Fundamental Debugging Concepts
- **TraditionalDebugging.java** - Demonstrates debugging using print statements
- **ModernDebugging.java** - Shows IDE debugger usage with breakpoints
- **CalculatorTest.java** - Example of testing vs debugging approaches
- **CalculatorDebug.java** - Debugging unexpected behavior scenarios

### `util/` - Shared Utility Classes
- **Calculator.java** - Simple calculator class used across examples
- **Person.java** - Person model class for object debugging examples

### `breakpoints/` - Breakpoint Techniques
- **BreakpointExample.java** - Basic line breakpoint usage
- **MethodBreakpointExample.java** - Method entry/exit breakpoints
- **ConditionalBreakpointExample.java** - Conditional breakpoints
- **ExceptionBreakpointExample.java** - Exception-based breakpoints
- **BreakpointStrategyExample.java** - Effective breakpoint strategies

### `stepping/` - Step-Through Debugging
- **StepThroughExample.java** - Step over, into, and out techniques
- **CallStackExample.java** - Call stack navigation
- **SmartStepIntoExample.java** - Smart step into with multiple method calls
- **DropFrameExample.java** - Drop frame functionality

### `inspection/` - Variable and Expression Inspection
- **VariableInspectionExample.java** - Inspecting variables and objects
- **WatchExample.java** - Using watches to monitor expressions
- **ExpressionEvaluationExample.java** - Evaluating expressions during debugging

### `advanced/` - Advanced Debugging Techniques
- **CollectionDebuggingExample.java** - Debugging collections and arrays
- **RecursiveDebuggingExample.java** - Debugging recursive methods
- **ThreadDebuggingExample.java** - Multi-threaded debugging
- **DebugConsoleExample.java** - Debug console features

### `scenarios/` - Common Debugging Scenarios
- **NullPointerDebuggingExample.java** - NullPointerException debugging
- **ArrayIndexDebuggingExample.java** - Array index issues
- **InfiniteLoopDebuggingExample.java** - Infinite loop detection
- **MemoryLeakExample.java** - Memory leak scenarios
- **PerformanceDebuggingExample.java** - Performance debugging techniques

## Usage

Each example is designed to be run in an IDE with debugging capabilities (like IntelliJ IDEA). The examples include comments indicating where to set breakpoints and what to observe during debugging sessions.

## Learning Path

1. Start with `basics/` to understand fundamental concepts
2. Practice with `breakpoints/` to master breakpoint techniques
3. Learn `stepping/` for execution flow control
4. Use `inspection/` for variable and expression analysis
5. Explore `advanced/` for complex debugging scenarios
6. Study `scenarios/` for real-world debugging challenges

## Dependencies

- Java 8 or higher
- IDE with debugging capabilities (IntelliJ IDEA recommended)
- No external dependencies required 
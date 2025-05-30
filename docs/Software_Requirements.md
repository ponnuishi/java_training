# Software Requirements

## Required Tools and Software

### 1. Java Development Kit (JDK)
- **Version**: 17 or higher
- **Installation Options**:
  - [SDKMAN!](https://sdkman.io/): `sdk install java 17.0.x-tem`
  - [Jabba](https://github.com/shyiko/jabba): `jabba install openjdk@17.0`
  - Direct download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/)

### 2. Integrated Development Environment
- **IntelliJ IDEA Community Edition**
- Download from: [JetBrains Website](https://www.jetbrains.com/idea/download/)
- Recommended plugins:
  - GitHub Copilot
  - Gradle
  - Java IDE Pack
  - Database Tools

### 3. Version Control
- **Git**
  - Windows: [Git for Windows](https://gitforwindows.org/) (includes Git Bash)
  - Mac: `brew install git`
  - Linux: `sudo apt-get install git` or equivalent
- **GitHub Account**
  - Sign up at [GitHub](https://github.com/)
  - Required for:
    - Code sharing
    - GitHub Copilot access
    - Repository management

### 4. AI Assistance
- **GitHub Copilot**
  - Subscription required
  - Setup in IntelliJ IDEA:
    1. Install GitHub Copilot plugin
    2. Sign in with GitHub account
    3. Verify subscription

### 5. Database Tools
- **PostgreSQL**
  - Version: Latest stable
  - Download: [PostgreSQL Downloads](https://www.postgresql.org/download/)
- **Database GUI** (choose one):
  - [pgAdmin](https://www.pgadmin.org/download/) - Official PostgreSQL GUI
  - [DBeaver](https://dbeaver.io/download/) - Universal database tool

### 6. Build Tools
- **Gradle**
  - Latest version
  - Installation:
    - Windows: `choco install gradle`
    - Mac: `brew install gradle`
    - Linux: `sdk install gradle`
    - Or via wrapper (recommended): included in project

### 7. Monitoring Tools
- **VisualVM**
  - Download: [VisualVM Downloads](https://visualvm.github.io/download.html)
  - Used for:
    - JVM monitoring
    - Performance profiling
    - Memory analysis

### 8. API Testing Tools (choose one)
- **Postman**
  - Download: [Postman](https://www.postman.com/downloads/)
  - Features:
    - GUI interface
    - Collection management
    - Environment variables
- **cURL**
  - Usually pre-installed on Mac/Linux
  - Windows: Included with Git for Windows

## Verification Steps

1. Verify Java installation:
   ```bash
   java -version
   ```

2. Verify Git installation:
   ```bash
   git --version
   ```

3. Verify Gradle installation:
   ```bash
   gradle --version
   ```

4. Verify PostgreSQL installation:
   ```bash
   psql --version
   ```

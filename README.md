# Selenide Test Automation Framework

A comprehensive UI test automation framework built with Java, Selenide, and TestNG, implementing the Page Object Model (POM) pattern for enhanced maintainability and scalability.

## 🚀 Features

- **Page Object Model (POM)** - Clean separation of test logic and page elements
- **Selenide Integration** - Simplified Selenium WebDriver operations
- **TestNG Framework** - Robust test execution and reporting
- **Maven Build System** - Dependency management and project structure
- **Cross-browser Support** - Compatible with multiple browsers
- **Detailed Reporting** - Comprehensive test execution reports

## 🏗️ Project Structure

```
Selenide_repo_DuyPhan/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── pageObject/
│   │   │       └── pages/
│   │   │           ├── HomePage.java
│   │   │           └── ShopPage.java
│   │   └── resources/
│   │       └── testng.xml
│   └── test/
│       ├── java/
│       │   └── test/
│       │       ├── BaseTest.java
│       │       └── TestCases.java
│       └── resources/
├── docker-compose.yml
├── pom.xml
├── test-output/          # TestNG reports
├── target/               # Maven build artifacts
├── allure-results/       # Allure test data
├── allure-report/        # Generated Allure reports
└── README.md
```

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| [Java](https://www.oracle.com/java/) | 21.0.7 | Programming Language |
| [Apache Maven](https://maven.apache.org/) | 3.9.9 | Build Tool & Dependency Management |
| [Selenide](https://selenide.org/) | 7.9.2 | UI Test Automation Framework |
| [TestNG](https://testng.org/) | 7.11.0 | Testing Framework |
| [Docker](https://www.docker.com/) | Latest | Containerization Platform |
| [Docker Compose](https://docs.docker.com/compose/) | Latest | Multi-container Docker Applications |
| [Allure](https://docs.qameta.io/allure/) | Latest | Test Reporting Framework |

## 📋 Prerequisites

Before running this project, ensure you have the following installed:

- **Java JDK 8 or higher** (Java 21 recommended)
- **Apache Maven 3.6+**
- **Docker** and **Docker Compose**
- **Allure CLI** (for viewing detailed test reports)
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code)

### Installing Allure CLI

**On macOS (using Homebrew):**
```bash
brew install allure
```

**On Windows (using Scoop):**
```bash
scoop install allure
```

**Manual installation:**
1. Download from [Allure releases](https://github.com/allure-framework/allure2/releases)
2. Extract and add to PATH

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/caodii1999/Selenide_repo_DuyPhan.git
cd Selenide_repo_DuyPhan
```

### 2. Start Docker Environment

```bash
docker compose up -d
```

This command will:
- Start the required containerized services in detached mode
- Set up the test environment infrastructure
- Initialize any necessary databases or services

### 3. Install Dependencies & Run Tests

```bash
mvn clean test
```

### 4. View Test Reports (Optional)

Generate and view detailed Allure reports:

```bash
allure serve allure-results
```

This will:
- Generate a comprehensive HTML report from test results
- Automatically open the report in your default browser
- Display test execution details, trends, and failure analysis

### 5. Cleanup (Optional)

After testing, you can stop the Docker services:

```bash
docker compose down
```

## 🐳 Docker Configuration

The project uses Docker Compose to orchestrate the test environment. The `docker-compose.yml` file defines:
- Application services
- Database containers (if applicable)
- Network configuration
- Volume mappings

Make sure Docker is running before executing `docker compose up -d`.

## 🧪 Test Execution Options

**Standard test execution:**
```bash
# Start services and run tests
docker compose up -d
mvn clean test
```

**Run specific test class:**
```bash
docker compose up -d
mvn clean test -Dtest=TestCases
```

**Run with different browser (if supported):**
```bash
docker compose up -d
mvn clean test -Dselenide.browser=chrome
```

**Run tests with custom properties:**
```bash
docker compose up -d
mvn clean test -Dselenide.headless=true -Dselenide.browserSize=1920x1080
```

**View test reports:**
```bash
# After test execution, generate and view Allure report
allure serve allure-results
```

## 🏗️ Framework Architecture

### Page Object Model Structure

- **BaseTest.java** - Contains common setup and teardown methods
- **HomePage.java** - Page object for home page elements and actions
- **ShopPage.java** - Page object for shop page elements and actions
- **TestCases.java** - Contains all test scenarios

### Key Components

1. **Page Objects** (`src/main/java/pageObject/pages/`)
   - Encapsulate web elements and page-specific actions
   - Promote code reusability and maintainability

2. **Test Classes** (`src/test/java/test/`)
   - BaseTest: Common test setup and configuration
   - TestCases: Actual test scenarios implementation

3. **Configuration** (`src/main/resources/`)
   - TestNG XML configuration for test execution

## 📊 Test Reporting

This project supports multiple reporting formats:

### Allure Reports (Recommended)
After test execution, generate and view detailed Allure reports:

```bash
allure serve allure-results
```

**Allure Features:**
- 📈 **Trend Analysis** - Track test execution trends over time
- 🔍 **Detailed Test Steps** - Step-by-step execution breakdown
- 📷 **Screenshots** - Automatic failure screenshots (if configured)
- 📋 **Test Categories** - Organized test results by features
- ⏱️ **Execution Timeline** - Visual representation of test timing
- 📊 **Statistics** - Pass/fail ratios and execution metrics

### Standard Reports
- `test-output/` - TestNG HTML reports
- `target/surefire-reports/` - Maven Surefire reports
- `allure-results/` - Raw Allure data files

### Viewing Reports

1. **Allure Report (Interactive):**
   ```bash
   allure serve allure-results
   ```

2. **Generate Allure Report to Directory:**
   ```bash
   allure generate allure-results --output allure-report
   ```

3. **TestNG Report:**
   Open `test-output/index.html` in your browser

## 🔧 Configuration

### Browser Configuration

You can configure browser settings in several ways:

1. **System Properties:**
   ```bash
   -Dselenide.browser=chrome
   -Dselenide.headless=true
   -Dselenide.browserSize=1920x1080
   ```

2. **In Code (BaseTest.java):**
   ```java
   Configuration.browser = "chrome";
   Configuration.headless = false;
   Configuration.browserSize = "1920x1080";
   ```

### TestNG Configuration

Test execution is configured in `src/main/resources/testng.xml`:
- Test suites and classes
- Parallel execution settings
- Test parameters

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 Best Practices

- Follow Page Object Model pattern
- Use meaningful and descriptive test names
- Implement proper wait strategies with Selenide
- Add appropriate assertions for test validation
- Maintain clean and readable code
- Use TestNG annotations effectively

## 🐛 Troubleshooting

### Common Issues

1. **Docker Issues:**
   - Ensure Docker is running: `docker --version`
   - Check if containers are running: `docker compose ps`
   - View container logs: `docker compose logs [service-name]`
   - Restart services: `docker compose restart`

2. **Port Conflicts:**
   - Check if required ports are available
   - Stop conflicting services or change port mappings in docker-compose.yml

3. **Test Execution Failures:**
   - Ensure Docker services are running (`docker compose ps`)
   - Check application logs for service startup issues
   - Verify network connectivity between containers

4. **Build Issues:**
   - Run `mvn clean install` to refresh dependencies
   - Check Java and Maven versions
   - Ensure Docker containers are healthy before running tests

5. **Report Generation Issues:**
   - Ensure tests have completed successfully before generating reports
   - Check if `allure-results` directory exists and contains data
   - Verify Allure CLI installation: `allure --version`
   - Clear old results: `rm -rf allure-results allure-report` then re-run tests

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 📧 Contact

**Duy Phan** - [@caodii1999](https://github.com/caodii1999)

Project Link: [https://github.com/caodii1999/Selenide_repo_DuyPhan](https://github.com/caodii1999/Selenide_repo_DuyPhan)

---

⭐ **Star this repository if you find it helpful!**

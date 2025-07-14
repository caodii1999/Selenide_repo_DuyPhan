# Selenium Test Automation with Selenide & TestNG

This project demonstrates a basic automated UI test using Java, Selenide and TestNG  
It follows the Page Object Model (POM) pattern for better structure and maintainability.

## Project Structure

├── src
│ ├── main
│ │ ├── java
│ │ │ └── pageObject.pages
│ │ │ ├── HomePage.java
│ │ │ └── ShopPage.java
│ │ └── resources
│ │ └── testng.xml
│ ├── test
│ │ ├── java
│ │ │ └── test
│ │ │ ├── BaseTest.java
│ │ │ └── TestCases.java
│ │ └── resources
├── pom.xml
├── test-output/
├── target/
├── build/
├── TestNG/
├── Maven Dependencies/
└── JRE System Library [jdk-21]

## Technologies Used

- [Java] - [Version: 21.0.7] (https://www.oracle.com/java/)
- [Apache Maven] - [Version: 3.9.9] (https://maven.apache.org/)
- [Selenide] - [Version: 7.9.2] (https://selenide.org/)
- [TestNG] - [Version: 7.11.0] (https://testng.org/)
- Page Object Model (POM)

### Prerequisites

- Java JDK 8+
- Maven or your preferred build tool
- IDE (IntelliJ IDEA, Eclipse, etc.)

### Installation

Clone the repository:

```bash
git clone https://github.com/yourusername/your-repo-name.git
cd your-repo-name

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

- [Java](https://www.oracle.com/java/)
- [Selenide](https://selenide.org/)
- [TestNG](https://testng.org/)
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

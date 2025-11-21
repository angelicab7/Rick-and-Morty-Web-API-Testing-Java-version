# Rick and Morty Web & API Testing

A comprehensive Java-based testing project for the Rick and Morty API and web application, combining API testing with UI automation using modern testing frameworks.

## 📋 Project Overview

This project demonstrates test automation for both:
- **Web UI Testing**: Using Playwright with Cucumber BDD framework
- **API Testing**: Using RestAssured for REST API validation

The project tests the [Rick and Morty API](https://rickandmortyapi.com/api) and a custom [Web App](https://angelicab7.github.io/BOG001-data-lovers/) built with the API data.

## 🛠️ Tech Stack

- **Java 25**: Programming language
- **Maven 3.x**: Build tool
- **Playwright**: Browser automation for web testing
- **Cucumber 7.31.0**: BDD framework for web tests
- **TestNG 7.11.0**: Testing framework
- **RestAssured 5.5.6**: API testing library
- **Logback 1.5.13**: Logging framework

## 📦 Project Structure

```
src/test/
├── java/
│   └── dev/angelicab7/rickandmorty/app/
│       └── Web/
│           ├── hooks/
│           │   └── Hooks.java
│           ├── pages/
│           │   ├── HomePage.java
│           │   └── CharactersPage.java
│           ├── runners/
│           │   └── TestRunner.java
│           ├── steps/
│           │   ├── HomePageSteps.java
│           │   ├── CharactersPageSteps.java
│           │   └── VisualSteps.java
│           └── utils/
│               └── VisualRegression.java
└── resources/
    └── features/
        ├── search_character.feature
        └── visual_search.feature

visuals/
├── baseline/     # Baseline screenshots
├── current/      # Current test screenshots
└── diffs/  
```
## 🧪 Test Suites

### API Tests

The project includes three API test classes:

- **CharactersTest.java**: Tests character endpoints
  - Health check for all characters
  - Get single character by ID
  - Get multiple characters
  - Filter characters by name and type

- **LocationsTest.java**: Tests location endpoints
  - Health check
  - Single and multiple location retrieval
  - Location filtering

- **EpisodesTest.java**: Tests episode endpoints
  - Health checks
  - Episode retrieval and filtering

### Web Tests

Web UI tests are organized using BDD with Cucumber:

- **Feature File**: search_character.feature
  - Verify characters list display
  - Search and filter characters combined
  - Sort characters A-Z

- **Page Objects**:
  - HomePage.java
  - CharactersPage.java

- **Step Definitions**:
  - HomePageSteps.java
  - CharactersPageSteps.java

### Visual Tests
Visual regression testing is implemented using **Java AWT BufferedImage** for pixel-by-pixel comparison

- **Feature File**: visual_search.feature
    - Verify home page visual stability
    - Verify characters page layout consistency

- **Page Objects**:
    - HomePage.java
    - CharactersPage.java

- **Step Definitions**:
    - VisualSteps.java

- **Utilities**:
    - VisualRegression.java

## 🚀 Getting Started

### Prerequisites

- Java 25 or higher
- Maven 3.6+
- Git

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd Rick-and-Morty-Web-API-Testing-Java-version
```

2. Install dependencies:
```bash
mvn clean install
```

3. Install Playwright browsers:
```bash
mvn exec:java -Dexec.mainClass="com.microsoft.playwright.CLI" -Dexec.args="install"
```

## ▶️ Running Tests

### Run API Tests Only
```bash
mvn test -P api-tests
```

### Run Web/Cucumber Tests
```bash
mvn test -P web-tests
```
### Run Specific Test Class
```bash
mvn clean test -Dcucumber.filter.tags="@search_and_filter"
```
# Run only visual regression tests
```bash
mvn test -Dcucumber.filter.tags="@visual_regression"
```
# Run specific visual test
```bash
mvn test -Dcucumber.filter.tags="@visual_homepage"
```
# Run all tests including visual
```bash
mvn clean test
```
# First run - Creates baseline images:
```bash
mvn test -Dcucumber.filter.tags="@visual_homepage"
```
# Test will fail with message "BASELINE CREATED"
Baseline images saved to visuals/baseline/

# Subsequent runs - Compares against baseline:
```bash
mvn test -Dcucumber.filter.tags="@visual_homepage"
```
# If differences > 50 pixels → Test fails
Diff images saved to visuals/diffs/


## 📊 Test Reports

After running tests, reports are generated in:

- **Cucumber HTML Report**: `target/cucumber-reports/cucumber.html`
- **Cucumber JSON Report**: `target/cucumber-reports/cucumber.json`
- **Cucumber XML Report**: `target/cucumber-reports/cucumber.xml`
- **Test Logs**: `target/test-logs/api-tests.log`

## 📝 Configuration

### Logging Configuration

Logging is configured in `logback-test.xml`:
- Console output with formatted timestamps
- File output to `target/test-logs/api-tests.log`
- Debug level for test packages

### Test Configuration

- API Base URL: `https://rickandmortyapi.com/api`
- Web Application URL: `https://angelicab7.github.io/BOG001-data-lovers/`
- Browser: Chromium (non-headless for visibility)
- Viewport: 1920x1080

## 🔧 Browser Hooks

The `Hooks.java` class manages:

- Browser initialization and teardown
- Screenshot capture on test failure
- Thread-safe browser context management
- Detailed scenario logging

## 📚 Dependencies

Key dependencies defined in `pom.xml`:

- TestNG 7.11.0
- RestAssured 5.5.6
- Playwright 1.55.0
- Cucumber 7.31.0
- Logback 1.5.13

## 🤝 Best Practices

- **Page Object Model**: UI elements are encapsulated in page classes
- **BDD Framework**: User-friendly scenario descriptions
- **Logging**: Comprehensive logging for debugging
- **Screenshots**: Automatic failure screenshots
- **Maven Profiles**: Easy test suite organization

## 📄 License

This project is provided as-is for educational purposes.

## 👤 Author

- [angelicab7](https://github.com/angelicab7)

---
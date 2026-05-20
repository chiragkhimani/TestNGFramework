# AGENTS.md - TestNG Automation Framework Guide

## Project Overview
This is a **Selenium + TestNG automation framework** using the **Page Object Model (POM)** pattern to test the SauceLabs demo application (https://www.saucedemo.com/). The framework is built with Maven and Java 21.

## Architecture & Key Patterns

### Page Object Model (POM) Structure
- **BasePage** (`src/test/java/com/automation/pages/BasePage.java`): Base class for all page classes
  - Holds the WebDriver instance
  - All page classes extend this and call `super(driver)` in constructor
  - Fully implemented page classes: LoginPage, HomePage, ProductDetailPage, CheckoutPage
  - Incomplete/stub page classes: CartPage, ReviewPage, OrderConfirmationPage (empty implementations - extend as needed)

- **Page Class Pattern**:
  - Use `@FindBy` annotations from Selenium's PageFactory for element locators
  - Create public methods that represent user actions (e.g., `doLogin()`, `clickOnProductName()`, `fillCheckoutForm()`)
  - Methods should encapsulate locator logic and return human-readable data
  - Example from LoginPage: `doLogin()` handles username/password input and button click
  - Example from CheckoutPage: `fillCheckoutForm()` demonstrates multi-field input patterns

### Test Base Class
- **BaseTest** (`src/test/java/com/automation/test/BaseTest.java`): Foundation for all test classes
  - `@BeforeMethod` (named `setUp()`): Initializes ChromeDriver with options disabling password manager, navigates to https://www.saucedemo.com/, maximizes window, sets implicit wait to 30 seconds
  - Instantiates all page objects for use in tests
  - `@AfterMethod` (named `cleanUp()`): Currently has `driver.quit()` commented out (may be intentional for debugging)
  - **All test classes should extend BaseTest** to use inherited `driver` and page object instances

- **Test Class Pattern**:
  - Core test class: `DemoClassTest` extends BaseTest and demonstrates the pattern
  - Stub test class: `LoginTest` (incomplete - does NOT currently extend BaseTest)
  - Extend BaseTest to access driver and page objects
  - Use @Test annotation for test methods
  - Call page object methods to interact with application (preferred over direct WebDriver calls)
  - Use TestNG Assert for validations
  - Access driver directly only when page object doesn't provide needed action (as shown in DemoClassTest for checkout flow)

## Critical Developer Workflows

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=DemoClassTest

# Run specific test method
mvn test -Dtest=DemoClassTest#orderPlacementTest
```

### Build & Compile
```bash
# Compile project
mvn compile

# Clean and compile
mvn clean compile

# Full clean install
mvn clean install
```

## Project-Specific Conventions

### Element Locator Preference
Files show mixed locator strategies:
- **@FindBy preferred**: Use for elements on a specific page (id, xpath, class attributes)
- **Dynamic xpath patterns**: Used in HomePage for dynamic product selection (`//div[@class='inventory_item_name ' and text()='...']`)
- **Direct driver.findElement()**: Used in test methods only when page object doesn't provide needed action (CheckoutPage logic in DemoClassTest)

### Method Naming
- Page action methods: Use descriptive action names (`clickOnProductName()`, `doLogin()`, `getProductDetailPageTitle()`)
- Getter methods: Prefix with `get` for retrieving text/values (`getProductDetailPageTitle()`, `getItemName()`)
- Boolean methods: Prefix with `is` for page state verification (`isHomePageDisplayed()`)

### Default Test Credentials
- Login credentials hardcoded in LoginPage.doLogin():
  - Username: `standard_user`
  - Password: `secret_sauce`

## Dependencies & Integration Points

### Core Dependencies (pom.xml)
- **Selenium 4.41.0**: WebDriver for browser automation
- **TestNG 7.11.0**: Testing framework with @BeforeMethod/@AfterMethod/@Test annotations
- **Java 21**: compiler source/target

### Utility Classes
- **ConfigReader** (`src/test/java/com/automation/utilities/ConfigReader.java`): Configuration property loader (incomplete implementation - placeholder for reading config.properties file)

### Application Under Test
- Target: SauceLabs demo application (https://www.saucedemo.com/)
- Note: Test navigates to this URL in BaseTest.setUp()

## Common Tasks for Agents

### Incomplete Components (Current State)
Be aware that the following components are stub implementations and may need completion:
- **CartPage, ReviewPage, OrderConfirmationPage**: Empty classes that don't extend BasePage. If tests require these pages, they need to be implemented following the POM pattern
- **LoginTest**: Exists but is empty and doesn't extend BaseTest. Should be implemented following DemoClassTest pattern if needed
- **ConfigReader**: Exists but is incomplete - relies on external `config.properties` file that may not be present

### Adding a New Test
1. Create new class extending BaseTest in `src/test/java/com/automation/test/`
2. Inherit access to `driver` and all page objects (loginPage, homePage, productDetailPage, cartPage)
3. Use @Test annotation on test methods
4. Call existing page object methods rather than duplicating element interactions

### Adding a New Page Class
1. Create class extending BasePage in `src/test/java/com/automation/pages/`
2. Constructor must accept WebDriver and call `super(driver)`
3. Define @FindBy WebElement fields for locators
4. Create public action methods that return data or void
5. Register new page object instance in BaseTest (add field in BaseTest class and instantiate in setUp() method)
6. **Note**: CartPage, ReviewPage, and OrderConfirmationPage are currently empty stubs - implement them using this pattern if needed

### Updating Element Locators
- Update @FindBy annotations in page classes, not in tests
- If @FindBy doesn't match, use `driver.findElement()` with By locators
- Review existing page classes for locator format consistency

### Debugging Tests
- Implicit wait is 30 seconds globally (BaseTest.setUp())
- Page objects only retrieve element state; tests handle assertions
- Check element visibility with page object methods before proceeding


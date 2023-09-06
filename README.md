# Java QA Automation Demo 2

This repository contains a QA automation demonstration that utilizes Java, Maven, Selenium WebDriver with Selenide, and JUnit. The project is built to automate crucial steps of the e-commerce workflow on Google and Rozetka websites.

## Setup

### Prerequisites

You will need the following tools and technologies:

* Java JDK 17
* Maven

```bash
# Running the Test Suite
mvn test
```

## Project Structure

The automation framework adheres to the Page Object Model (POM) design pattern and makes use of JUnit for test scripting. Each page-specific function is abstracted in the classes under the `com.os8580.pages` package.

* `RozetkaTest` - Under the `com.os8580` package, this class carries the main e-commerce operation test suite. 

* `com.os8580.pages`:
  * `AbstractPage` - This abstract base class caters for common page interactions and sets groundwork for other page classes.
  * `GooglePage` - The page object model for Google's search page. 
  * `RozetkaPage` - The page object model for Rozetka search page. It contains methods to operate the search function, get search results, add product to cart, modify quantity and others.
  * `Pages` - An enumeration which represents types of available pages.

* `com.os8580.utils`:
  * `PageFactory` - It creates and returns instances of various pages including `GooglePage`, `RozetkaPage` etc.

* `pom.xml` - Contained in the root directory, it acts as the project configuration file for Maven.

* `logback-test.xml` - It is a configuration file that defines how log statements are displayed during test execution

## Note

This is a demonstration project aimed at showcasing an automation framework using Selenide, JUnit, Java, and Maven. The e-commerce operations that are being performed are simulated and do not correspond to actual goods purchases.

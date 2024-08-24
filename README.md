# Spring Upload Download Thymeleaf Tests

## Project Description

This project is a web application built using the Spring Framework that demonstrates file upload and download functionalities, utilizing Thymeleaf for generating dynamic HTML pages. The project also includes a suite of unit and integration tests to verify the correctness of the implementation.

## Technologies Used

- **Java 19** - The programming language used for the project.
- **Spring Boot 3.x** - A framework for building web applications, simplifying configuration and application startup.
- **Spring MVC** - A Spring component responsible for managing HTTP requests and generating responses.
- **Thymeleaf** - A template engine used for generating HTML views.
- **JUnit 5** - A unit testing framework used for writing and running tests.
- **Mockito** - A tool for mocking objects in tests, allowing isolation of units under test.

## Features

- **File Upload**: Users can upload files to the server through an HTML form.
- **File Download**: Uploaded files can be downloaded from the server.
- **File Validation**: The application validates files before saving them.
- **Testing**: The project includes a suite of unit and integration tests to ensure the application's functionality.

## Project Structure

- **src/main/java**: Main application source files.
- **src/main/resources**: Configuration files and HTML templates.
- **src/test/java**: Unit and integration tests.

## Testing

The project includes the following types of tests:

### Unit Tests

Unit tests are implemented using JUnit 5 and Mockito. These tests focus on verifying the correctness of individual units of code, such as services or controllers, in isolation from other components.

- **Mockito**: Used for mocking dependencies, allowing the testing of units in isolation from external dependencies like databases.

### Integration Tests

Integration tests verify the correct interaction between different components of the application, such as controllers, services, and repositories. These tests ensure that the application works as expected as a whole.

- **Spring Boot Test**: Used to load the application context during tests, allowing for the testing of real component integration.

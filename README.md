# <div align="center">REST User Service</div>

This application allows to manage user activities. You can register, change user profile or select users by birth date range.

## Get Started

To run the project, follow this instruction:

1. Clone the repository:
   ```bash
   git clone https://github.com/vadymhrnk/REST-user-service.git
   ```

2. Download [JDK](https://www.oracle.com/java/technologies/downloads/), [Apache Maven](https://maven.apache.org/download.cgi) and [Docker](https://docs.docker.com/get-docker/)

3. Build the project using:
   ```bash
   mvn clean package 
   ```
 
4. Use terminal to run application:
   ```bash
   mvn spring-boot:run
   ```

## Technologies used

- ### Backend Technologies
    - **Java 17**: the primary programming language for backend development.
    - **Spring Boot**: the framework for building and deploying Java-based applications with ease.
    - **Spring Boot Starter Data JPA**: starter for using Spring Data JPA with Hibernate.
    - **Spring Boot Starter Validation**: starter for validation support.
    - **Spring Boot Starter Web**: starter for building web applications, including RESTful APIs.
    - **Hibernate Validator**: a powerful and flexible framework for data validation.
    - **Liquibase Core**: a database-independent library for tracking, managing and applying database schema changes.
    - **MapStruct**: simplifies the implementation of bean mappings, reducing manual coding effort.
    - **MySQL Connector/J**: JDBC driver for MySQL integration.
    - **H2 Database**: an in-memory database for testing purposes.
    - **Lombok**: a tool to reduce boilerplate code, enhancing code readability and conciseness.
    - **Spring Boot Starter Test**: starter for testing Spring Boot applications.
    - **Testcontainers JUnit Jupiter**: a Java library that supports JUnit tests, providing lightweight, throwaway instances of common databases, Selenium web browsers, or anything else that can run in a Docker container.
    - **Testcontainers MySQL**: a Testcontainers module for MySQL.
    - **AssertJ Core**: a library providing a fluent interface for writing assertions in Java tests.

- ### API Documentation
    - **Springdoc OpenAPI UI**: an OpenAPI for generating documentation with a UI interface.


## Application Endpoints

  - **Risk countries controller:**
      - `POST: /users/registration` -> Register a new user.
      - `GET: /users/birthDateRange` -> Search users within a specified birthdate range.
      - `PUT: /users/{userId}` -> Allows users to update their profile information.
      - `PATCH: /users/{userId}` -> Allows users to partially update an existing user.
      - `DELETE: /users/{userId}` -> Delete user by ID.

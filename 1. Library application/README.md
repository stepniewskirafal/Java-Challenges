# Library Management Application - Junior Level Project

## Overview

This project entails the design and implementation of a simple application for managing books in a library, tailored for Junior level proficiency with Java, Spring Boot, and H2 Database. It serves as a practical exercise in developing RESTful APIs, handling database operations, and applying best practices in software development with a focus on clean, maintainable code.

## Features

- **CRUD Operations**: The application supports Create, Read, Update, and Delete (CRUD) operations for books, allowing users to manage the library's collection effectively.
- **H2 Database Integration**: Utilizing the H2 Database provides a lightweight, in-memory database solution for development purposes, making it easier to test and deploy the application without the need for a complex database setup.
- **Layered Architecture**: Attention was given to proper application layering, including the separation of concerns among controller, service, and repository layers. This ensures a clean architecture that facilitates maintenance and scalability.
- **HTTP Status Codes**: Appropriate HTTP status codes are used for CRUD operations to provide clear feedback on the outcome of requests, enhancing the API's usability and reliability.
- **Data Validation**: Implementing data validation for adding and updating book records ensures data integrity and prevents common input errors.
- **Exception Handling**: The application includes robust exception handling mechanisms, providing meaningful error messages to users and aiding in troubleshooting.
- **Error Handling Mechanisms**: Custom error handling enhances the user experience by providing clear, actionable feedback in case of issues.
- **Pagination**: Implementing pagination for the book list improves performance and user experience when dealing with large datasets.
- **DTO Pattern**: Applying the Data Transfer Object (DTO) pattern for CRUD operations encapsulates data transfer, reducing coupling and enhancing data integrity.

## Potential Interview Questions

- What motivated your choice of entity structure?
- How have you handled errors within the application?
- Are you familiar with transitioning from an H2 database to a more production-ready database solution?

# CodeCraftHub - Learning Platform REST API

A personalized learning platform built with Java and Spring Boot that allows developers to track courses they want to learn.

## Project Overview

CodeCraftHub is a Spring Boot REST API application developed as a final project for the "Generative AI for Software Developers" course on Coursera. It demonstrates core REST API principles and Spring Boot best practices for building backend applications.

### Features

- Track courses with details (name, description, target completion date, status)
- RESTful API for managing courses (Create, Read, Update, Delete)
- JSON file-based data storage (no database required)
- Simple, beginner-friendly project structure

### Course Tracking

Each course tracks the following information:

- **Course Name**: Title of the course
- **Description**: Course details and overview
- **Target Completion Date**: Expected date to complete the course
- **Status**: Current progress (Not Started, In Progress, Completed)

## Technology Stack

- **Java 17** - Programming language
- **Spring Boot 3.x** - Web framework
- **Maven** - Build tool
- **Jackson** - JSON serialization/deserialization
- **JUnit 5** - Testing framework

## Project Setup

### Prerequisites

- Java 17 or higher installed
- Maven 3.6+ installed
- Git installed

### Installation

1. Clone the repository:

   ```bash
   git clone <repository-url>
   cd codecrafthub
   ```

2. Build the project:

   ```bash
   mvn clean install
   ```

3. Run the application:

   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## API Endpoints

### Get All Courses

```http
GET /api/courses
```

Returns a list of all courses.

### Get Course by ID

```http
GET /api/courses/{id}
```

Returns a specific course by its ID.

### Create a New Course

```http
POST /api/courses
Content-Type: application/json

{
  "name": "Spring Boot Basics",
  "description": "Learn Spring Boot fundamentals",
  "targetDate": "2026-04-30",
  "status": "IN_PROGRESS"
}
```

### Update a Course

```http
PUT /api/courses/{id}
Content-Type: application/json

{
  "name": "Spring Boot Advanced",
  "description": "Advanced Spring Boot concepts",
  "targetDate": "2026-05-30",
  "status": "COMPLETED"
}
```

### Delete a Course

```http
DELETE /api/courses/{id}
```

Deletes a course by its ID.

## Data Storage

Course data is stored in `src/main/resources/courses.json` as a JSON array. The file is automatically created and managed by the application.

Example JSON structure:

```json
[
  {
    "id": "1",
    "name": "Spring Boot Basics",
    "description": "Learn Spring Boot fundamentals",
    "targetDate": "2026-04-30",
    "status": "IN_PROGRESS"
  }
]
```

## Project Structure

```text
codecrafthub/
├── src/
│   ├── main/
│   │   ├── java/com/codecrafthub/
│   │   │   ├── CodeCraftHubApplication.java    (Main application entry point)
│   │   │   ├── controller/
│   │   │   │   ├── CourseController.java      (REST endpoints)
│   │   │   │   └── ...
│   │   │   ├── service/
│   │   │   │   ├── CourseService.java         (Business logic)
│   │   │   │   └── ...
│   │   │   ├── model/
│   │   │   │   ├── Course.java                (Course entity)
│   │   │   │   ├── CourseStatus.java          (Status enum)
│   │   │   │   └── ...
│   │   │   ├── util/
│   │   │   │   ├── FileStorageUtil.java       (JSON file I/O)
│   │   │   │   └── ...
│   │   │   └── exception/
│   │   │       ├── CourseException.java       (Custom exception)
│   │   │       ├── GlobalExceptionHandler.java (Global error handling)
│   │   │       └── ...
│   │   └── resources/
│   │       ├── application.properties         (App configuration)
│   │       └── courses.json                   (Course data file)
│   └── test/
│       └── java/com/codecrafthub/
│           ├── service/
│           │   └── CourseServiceTest.java    (Service tests)
│           ├── controller/
│           │   └── CourseControllerTest.java (Controller tests)
│           └── ...
├── pom.xml                                     (Maven configuration)
├── README.md                                   (This file)
└── .gitignore                                  (Git ignore patterns)
```

## Running Tests

To run all tests:

```bash
mvn test
```

To run a specific test class:

```bash
mvn test -Dtest=CourseServiceTest
```

To run with coverage:

```bash
mvn test jacoco:report
```

## Building for Production

Create an executable JAR file:

```bash
mvn clean package
```

Run the JAR file:

```bash
java -jar target/codecrafthub-1.0.0.jar
```

## Development Phases

This project follows a systematic development approach:

1. **Phase 1** - Project Setup & Spring Boot Configuration
2. **Phase 2** - Data Model & File-Based Storage
3. **Phase 3** - Business Logic Layer (Service)
4. **Phase 4** - REST API Layer (Controller)
5. **Phase 5** - Testing

Each phase builds on the previous one, providing a clear learning progression.

## Learning Objectives

- Design and develop software applications using GenAI
- Create documentation for code with GenAI
- Create test cases with GenAI
- Run and test the application developed with GenAI

## Troubleshooting

### Application fails to start

- Ensure Java 17 or higher is installed: `java -version`
- Check that port 8080 is not in use
- Look at the console logs for error messages

### Cannot find courses.json

- The file should be at `src/main/resources/courses.json`
- If missing, the application will create it on first run
- Ensure the application has write permissions in the `src/main/resources/` directory

## License

This project is part of the Coursera "Generative AI for Software Developers" course.

## Author

Developed as a learning project for understanding REST APIs and Spring Boot with GenAI assistance.

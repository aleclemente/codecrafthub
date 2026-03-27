# CodeCraftHub Development Guide

This document provides guidance for developing the CodeCraftHub REST API learning platform.

## Project Overview

CodeCraftHub is a Spring Boot REST API for tracking courses. It's developed as a final project for the "Generative AI for Software Developers" Coursera course, focusing on learning REST API basics and Spring Boot best practices.

**Technology Stack:**

- Java 17
- Spring Boot 3.x
- Maven
- JSON file-based storage (no database)

## Development Approach

This project follows a **systematic step-by-step development approach** with 5 phases:

1. **Phase 1** - Project Setup & Spring Boot Configuration ✅ COMPLETE
2. **Phase 2** - Data Model & File-Based Storage ✅ COMPLETE
3. **Phase 3** - Business Logic Layer (Service) ✅ COMPLETE
4. **Phase 4** - REST API Layer (Controller) ✅ COMPLETE
5. **Phase 5** - Testing 🔄 IN PROGRESS

Each phase builds on previous phases and can be verified independently.

## Current Status: Phase 4 Complete ✅

**Completed Components:**

- ✅ Phase 1: pom.xml with Spring Boot 3.x dependencies
- ✅ Phase 1: Spring Boot main application class (CodeCraftHubApplication.java)
- ✅ Phase 1: Application configuration (application.properties)
- ✅ Phase 1: Empty courses.json data file
- ✅ Phase 1: Project metadata (.gitignore, README.md)
- ✅ Phase 2: Course model (Course.java) and status enum (CourseStatus.java)
- ✅ Phase 2: Custom exception (CourseException.java)
- ✅ Phase 2: File storage utility (FileStorageUtil.java)
- ✅ Phase 3: Business logic service (CourseService.java)
- ✅ Phase 4: Global exception handler (GlobalExceptionHandler.java)
- ✅ Phase 4: REST controller with endpoints (CourseController.java)

**Next Steps (Phase 5 - Testing):**

- Create CourseService unit tests (CourseServiceTest.java)
- Create CourseController integration tests (CourseControllerTest.java)
- Create test configuration (application-test.properties)
- Run end-to-end verification
- Verify all tests pass with `mvn test`
- Build final JAR with `mvn clean package`

## Core Features

### Course Data Model

Each course tracks:

- **id**: UUID (unique identifier)
- **name**: Course title
- **description**: Course details
- **targetDate**: Expected completion date (LocalDate)
- **status**: Current progress (NOT_STARTED, IN_PROGRESS, COMPLETED)

### REST API Endpoints

```text
GET    /api/courses              - List all courses (200 OK)
GET    /api/courses/{id}         - Get specific course (200 OK or 404)
POST   /api/courses              - Create new course (201 Created)
PUT    /api/courses/{id}         - Update course (200 OK or 404)
DELETE /api/courses/{id}         - Delete course (204 No Content or 404)
```

## Project Structure

```text
codecrafthub/
├── src/
│   ├── main/
│   │   ├── java/com/codecrafthub/
│   │   │   ├── CodeCraftHubApplication.java
│   │   │   ├── controller/
│   │   │   │   └── CourseController.java
│   │   │   ├── service/
│   │   │   │   └── CourseService.java
│   │   │   ├── model/
│   │   │   │   ├── Course.java
│   │   │   │   └── CourseStatus.java
│   │   │   ├── util/
│   │   │   │   └── FileStorageUtil.java
│   │   │   └── exception/
│   │   │       ├── CourseException.java
│   │   │       └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── courses.json
│   └── test/
│       └── java/com/codecrafthub/
│           ├── service/
│           │   └── CourseServiceTest.java (TO CREATE)
│           └── controller/
│               └── CourseControllerTest.java (TO CREATE)
├── pom.xml
├── README.md
├── .gitignore
└── CLAUDE.md (this file)
```

## Building and Running

### Build Project

```bash
mvn clean install
```

### Run Application

```bash
mvn spring-boot:run
```

The application starts on `http://localhost:8080`

### Run Tests

```bash
mvn test
```

### Test API Endpoints

```bash
# Get all courses
curl -X GET http://localhost:8080/api/courses

# Create a course
curl -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Spring Boot Basics",
    "description": "Learn Spring Boot fundamentals",
    "targetDate": "2026-04-30",
    "status": "IN_PROGRESS"
  }'

# Get specific course
curl -X GET http://localhost:8080/api/courses/{id}

# Update course
curl -X PUT http://localhost:8080/api/courses/{id} \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Advanced Spring Boot",
    "description": "Advanced concepts",
    "targetDate": "2026-05-30",
    "status": "COMPLETED"
  }'

# Delete course
curl -X DELETE http://localhost:8080/api/courses/{id}
```

## Key Implementation Details

### File Storage

- Courses are stored in `src/main/resources/courses.json`
- FileStorageUtil handles all JSON file I/O operations
- Uses Jackson ObjectMapper for serialization/deserialization
- Automatically creates file if it doesn't exist

### Service Layer (CourseService)

- Manages all CRUD operations
- Validates course data before operations
- Generates unique UUIDs for new courses
- Handles file read/write through FileStorageUtil
- Throws CourseException with appropriate HTTP status codes

### REST Controller

- Exposes 5 endpoints with proper HTTP methods
- Returns appropriate HTTP status codes
- Uses GlobalExceptionHandler for centralized error handling
- Follows REST best practices

### Error Handling

- CourseException with custom error codes and HTTP status
- GlobalExceptionHandler converts exceptions to JSON responses
- Consistent error response format with status, errorCode, message, timestamp, and path

## Learning Objectives Alignment

✅ **Design and develop software applications using GenAI** - Create complete Spring Boot REST API
✅ **Create documentation for the code with GenAI** - Javadoc and inline comments included
✅ **Create test cases with GenAI** - Unit and integration tests to be created in Phase 5
✅ **Run and test the application developed with GenAI** - Execute API and verify all functionality

## Testing Strategy

### Phase 5 - Testing (In Progress)

**CourseService Tests (Unit Tests):**

- Test all CRUD methods work correctly
- Test exception handling for invalid IDs
- Test field validation
- Test file I/O operations
- Use Mockito to mock FileStorageUtil

**CourseController Tests (Integration Tests):**

- Test all 5 endpoints with MockMvc
- Test request/response JSON serialization
- Test HTTP status codes (200, 201, 204, 400, 404)
- Test error responses from GlobalExceptionHandler
- Use @WebMvcTest for controller testing

## Validation Rules

### Course Validation (enforced in CourseService)

- Course name is required (non-empty string)
- Course description is required (non-empty string)
- Target date is required (LocalDate)
- Status is required (CourseStatus enum)
- Target date cannot be in the past
- Course cannot be null

### ID Validation

- ID is required and non-empty for update/delete/get operations
- ID format is UUID (generated by FileStorageUtil)

## Troubleshooting

| Issue | Solution |
| ------- | ---------- |
| Project won't build | Run `mvn clean install` to rebuild |
| Application won't start | Check port 8080 is available; verify Java 17+ installed |
| Cannot find courses.json | File is created automatically; ensure write permissions |
| API returns 404 | Verify course ID exists; check courses.json file |
| JSON parsing errors | Verify date format is `yyyy-MM-dd` in requests |

## Next Session Instructions

To continue development:

1. **Complete Phase 5 - Testing**
   - Create `src/test/java/com/codecrafthub/service/CourseServiceTest.java`
   - Create `src/test/java/com/codecrafthub/controller/CourseControllerTest.java`
   - Run `mvn test` and verify all tests pass

2. **End-to-End Verification**
   - Start application with `mvn spring-boot:run`
   - Test all 5 endpoints manually with curl
   - Verify courses.json persists data correctly
   - Build project with `mvn clean install`

3. **Optional Enhancements** (Beyond scope)
   - Add pagination for course listing
   - Add filtering by status
   - Add sorting capabilities
   - Add API documentation with Swagger

## Complete 5-Phase Development Plan

### Phase 1: Project Setup & Spring Boot Configuration ✅ COMPLETE

**Objective:** Set up Maven project with Spring Boot application foundation

**Created Files:**

- `pom.xml` - Maven configuration with Spring Boot 3.x
- `src/main/java/com/codecrafthub/CodeCraftHubApplication.java` - Main Spring Boot class
- `src/main/resources/application.properties` - App configuration
- `src/main/resources/courses.json` - Empty JSON data file
- `.gitignore` - Git ignore patterns
- `README.md` - Project documentation

**Dependencies Added:**

- spring-boot-starter-web (REST API)
- spring-boot-starter-json (Jackson)
- spring-boot-starter-test (Testing)
- lombok (Boilerplate reduction)

**Verification:**

```bash
mvn clean install
mvn spring-boot:run
```

---

### Phase 2: Data Model & File-Based Storage ✅ COMPLETE

**Objective:** Create Course entity and JSON file storage utility

**Created Files:**

- `src/main/java/com/codecrafthub/model/CourseStatus.java` - Enum
- `src/main/java/com/codecrafthub/model/Course.java` - POJO
- `src/main/java/com/codecrafthub/exception/CourseException.java` - Custom exception
- `src/main/java/com/codecrafthub/util/FileStorageUtil.java` - JSON file I/O

**Course Model:**

```java
- id: String (UUID)
- name: String
- description: String
- targetDate: LocalDate (yyyy-MM-dd)
- status: CourseStatus (NOT_STARTED, IN_PROGRESS, COMPLETED)
```

**FileStorageUtil Methods:**

- `loadCourses()` - Read from courses.json
- `saveCourses(List<Course>)` - Write to courses.json
- `generateId()` - Generate UUID

**JSON File Format:**

```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "Spring Boot Basics",
    "description": "Learn Spring Boot fundamentals",
    "targetDate": "2026-04-30",
    "status": "IN_PROGRESS"
  }
]
```

---

### Phase 3: Business Logic Layer (Service) ✅ COMPLETE

**Objective:** Implement CourseService with CRUD operations

**Created Files:**

- `src/main/java/com/codecrafthub/service/CourseService.java`

**Service Methods:**

- `getAllCourses()` - List all courses
- `getCourseById(String id)` - Get single course (404 if not found)
- `createCourse(Course)` - Create with auto-generated ID
- `updateCourse(String id, Course)` - Update existing (404 if not found)
- `deleteCourse(String id)` - Delete course (404 if not found)

**Business Logic:**

- UUID auto-generation for new courses
- Field validation (required, non-empty, date validation)
- Target date cannot be in the past
- Load → Modify → Save pattern
- CourseException with HTTP status codes

**Validation Rules:**

- name: Required, non-empty
- description: Required, non-empty
- targetDate: Required, not in past
- status: Required, valid enum

---

### Phase 4: REST API Layer (Controller) ✅ COMPLETE

**Objective:** Expose CourseService through REST endpoints

**Created Files:**

- `src/main/java/com/codecrafthub/exception/GlobalExceptionHandler.java`
- `src/main/java/com/codecrafthub/controller/CourseController.java`

**REST Endpoints:**

| Method | Endpoint | Status | Description |
| -------- | ---------- | -------- | ------------- |
| GET | /api/courses | 200 OK | List all courses |
| POST | /api/courses | 201 Created | Create course |
| GET | /api/courses/{id} | 200/404 | Get specific course |
| PUT | /api/courses/{id} | 200/404 | Update course |
| DELETE | /api/courses/{id} | 204/404 | Delete course |

**HTTP Status Codes:**

- 200 OK - Successful GET/PUT
- 201 Created - Successful POST
- 204 No Content - Successful DELETE
- 400 Bad Request - Invalid input
- 404 Not Found - Resource doesn't exist
- 500 Internal Server Error - Server error

**Error Response Format:**

```json
{
  "status": 404,
  "errorCode": "COURSE_NOT_FOUND",
  "message": "Course not found with ID: xyz",
  "timestamp": "2026-03-26T10:30:00",
  "path": "/api/courses/xyz"
}
```

**GlobalExceptionHandler:**

- Centralized error handling with @RestControllerAdvice
- CourseException to appropriate HTTP responses
- Generic exception handling for unexpected errors
- Consistent JSON error response format

---

### Phase 5: Testing ⏳ PENDING

**Objective:** Create unit and integration tests

**Tasks to Complete:**

#### 5.1: CourseService Unit Tests

**File:** `src/test/java/com/codecrafthub/service/CourseServiceTest.java`

Test Cases:

- getAllCourses returns list correctly
- getCourseById returns course when found
- getCourseById throws 404 when not found
- createCourse generates ID and saves
- createCourse validates required fields
- createCourse rejects past dates
- updateCourse modifies existing course
- updateCourse throws 404 when not found
- deleteCourse removes course
- deleteCourse throws 404 when not found

#### 5.2: CourseController Integration Tests

**File:** `src/test/java/com/codecrafthub/controller/CourseControllerTest.java`

Test Cases:

- GET /api/courses returns 200 with list
- POST /api/courses returns 201 with created course
- POST /api/courses with missing fields returns 400
- GET /api/courses/{id} returns 200 with course
- GET /api/courses/{invalid-id} returns 404
- PUT /api/courses/{id} returns 200 with updated course
- PUT /api/courses/{invalid-id} returns 404
- DELETE /api/courses/{id} returns 204
- DELETE /api/courses/{invalid-id} returns 404
- Global exception handler formats errors correctly

#### 5.3: Test Configuration

**File:** `src/test/resources/application-test.properties`

- Separate test configuration as needed

#### 5.4: Test Data

**File:** `src/test/resources/test-courses.json`

- Sample test data for integration tests

**Test Tools:**

- JUnit 5 (spring-boot-starter-test)
- Mockito for mocking
- Spring Test with MockMvc
- AssertJ for assertions

**Run Tests:**

```bash
mvn test
mvn test -Dtest=CourseServiceTest
mvn test jacoco:report
```

---

## End-to-End Verification Checklist

### 1. Build & Startup

- [ ] `mvn clean install` succeeds
- [ ] `mvn spring-boot:run` starts on port 8080
- [ ] Console shows "Started CodeCraftHubApplication"

### 2. Create Operations

- [ ] POST /api/courses with valid data → 201 Created
- [ ] POST /api/courses with missing name → 400 Bad Request
- [ ] POST /api/courses with past date → 400 Bad Request
- [ ] New course saved to courses.json

### 3. Read Operations

- [ ] GET /api/courses → 200 OK with course list
- [ ] GET /api/courses/{valid-id} → 200 OK with course
- [ ] GET /api/courses/{invalid-id} → 404 Not Found
- [ ] Empty list [] returned when no courses exist

### 4. Update Operations

- [ ] PUT /api/courses/{id} with valid data → 200 OK, updated
- [ ] PUT /api/courses/{id} with invalid data → 400 Bad Request
- [ ] PUT /api/courses/{invalid-id} → 404 Not Found
- [ ] Changes persisted in courses.json

### 5. Delete Operations

- [ ] DELETE /api/courses/{id} → 204 No Content
- [ ] DELETE /api/courses/{invalid-id} → 404 Not Found
- [ ] GET /api/courses/{deleted-id} → 404 Not Found
- [ ] Course removed from courses.json

### 6. Error Handling

- [ ] All errors include status, errorCode, message, timestamp, path
- [ ] Global exception handler catches all exceptions
- [ ] Proper HTTP status codes for all scenarios

### 7. Data Persistence

- [ ] courses.json maintains valid JSON format
- [ ] Data survives application restart
- [ ] File created automatically if missing

### 8. Testing

- [ ] `mvn test` - all tests pass
- [ ] Code coverage > 70%
- [ ] No test failures or warnings

### 9. Code Quality

- [ ] No compilation errors
- [ ] No console warnings
- [ ] Javadoc/comments present
- [ ] Spring Boot conventions followed

### 10. Documentation

- [ ] README.md accurate and complete
- [ ] Code comments and Javadoc present
- [ ] Endpoints documented with examples
- [ ] Setup instructions clear

---

## Quick Commands Reference

```bash
# Build & Installation
mvn clean install
mvn compile

# Run Application
mvn spring-boot:run

# Testing
mvn test
mvn test -Dtest=CourseServiceTest
mvn test -Dtest=CourseControllerTest

# Production Build
mvn clean package
java -jar target/codecrafthub-1.0.0.jar

# Other Utilities
mvn dependency:tree
mvn clean
```

---

## cURL Testing Examples

```bash
# GET all courses
curl -X GET http://localhost:8080/api/courses

# POST create course
curl -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Spring Boot Basics",
    "description": "Learn Spring Boot fundamentals",
    "targetDate": "2026-04-30",
    "status": "NOT_STARTED"
  }'

# GET specific course (replace COURSE_ID)
curl -X GET http://localhost:8080/api/courses/COURSE_ID

# PUT update course
curl -X PUT http://localhost:8080/api/courses/COURSE_ID \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Spring Boot Advanced",
    "description": "Advanced Spring Boot concepts",
    "targetDate": "2026-05-30",
    "status": "IN_PROGRESS"
  }'

# DELETE course
curl -X DELETE http://localhost:8080/api/courses/COURSE_ID

# GET non-existent course (should return 404)
curl -X GET http://localhost:8080/api/courses/invalid-id
```

---

## References

- Full detailed plan: See `/home/alexandre/.claude/plans/greedy-booping-cake.md`
- Project context: See `docs/context.md`
- Spring Boot docs: <https://spring.io/projects/spring-boot>
- Maven docs: <https://maven.apache.org/>

## Development Workflow

1. **Read CLAUDE.md** - Understand current status and next steps
2. **Reference Development Plan** - Check `/home/alexandre/.claude/plans/greedy-booping-cake.md` for detailed phase information
3. **Create/Modify Files** - Use the file paths and code structure provided
4. **Update Todo List** - Mark tasks as completed as you progress
5. **Test Each Phase** - Verify components work before moving to next phase
6. **Commit to Git** - Create commits for each completed phase

## Important Notes

- **Do NOT create database** - Use JSON file storage only
- **Do NOT add authentication** - Focus on REST API basics
- **Do NOT skip validation** - Validate all inputs in service layer
- **Follow Spring conventions** - Use annotations properly (@RestController, @Service, @Component)
- **Use dependency injection** - Let Spring manage beans with @RequiredArgsConstructor or @Autowired
- **Maintain consistency** - Follow existing code style and patterns throughout

---

**Last Updated:** March 26, 2026
**Status:** Phase 4 Complete, Phase 5 In Progress

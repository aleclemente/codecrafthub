package com.codecrafthub.util;

import com.codecrafthub.exception.CourseException;
import com.codecrafthub.model.Course;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Utility class for handling JSON file storage operations.
 *
 * This class manages reading from and writing to the courses.json file.
 * It uses Jackson ObjectMapper for JSON serialization/deserialization.
 *
 * Methods:
 * - loadCourses(): Load all courses from the JSON file
 * - saveCourses(): Save courses to the JSON file
 * - generateId(): Generate a unique ID for a new course
 */
@Component
public class FileStorageUtil {

    private static final String COURSES_FILE_PATH = "src/main/resources/courses.json";
    private final ObjectMapper objectMapper;

    public FileStorageUtil() {
        this.objectMapper = new ObjectMapper();
        // Register Java 8 date/time module for LocalDate serialization
        this.objectMapper.registerModule(new JavaTimeModule());
        // Configure ObjectMapper to write dates in ISO format
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // Enable pretty printing for readable JSON
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * Load all courses from the JSON file.
     *
     * @return List of courses, or empty list if file is empty or doesn't exist
     * @throws CourseException if file reading fails or JSON is invalid
     */
    public List<Course> loadCourses() throws CourseException {
        try {
            File file = new File(COURSES_FILE_PATH);

            // If file doesn't exist, create it with empty array
            if (!file.exists()) {
                saveCourses(new ArrayList<>());
                return new ArrayList<>();
            }

            // If file is empty, return empty list
            if (file.length() == 0) {
                return new ArrayList<>();
            }

            // Read and deserialize the JSON file
            Course[] coursesArray = objectMapper.readValue(file, Course[].class);
            return new ArrayList<>(List.of(coursesArray));

        } catch (IOException e) {
            throw new CourseException(
                    "Failed to load courses from file: " + e.getMessage(),
                    "FILE_READ_ERROR",
                    500,
                    e
            );
        }
    }

    /**
     * Save courses to the JSON file.
     *
     * @param courses List of courses to save
     * @throws CourseException if file writing fails
     */
    public void saveCourses(List<Course> courses) throws CourseException {
        try {
            File file = new File(COURSES_FILE_PATH);

            // Create parent directories if they don't exist
            file.getParentFile().mkdirs();

            // Write courses to file as JSON
            objectMapper.writeValue(file, courses);

        } catch (IOException e) {
            throw new CourseException(
                    "Failed to save courses to file: " + e.getMessage(),
                    "FILE_WRITE_ERROR",
                    500,
                    e
            );
        }
    }

    /**
     * Generate a unique ID for a new course.
     *
     * @return A unique identifier (UUID)
     */
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}

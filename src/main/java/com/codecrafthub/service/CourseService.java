package com.codecrafthub.service;

import com.codecrafthub.exception.CourseException;
import com.codecrafthub.model.Course;
import com.codecrafthub.util.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing courses.
 *
 * This class contains all business logic for CRUD operations on courses.
 * It handles the interaction between the controller and the file storage layer.
 *
 * Methods:
 * - getAllCourses(): Retrieve all courses
 * - getCourseById(): Retrieve a specific course by ID
 * - createCourse(): Create a new course
 * - updateCourse(): Update an existing course
 * - deleteCourse(): Delete a course
 */
@Service
@RequiredArgsConstructor
public class CourseService {

    private final FileStorageUtil fileStorageUtil;

    /**
     * Retrieve all courses from storage.
     *
     * @return List of all courses
     */
    public List<Course> getAllCourses() {
        return fileStorageUtil.loadCourses();
    }

    /**
     * Retrieve a specific course by its ID.
     *
     * @param id The course ID
     * @return The course if found
     * @throws CourseException if course not found
     */
    public Course getCourseById(String id) throws CourseException {
        validateId(id);

        List<Course> courses = fileStorageUtil.loadCourses();
        Optional<Course> course = courses.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        if (course.isEmpty()) {
            throw new CourseException(
                    "Course not found with ID: " + id,
                    "COURSE_NOT_FOUND",
                    404
            );
        }

        return course.get();
    }

    /**
     * Create a new course.
     *
     * @param course The course to create (without ID)
     * @return The created course with generated ID
     * @throws CourseException if validation fails or save fails
     */
    public Course createCourse(Course course) throws CourseException {
        // Validate course data
        validateCourse(course);

        // Generate unique ID
        String generatedId = fileStorageUtil.generateId();
        course.setId(generatedId);

        // Load existing courses
        List<Course> courses = fileStorageUtil.loadCourses();

        // Add new course
        courses.add(course);

        // Save all courses
        fileStorageUtil.saveCourses(courses);

        return course;
    }

    /**
     * Update an existing course.
     *
     * @param id The course ID to update
     * @param courseUpdate The updated course data
     * @return The updated course
     * @throws CourseException if course not found or validation fails
     */
    public Course updateCourse(String id, Course courseUpdate) throws CourseException {
        validateId(id);
        validateCourse(courseUpdate);

        List<Course> courses = fileStorageUtil.loadCourses();

        // Find and update the course
        Optional<Course> courseOptional = courses.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        if (courseOptional.isEmpty()) {
            throw new CourseException(
                    "Course not found with ID: " + id,
                    "COURSE_NOT_FOUND",
                    404
            );
        }

        Course existingCourse = courseOptional.get();

        // Update fields from courseUpdate
        existingCourse.setName(courseUpdate.getName());
        existingCourse.setDescription(courseUpdate.getDescription());
        existingCourse.setTargetDate(courseUpdate.getTargetDate());
        existingCourse.setStatus(courseUpdate.getStatus());

        // Save all courses
        fileStorageUtil.saveCourses(courses);

        return existingCourse;
    }

    /**
     * Delete a course by its ID.
     *
     * @param id The course ID to delete
     * @throws CourseException if course not found
     */
    public void deleteCourse(String id) throws CourseException {
        validateId(id);

        List<Course> courses = fileStorageUtil.loadCourses();

        // Check if course exists
        boolean courseExists = courses.stream()
                .anyMatch(c -> c.getId().equals(id));

        if (!courseExists) {
            throw new CourseException(
                    "Course not found with ID: " + id,
                    "COURSE_NOT_FOUND",
                    404
            );
        }

        // Remove the course
        courses.removeIf(c -> c.getId().equals(id));

        // Save all courses
        fileStorageUtil.saveCourses(courses);
    }

    /**
     * Validate a course object for required fields.
     *
     * @param course The course to validate
     * @throws CourseException if validation fails
     */
    private void validateCourse(Course course) throws CourseException {
        if (course == null) {
            throw new CourseException(
                    "Course cannot be null",
                    "INVALID_COURSE",
                    400
            );
        }

        if (course.getName() == null || course.getName().trim().isEmpty()) {
            throw new CourseException(
                    "Course name is required",
                    "INVALID_COURSE",
                    400
            );
        }

        if (course.getDescription() == null || course.getDescription().trim().isEmpty()) {
            throw new CourseException(
                    "Course description is required",
                    "INVALID_COURSE",
                    400
            );
        }

        if (course.getTargetDate() == null) {
            throw new CourseException(
                    "Course target date is required",
                    "INVALID_COURSE",
                    400
            );
        }

        if (course.getStatus() == null) {
            throw new CourseException(
                    "Course status is required",
                    "INVALID_COURSE",
                    400
            );
        }

        // Validate that target date is not in the past
        if (course.getTargetDate().isBefore(LocalDate.now())) {
            throw new CourseException(
                    "Course target date cannot be in the past",
                    "INVALID_COURSE",
                    400
            );
        }
    }

    /**
     * Validate that an ID is not null or empty.
     *
     * @param id The ID to validate
     * @throws CourseException if ID is invalid
     */
    private void validateId(String id) throws CourseException {
        if (id == null || id.trim().isEmpty()) {
            throw new CourseException(
                    "Course ID is required",
                    "INVALID_ID",
                    400
            );
        }
    }
}

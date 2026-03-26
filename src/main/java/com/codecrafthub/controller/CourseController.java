package com.codecrafthub.controller;

import com.codecrafthub.model.Course;
import com.codecrafthub.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing courses.
 *
 * This controller handles all HTTP requests related to courses and
 * provides RESTful endpoints for CRUD operations.
 *
 * Base URL: /api/courses
 *
 * Endpoints:
 * - GET    /api/courses          - Get all courses
 * - GET    /api/courses/{id}     - Get specific course
 * - POST   /api/courses          - Create new course
 * - PUT    /api/courses/{id}     - Update course
 * - DELETE /api/courses/{id}     - Delete course
 */
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    /**
     * Get all courses.
     *
     * @return ResponseEntity with list of courses and 200 status
     */
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    /**
     * Get a specific course by ID.
     *
     * @param id The course ID
     * @return ResponseEntity with course data and 200 status
     * @throws com.codecrafthub.exception.CourseException if course not found (404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable String id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    /**
     * Create a new course.
     *
     * @param course The course data (without ID)
     * @return ResponseEntity with created course and 201 status
     * @throws com.codecrafthub.exception.CourseException if validation fails (400)
     */
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdCourse);
    }

    /**
     * Update an existing course.
     *
     * @param id The course ID
     * @param courseUpdate The updated course data
     * @return ResponseEntity with updated course and 200 status
     * @throws com.codecrafthub.exception.CourseException if course not found (404) or validation fails (400)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable String id,
            @RequestBody Course courseUpdate
    ) {
        Course updatedCourse = courseService.updateCourse(id, courseUpdate);
        return ResponseEntity.ok(updatedCourse);
    }

    /**
     * Delete a course by ID.
     *
     * @param id The course ID
     * @return ResponseEntity with 204 No Content status
     * @throws com.codecrafthub.exception.CourseException if course not found (404)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}

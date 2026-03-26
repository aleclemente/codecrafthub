package com.codecrafthub.exception;

/**
 * Custom exception for course-related errors.
 *
 * This exception is thrown when operations on courses fail, such as:
 * - Course not found
 * - Invalid course data
 * - File I/O errors
 */
public class CourseException extends RuntimeException {

    private String errorCode;
    private int httpStatus;

    public CourseException(String message) {
        super(message);
        this.errorCode = "COURSE_ERROR";
        this.httpStatus = 500;
    }

    public CourseException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "COURSE_ERROR";
        this.httpStatus = 500;
    }

    public CourseException(String message, String errorCode, int httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public CourseException(String message, String errorCode, int httpStatus, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}

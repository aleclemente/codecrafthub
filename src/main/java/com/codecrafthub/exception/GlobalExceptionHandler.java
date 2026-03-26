package com.codecrafthub.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * Global exception handler for REST API errors.
 *
 * This class handles all exceptions thrown by controllers and provides
 * consistent error responses in JSON format.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle CourseException
     *
     * @param ex The CourseException
     * @param request The web request
     * @return Error response with appropriate HTTP status
     */
    @ExceptionHandler(CourseException.class)
    public ResponseEntity<ErrorResponse> handleCourseException(
            CourseException ex,
            WebRequest request
    ) {
        HttpStatus httpStatus = HttpStatus.valueOf(ex.getHttpStatus());

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getHttpStatus(),
                ex.getErrorCode(),
                ex.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    /**
     * Handle generic exceptions
     *
     * @param ex The exception
     * @param request The web request
     * @return Error response with 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex,
            WebRequest request
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                500,
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred: " + ex.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Inner class representing the error response structure
     */
    @Data
    @AllArgsConstructor
    public static class ErrorResponse {
        private int status;
        private String errorCode;
        private String message;
        private LocalDateTime timestamp;
        private String path;
    }
}

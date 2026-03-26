package com.codecrafthub.model;

/**
 * Enum representing the status of a course.
 *
 * Possible statuses:
 * - NOT_STARTED: Course has not been started yet
 * - IN_PROGRESS: Course is currently being taken
 * - COMPLETED: Course has been completed
 */
public enum CourseStatus {
    NOT_STARTED("Not Started"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");

    private final String displayName;

    CourseStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

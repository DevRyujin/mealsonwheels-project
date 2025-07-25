package com.merrymeal.mealsonwheels.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskStatus {
    PENDING("Pending"),
    ASSIGNED("Assigned"),
    ACCEPTED("Accepted"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");

    private final String label;

    TaskStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @JsonValue
    public String toJson() {
        return label;
    }
}

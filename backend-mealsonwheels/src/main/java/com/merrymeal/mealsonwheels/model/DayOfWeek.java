package com.merrymeal.mealsonwheels.model;

public enum DayOfWeek {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    private final String label;

    DayOfWeek(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    // For converting label to enum
    public static DayOfWeek fromLabel(String label) {
        for (DayOfWeek day : DayOfWeek.values()) {
            if (day.getLabel().equalsIgnoreCase(label)) {
                return day;
            }
        }
        throw new IllegalArgumentException("Invalid day label: " + label);
    }
}

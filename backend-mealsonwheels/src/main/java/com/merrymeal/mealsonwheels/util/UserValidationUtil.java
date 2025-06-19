package com.merrymeal.mealsonwheels_backend.util;

import com.merrymeal.mealsonwheels_backend.model.User;

public class UserValidationUtil {

    public static void checkApproved(User user) {
        if (!user.isApproved()) {
            throw new RuntimeException(user.getClass().getSimpleName() + " is not approved");
        }
    }

    public static void checkRole(User user, String expectedRole) {
        if (user.getRole() == null || !expectedRole.equalsIgnoreCase(user.getRole().getName())) {
            throw new RuntimeException("User is not a " + expectedRole.toLowerCase());
        }
    }
}

package com.merrymeal.mealsonwheels.util;

import com.merrymeal.mealsonwheels.model.User;

public class UserValidationUtil {

    public static void checkApproved(User user) {
        if (!user.isApproved()) {
            throw new RuntimeException(user.getClass().getSimpleName() + " is not approved");
        }
    }

    public static void checkRole(User user, String expectedRole) {
        if (user.getRole() == null || !expectedRole.equalsIgnoreCase(user.getRole().name())) {
            throw new RuntimeException("User is not a " + expectedRole.toLowerCase());
        }
    }

}

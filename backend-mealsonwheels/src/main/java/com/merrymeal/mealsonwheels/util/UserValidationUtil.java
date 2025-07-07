package com.merrymeal.mealsonwheels.util;

import com.merrymeal.mealsonwheels.model.User;
import com.merrymeal.mealsonwheels.model.Role;

public class UserValidationUtil {

    public static void checkApproved(User user) {
        if (!user.isApproved()) {
            throw new RuntimeException(user.getClass().getSimpleName() + " is not approved");
        }
    }

    public static void checkRole(User user, Role expectedRole) {
        if (user.getRole() != expectedRole) {
            throw new RuntimeException("User is not a " + expectedRole.name().toLowerCase());
        }
    }

}

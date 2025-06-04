package com.merrymeal.mealsonwheels.security;

import com.merrymeal.mealsonwheels.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public User getUser() {
        return user; // for access if needed
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert Role enum to ROLE_ format expected by Spring
        String roleName = "ROLE_" + user.getRole().name();
        return Collections.singletonList(new SimpleGrantedAuthority(roleName));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // using email as a username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // change this if you want to implement logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // change this if you want to implement logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // change this if you want to implement logic
    }

    @Override
    public boolean isEnabled() {
        return true; // change this if you want to implement logic
    }


}

package ru.ghost.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    User,
    Admin,
    Redactor;

    @Override
    public String getAuthority() {
        return name();
    }
}

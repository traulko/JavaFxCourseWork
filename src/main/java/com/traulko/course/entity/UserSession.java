package com.traulko.course.entity;

public final class UserSession {
    private static UserSession instance;
    public String email;

    private UserSession(String email) {
        this.email = email;
    }

    public static UserSession initInstance(String email) {
        if (instance == null) {
            instance = new UserSession(email);
        }
        return instance;
    }

    public static UserSession getInstance() {
        return instance;
    }
}

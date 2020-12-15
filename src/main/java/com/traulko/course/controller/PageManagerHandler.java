package com.traulko.course.controller;

public final class PageManagerHandler {
    private static PageManagerHandler instance;
    public String value;

    private PageManagerHandler(String value) {
        this.value = value;
    }

    public static PageManagerHandler initInstance(String value) {
        instance = new PageManagerHandler(value);
        return instance;
    }

    public static PageManagerHandler getInstance() {
        return instance;
    }
}

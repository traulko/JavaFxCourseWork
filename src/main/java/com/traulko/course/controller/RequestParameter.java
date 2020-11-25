package com.traulko.course.controller;

/**
 * The {@code RequestParameter} class represents request parameter.
 *
 * @author Yan Traulko
 * @version 1.0
 */
public class RequestParameter {
    public static final String COMMAND_NAME = "commandName";
    public static final String USER = "user";
    public static final String PAGE_PATH = "pagePath";

    public static final String USER_NAME = "userName";
    public static final String USER_SURNAME = "userSurname";
    public static final String USER_PATRONYMIC = "userPatronymic";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_PASSWORD = "userPassword";
    public static final String USER_PASSWORD_REPEAT = "userPasswordRepeat";
    public static final String REGISTRATION_PARAMETERS = "registrationParameters";

    //commands
    public static final String SIGN_IN = "sign_in";
    public static final String SIGN_UP = "sign_up";

    private RequestParameter() {
    }
}

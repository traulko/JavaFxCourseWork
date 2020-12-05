package com.traulko.course.validator;

import com.traulko.course.controller.RequestParameter;

import java.util.Map;

public class UserValidator {
    public static final String PASSWORD_REGEX = "^(?=.*[0-9]+.*)(?=.*[a-zA-Z]+.*)[0-9a-zA-Z]{6,16}$";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9]+\\.[A-Za-z0-9]+$";
    private static final String NAME_REGEX = "^\\p{L}{2,25}$";
    private static final String EMPTY_VALUE = "";

    /**
     * Check registration parameters for correct.
     *
     * @param registrationParameters the parameters
     * @return the boolean
     */
    public static boolean isRegistrationParametersCorrect(Map<String, String> registrationParameters) {
        boolean isCorrect = true;
        if (!isEmailValid(registrationParameters.get(RequestParameter.USER_EMAIL))) {
            isCorrect = false;
            registrationParameters.put(RequestParameter.USER_EMAIL, EMPTY_VALUE);
        }
        if (!isNameValid(registrationParameters.get(RequestParameter.USER_NAME))) {
            isCorrect = false;
            registrationParameters.put(RequestParameter.USER_NAME, EMPTY_VALUE);
        }
        if (!isNameValid(registrationParameters.get(RequestParameter.USER_SURNAME))) {
            isCorrect = false;
            registrationParameters.put(RequestParameter.USER_SURNAME, EMPTY_VALUE);
        }
        if (!isNameValid(registrationParameters.get(RequestParameter.USER_PATRONYMIC))) {
            isCorrect = false;
            registrationParameters.put(RequestParameter.USER_PATRONYMIC, EMPTY_VALUE);
        }
        if (!isPasswordValid(registrationParameters.get(RequestParameter.USER_PASSWORD)) ||
                !(registrationParameters.get(RequestParameter.USER_PASSWORD)
                        .equals(registrationParameters.get(RequestParameter.USER_PASSWORD_REPEAT)))) {
            isCorrect = false;
            registrationParameters.put(RequestParameter.USER_PASSWORD, EMPTY_VALUE);
            registrationParameters.put(RequestParameter.USER_PASSWORD_REPEAT, EMPTY_VALUE);
        }
        return isCorrect;
    }

    /**
     * Check password for valid.
     *
     * @param password the password
     * @return the boolean
     */
    public static boolean isPasswordValid(String password) {
        return isStringCorrect(password, PASSWORD_REGEX) && !password.isBlank();
    }

    /**
     * Check email for valid.
     *
     * @param email the email
     * @return the boolean
     */
    public static boolean isEmailValid(String email) {
        return isStringCorrect(email, EMAIL_REGEX) && !email.isBlank();
    }

    /**
     * Check name for valid.
     *
     * @param name the name
     * @return the boolean
     */
    public static boolean isNameValid(String name) {
        return isStringCorrect(name, NAME_REGEX) && !name.isBlank();
    }

    private static boolean isStringCorrect(String line, String regex) {
        boolean result = false;
        if (line != null) {
            result = line.matches(regex);
        }
        return result;
    }
}

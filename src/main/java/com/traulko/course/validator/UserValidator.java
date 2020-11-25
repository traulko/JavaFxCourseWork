package com.traulko.course.validator;

public class UserValidator {
    private static final String PASSWORD_REGEX = "^(?=.*[0-9]+.*)(?=.*[a-zA-Z]+.*)[0-9a-zA-Z]{6,16}$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9]+\\.[A-Za-z0-9]+$";

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

    private static boolean isStringCorrect(String line, String regex) {
        boolean result = false;
        if (line != null) {
            result = line.matches(regex);
        }
        return result;
    }
}

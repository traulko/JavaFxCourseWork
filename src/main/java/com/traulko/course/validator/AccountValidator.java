package com.traulko.course.validator;


/**
 * The {@code CurrencyValidator} class represents user validator.
 *
 * @author Yan Traulko
 * @version 1.0
 */
public class AccountValidator {
    private static final String MONEY_AMOUNT_REGEX = "^[0-9]\\d{0,6}(\\.\\d{0,2})?$";
    private static final String ID_REGEX = "^[1-9]\\d{0,9}$";

    /**
     * Check money amount for valid.
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean isMoneyAmountValid(String value) {
        return isStringCorrect(value, MONEY_AMOUNT_REGEX);
    }

    /**
     * Check id for valid.
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean isIdValueValid(String value) {
        return isStringCorrect(value, ID_REGEX);
    }

    private static boolean isStringCorrect(String line, String regex) {
        boolean result = false;
        if (line != null) {
            result = line.matches(regex);
        }
        return result;
    }
}

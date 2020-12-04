package com.traulko.course.validator;


/**
 * The {@code CurrencyValidator} class represents user validator.
 *
 * @author Yan Traulko
 * @version 1.0
 */
public class ConverterValidator {
    private static final String CURRENCY_VALUE_REGEX = "^[1-9]\\d{0,6}(\\.\\d{0,2})?$";

    /**
     * Check price for valid.
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean isCurrencyValueValid(String value) {
        return isStringCorrect(value, CURRENCY_VALUE_REGEX);
    }

    private static boolean isStringCorrect(String line, String regex) {
        boolean result = false;
        if (line != null) {
            result = line.matches(regex);
        }
        return result;
    }
}

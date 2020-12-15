package com.traulko.course.validator;

public class CreditCardValidator {
    private static final String MONEY_AMOUNT_REGEX = "^[0-9]\\d{0,6}(\\.\\d{0,2})?$";
    private static final String CARD_NUMBER_REGEX = "^[0-9]\\d{15}$";
    private static final String CARD_DATE_REGEX = "^[0-9]\\d{1,6}(\\/\\d{4})?$";
    private static final String CARD_CVV_REGEX = "^[0-9]\\d{2}$";

    public static boolean isCreditCardDateValid(String value) {
        return isStringCorrect(value, CARD_DATE_REGEX);
    }

    public static boolean isCreditCardCvvValid(String value) {
        return isStringCorrect(value, CARD_CVV_REGEX);
    }

    public static boolean isMoneyAmountValid(String value) {
        return isStringCorrect(value, MONEY_AMOUNT_REGEX);
    }

    public static boolean isCreditCardNumberValid(String value) {
        return isStringCorrect(value, CARD_NUMBER_REGEX);
    }

    private static boolean isStringCorrect(String line, String regex) {
        boolean result = false;
        if (line != null) {
            result = line.matches(regex);
        }
        return result;
    }
}

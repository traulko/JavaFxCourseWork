package com.traulko.course.dao;

/**
 * The {@code ColumnName} class represents column name.
 *
 * @author Yan Traulko
 * @version 1.0
 */
public class ColumnName {

    /**
     * Constants for user table
     */
    public static final String USER_ID = "user_id";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_ROLE = "user_role";
    public static final String USER_STATUS = "user_status";
    public static final String USER_NAME = "user_name";
    public static final String USER_SURNAME = "user_surname";
    public static final String USER_PATRONYMIC = "user_patronymic";

    /**
     * Constants for converter table
     */
    public static final String CONVERTER_ID = "converter_id";
    public static final String CONVERTER_BYN = "byn";
    public static final String CONVERTER_USD = "usd";
    public static final String CONVERTER_EUR = "eur";
    public static final String CONVERTER_RUB = "rub";
    public static final String CONVERTER_CREATION_DATE = "converter_creation_date";

    /**
     * Constants for credit card table
     */
    public static final String CREDIT_CARD_ID = "credit_card_id";
    public static final String CREDIT_CARD_NUMBER = "credit_card_number";
    public static final String CREDIT_CARD_SERVICE_END = "credit_card_service_end";
    public static final String CREDIT_CARD_CVV = "credit_card_cvv";

    /**
     * Constants for credit card table
     */
    public static final String ACCOUNT_ID = "account_id";
    public static final String ACCOUNT_CREATION_DATE = "account_creation_date";
    public static final String ACCOUNT_MONEY_AMOUNT = "account_money_amount";
    public static final String ACCOUNT_STATUS = "account_status";

    /**
     * Constants for transactions table
     */
    public static final String TRANSACTION_ID = "transaction_history_id";
    public static final String TRANSACTION_FROM_ACCOUNT = "from_account_id";
    public static final String TRANSACTION_TO_ACCOUNT = "to_account_id";
    public static final String TRANSACTION_MONEY_AMOUNT = "transaction_history_money_amount";
    public static final String TRANSACTION_DATE = "transaction_date";

    private ColumnName() {
    }
}

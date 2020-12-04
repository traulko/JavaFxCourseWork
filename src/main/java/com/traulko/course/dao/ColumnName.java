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

    private ColumnName() {
    }
}

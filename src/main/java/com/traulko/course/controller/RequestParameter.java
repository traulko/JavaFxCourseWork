package com.traulko.course.controller;

/**
 * The {@code RequestParameter} class represents request parameter.
 *
 * @author Yan Traulko
 * @version 1.0
 */
public class RequestParameter {
    public static final String COMMAND_NAME = "commandName";
    public static final String COMMAND_EXCEPTION = "commandException";
    public static final String PAGE_MANAGER_HANDLER_VALUE = "pageManagerHandlerValue";
    public static final String PAGE_PATH = "pagePath";

    public static final String USER = "user";
    public static final String USER_NAME = "userName";
    public static final String USER_SURNAME = "userSurname";
    public static final String USER_PATRONYMIC = "userPatronymic";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_PASSWORD = "userPassword";
    public static final String USER_PASSWORD_REPEAT = "userPasswordRepeat";
    public static final String USER_LIST = "userList";
    public static final String REGISTRATION_PARAMETERS = "registrationParameters";
    public static final String REGISTRATION_TOKEN = "registrationToken";

    public static final String BYN_CURRENCY = "byn";
    public static final String USD_CURRENCY = "usd";
    public static final String EUR_CURRENCY = "eur";
    public static final String RUB_CURRENCY = "rub";
    public static final String FROM_CURRENCY = "fromCurrency";
    public static final String TO_CURRENCY = "toCurrency";
    public static final String CONVERTER = "converter";
    public static final String CONVERTER_VALUE = "converterValue";
    public static final String CONVERTER_RESULT = "convertedResult";
    public static final String CONVERTER_RATIOS_LIST = "converterRatiosList";
    public static final String CONVERTER_MIN_RATIO = "converterMinRatio";
    public static final String CONVERTER_MAX_RATIO = "converterMaxRatio";
    public static final String CONVERTER_RATIO_STEP = "converterRatioStep";
    public static final String CONVERTER_LIST = "converterList";

    public static final String ACCOUNT = "account";
    public static final String ACCOUNT_ID = "accountId";
    public static final String ACCOUNT_LIST = "accountList";
    public static final String ACCOUNT_TOKEN = "accountToken";
    public static final String MONEY_AMOUNT = "moneyAmount";
    public static final String TO_ACCOUNT_ID = "toAccountId";

    public static final String CREDIT_CARD = "creditCard";
    public static final String CREDIT_CARD_CVV = "creditCardCvv";
    public static final String CREDIT_CARD_NUMBER = "creditCardNumber";
    public static final String CREDIT_CARD_END_SERVICE_DATE = "creditCardEndServiceDate";
    public static final String TO_CREDIT_CARD_NUMBER = "toCard";

    public static final String BOOLEAN_RESULT = "booleanResult";

    public static final String TRANSACTION_LIST = "transactionList";

    //commands
    public static final String SIGN_IN = "sign_in";
    public static final String SIGN_UP = "sign_up";
    public static final String CONVERT_VALUE = "convert_value";
    public static final String CONVERTER_RATIOS = "converter_ratios";
    public static final String FIND_USERS = "find_users";
    public static final String BLOCK_USER = "block_user";
    public static final String UNBLOCK_USER = "unblock_user";
    public static final String FIND_CONVERTERS = "find_converters";
    public static final String FIND_LATEST_CONVERTER = "find_latest_converter";
    public static final String ADD_CONVERTER = "add_converter";
    public static final String FORGOT_PASSWORD = "forgot_password";
    public static final String FIND_USER_ACCOUNTS = "find_user_accounts";
    public static final String ADD_ACCOUNT = "add_account";
    public static final String FIND_ALL_ACCOUNTS = "find_all_accounts";
    public static final String FIND_FULL_ACCOUNT_MANAGE_INFO = "find_full_account_manage_info";
    public static final String SAVE_PAGE_MANAGER_HANDLER = "save_page_manager_handler";
    public static final String ACCEPT_CREATION_ACCOUNT = "accept_creation_account";
    public static final String FIND_ALL_TRANSACTIONS = "find_all_transactions";
    public static final String UNBLOCK_ACCOUNT = "unblock_account";
    public static final String BLOCK_ACCOUNT = "block_account";
    public static final String CLOSE_ACCOUNT = "close_account";
    public static final String FILL_ACCOUNT_BALANCE = "fill_account_balance";
    public static final String TRANSFER_TO_ACCOUNT = "transfer_to_account";
    public static final String TRANSFER_FROM_CARD_TO_CARD = "transfer_from_card_to_card";

    private RequestParameter() {
    }
}

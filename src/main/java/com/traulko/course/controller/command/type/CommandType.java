package com.traulko.course.controller.command.type;

import com.traulko.course.controller.command.CustomCommand;
import com.traulko.course.controller.command.impl.*;

/**
 * The {@code CommandType} enum represents command type.
 *
 * @author Yan Traulko
 * @version 1.0
 */
public enum CommandType {
    SIGN_IN(new SingInCommand()),
    SIGN_UP(new SignUpCommand()),
    CONVERT_VALUE(new ConvertValueCommand()),
    CONVERTER_RATIOS(new ConverterRatiosCommand()),
    FIND_USERS(new FindUsersCommand()),
    BLOCK_USER(new BlockUserCommand()),
    UNBLOCK_USER(new UnblockUserCommand()),
    FIND_CONVERTERS(new FindConvertersCommand()),
    FIND_LATEST_CONVERTER(new FindLatestConverterCommand()),
    ADD_CONVERTER(new AddConverterCommand()),
    FORGOT_PASSWORD(new ForgotPasswordCommand()),
    FIND_USER_ACCOUNTS(new FindUserAccountsCommand()),
    ADD_ACCOUNT(new AddAccountCommand()),
    FIND_ALL_ACCOUNTS(new FindAllAccountsCommand()),
    FIND_FULL_ACCOUNT_MANAGE_INFO(new FindFullAccountManageInfoCommand()),
    SAVE_PAGE_MANAGER_HANDLER(new SavePageManagerHandlerCommand()),
    ACCEPT_CREATION_ACCOUNT(new AcceptCreationAccountCommand()),
    FIND_ALL_TRANSACTIONS(new FindAllTransactionsCommand()),
    UNBLOCK_ACCOUNT(new UnblockAccountCommand()),
    BLOCK_ACCOUNT(new BlockAccountCommand()),
    CLOSE_ACCOUNT(new CloseAccountCommand()),
    FILL_ACCOUNT_BALANCE(new FillAccountBalanceCommand()),
    TRANSFER_TO_ACCOUNT(new TransferToAccountCommand()),
    TRANSFER_FROM_CARD_TO_CARD(new TransferFromCardToCardCommand());

    private final CustomCommand command;

    CommandType(CustomCommand command) {
        this.command = command;
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public CustomCommand getCommand() {
        return command;
    }
}

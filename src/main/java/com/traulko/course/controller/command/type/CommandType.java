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
    FORGOT_PASSWORD(new ForgotPasswordCommand());

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

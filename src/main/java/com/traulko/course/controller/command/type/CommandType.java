package com.traulko.course.controller.command.type;

import com.traulko.course.controller.command.CustomCommand;
import com.traulko.course.controller.command.impl.SingInCommand;

/**
 * The {@code CommandType} enum represents command type.
 *
 * @author Yan Traulko
 * @version 1.0
 */
public enum CommandType {
    SIGN_IN(new SingInCommand());

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

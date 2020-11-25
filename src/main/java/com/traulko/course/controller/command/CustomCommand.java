package com.traulko.course.controller.command;

import com.traulko.course.entity.Batch;

/**
 * The {@code CustomCommand} interface represents command.
 *
 * @author Yan Traulko
 * @version 1.0
 */
public interface CustomCommand {

    /**
     * Execute command.
     *
     * @param request the request
     * @return the String containing page path
     */
    String execute(Batch request);
}

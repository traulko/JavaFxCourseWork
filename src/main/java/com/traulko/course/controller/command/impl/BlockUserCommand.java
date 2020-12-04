package com.traulko.course.controller.command.impl;

import com.traulko.course.controller.RequestParameter;
import com.traulko.course.controller.command.CustomCommand;
import com.traulko.course.entity.Batch;
import com.traulko.course.exception.ServiceException;
import com.traulko.course.service.UserService;
import com.traulko.course.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockUserCommand implements CustomCommand {
    private static final UserService userService = new UserServiceImpl();
    private static final Logger LOGGER = LogManager.getLogger(BlockUserCommand.class);

    @Override
    public Batch execute(Batch requestBatch) {
        boolean result = false;
        String email = requestBatch.getBatchMap().get(RequestParameter.USER_EMAIL).toString();
        try {
            result = userService.blockUser(email);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Error while blocking user", e);
        }
        Batch batch = new Batch();
        batch.getBatchMap().put(RequestParameter.BOOLEAN_RESULT, result);
        return batch;
    }
}

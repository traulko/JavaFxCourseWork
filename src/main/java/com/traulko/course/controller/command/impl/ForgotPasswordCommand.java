package com.traulko.course.controller.command.impl;

import com.traulko.course.controller.RequestParameter;
import com.traulko.course.controller.command.CustomCommand;
import com.traulko.course.entity.Batch;
import com.traulko.course.entity.User;
import com.traulko.course.exception.ServiceException;
import com.traulko.course.service.UserService;
import com.traulko.course.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class ForgotPasswordCommand implements CustomCommand {
    private static final UserService userService = new UserServiceImpl();
    private static final Logger LOGGER = LogManager.getLogger(ForgotPasswordCommand.class);

    @Override
    public Batch execute(Batch requestBatch) {
        boolean result = false;
        String email = requestBatch.getBatchMap().get(RequestParameter.USER_EMAIL).toString();
        String token = requestBatch.getBatchMap().get(RequestParameter.REGISTRATION_TOKEN).toString();
        String password = requestBatch.getBatchMap().get(RequestParameter.USER_PASSWORD).toString();
        String passwordRepeat = requestBatch.getBatchMap().get(RequestParameter.USER_PASSWORD_REPEAT).toString();
        try {
            Optional<User> optionalUserByEmail = userService.findUserByEmail(email);
            if (optionalUserByEmail.isPresent()) {
                Optional<User> optionalUserByToken = userService.findUserByToken(token);
                if (optionalUserByToken.isPresent()) {
                    if (optionalUserByEmail.get().equals(optionalUserByToken.get())) {
                        result = userService.updateUserPassword(email, password, passwordRepeat);
                    }
                }
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Error while updating user password", e);
        }
        Batch batch = new Batch();
        batch.getBatchMap().put(RequestParameter.BOOLEAN_RESULT, result);
        return batch;
    }
}

package com.traulko.course.controller.command.impl;

import com.traulko.course.controller.PagePath;
import com.traulko.course.controller.RequestParameter;
import com.traulko.course.controller.command.CustomCommand;
import com.traulko.course.entity.Batch;
import com.traulko.course.entity.User;
import com.traulko.course.entity.UserSession;
import com.traulko.course.exception.ServiceException;
import com.traulko.course.service.UserService;
import com.traulko.course.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SignUpCommand implements CustomCommand {
    private static final UserService userService = new UserServiceImpl();
    private static final Logger LOGGER = LogManager.getLogger(SignUpCommand.class);

    @Override
    public Batch execute(Batch requestBatch) {
        String page = PagePath.REGISTRATION;
        String name = requestBatch.getBatchMap().get(RequestParameter.USER_NAME).toString();
        String surname = requestBatch.getBatchMap().get(RequestParameter.USER_SURNAME).toString();
        String patronymic = requestBatch.getBatchMap().get(RequestParameter.USER_PATRONYMIC).toString();
        String email = requestBatch.getBatchMap().get(RequestParameter.USER_EMAIL).toString();
        String password = requestBatch.getBatchMap().get(RequestParameter.USER_PASSWORD).toString();
        String passwordRepeat = requestBatch.getBatchMap().get(RequestParameter.USER_PASSWORD_REPEAT).toString();
        Map<String, String> registrationParameters = new HashMap<>();
        registrationParameters.put(RequestParameter.USER_NAME, name);
        registrationParameters.put(RequestParameter.USER_SURNAME, surname);
        registrationParameters.put(RequestParameter.USER_PATRONYMIC, patronymic);
        registrationParameters.put(RequestParameter.USER_EMAIL, email);
        registrationParameters.put(RequestParameter.USER_PASSWORD, password);
        registrationParameters.put(RequestParameter.USER_PASSWORD_REPEAT, passwordRepeat);
        try {
            if (userService.addUser(registrationParameters)) {
                page = PagePath.AUTHORIZATION;
                // TODO: 26.11.2020 send message
            } else {
                page = PagePath.REGISTRATION;
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Error while login user", e);
        }
        Batch batch = new Batch();
        batch.getBatchMap().put(RequestParameter.PAGE_PATH, page);
        batch.getBatchMap().put(RequestParameter.REGISTRATION_PARAMETERS, registrationParameters);
        return batch;
    }
}

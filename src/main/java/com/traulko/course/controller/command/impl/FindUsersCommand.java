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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FindUsersCommand implements CustomCommand {
    private static final UserService userService = new UserServiceImpl();
    private static final Logger LOGGER = LogManager.getLogger(FindUsersCommand.class);

    @Override
    public Batch execute(Batch requestBatch) {
        List<User> userList = new ArrayList<>();
        try {
            userList = userService.findAllUsers();
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Error while finding all users", e);
        }
        Batch batch = new Batch();
        batch.getBatchMap().put(RequestParameter.USER_LIST, userList);
        return batch;
    }
}

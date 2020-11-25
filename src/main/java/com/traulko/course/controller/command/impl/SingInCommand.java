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

import java.util.Optional;

public class SingInCommand implements CustomCommand {
    private static final UserService userService = new UserServiceImpl();
    private static final Logger LOGGER = LogManager.getLogger(SingInCommand.class);

    @Override
    public Batch execute(Batch requestBatch) {
        String page = PagePath.AUTHORIZATION;
        String email = requestBatch.getBatchMap().get(RequestParameter.USER_EMAIL).toString();
        String password = requestBatch.getBatchMap().get(RequestParameter.USER_PASSWORD).toString();
        try {
            if (userService.isUserExists(email, password)) {
                UserSession.initInstance(email);
                Optional<User> optionalUser = userService.findUserByEmail(email);
                User user = optionalUser.get();
                if (user.getStatus() == User.Status.ENABLE) {
                    if (user.getRole() == User.Role.USER) {
                        page = PagePath.USER_HOME;
                    } else if (user.getRole() == User.Role.ADMIN) {
                        page = PagePath.ADMIN_HOME;
                    }
                }
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Error while login user", e);
        }
        Batch batch = new Batch();
        batch.getBatchMap().put(RequestParameter.PAGE_PATH, page);
        return batch;
    }
}

package com.traulko.course.service.impl;

import com.traulko.course.dao.UserDao;
import com.traulko.course.dao.impl.UserDaoImpl;
import com.traulko.course.entity.User;
import com.traulko.course.exception.DaoException;
import com.traulko.course.exception.ServiceException;
import com.traulko.course.service.UserService;
import com.traulko.course.util.CustomCipher;
import com.traulko.course.validator.UserValidator;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = UserDaoImpl.getInstance();

    @Override
    public boolean isUserExists(String email, String password) {
        boolean result = false;
        try {
            if (UserValidator.isEmailValid(email) && UserValidator.isPasswordValid(password)) {
                CustomCipher cipher = new CustomCipher();
                String encryptedPassword = cipher.encrypt(password);
                Optional<User> optionalUser = userDao.findByEmailAndPassword(email, encryptedPassword);
                result = optionalUser.isPresent();
            }
        } catch (NoSuchAlgorithmException | DaoException e) {
            throw new SecurityException("Error while finding user by email and password", e);
        }
        return result;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws ServiceException {
        Optional<User> optionalUser;
        try {
            optionalUser = userDao.findByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException("Error while getting user by email", e);
        }
        return optionalUser;
    }
}

package com.traulko.course.service.impl;

import com.traulko.course.builder.UserBuilder;
import com.traulko.course.controller.RequestParameter;
import com.traulko.course.dao.UserDao;
import com.traulko.course.dao.impl.UserDaoImpl;
import com.traulko.course.entity.User;
import com.traulko.course.exception.DaoException;
import com.traulko.course.exception.ServiceException;
import com.traulko.course.service.UserService;
import com.traulko.course.util.CustomCipher;
import com.traulko.course.validator.UserValidator;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final String EMPTY_VALUE = "";
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

    @Override
    public boolean blockUser(String email) throws ServiceException {
        boolean result = false;
        try {
            if (UserValidator.isEmailValid(email)) {
                result = userDao.block(email);
            }
        } catch (DaoException e) {
            throw new ServiceException("Error while blocking user", e);
        }
        return result;
    }

    @Override
    public boolean unblockUser(String email) throws ServiceException {
        boolean result = false;
        try {
            if (UserValidator.isEmailValid(email)) {
                result = userDao.unblock(email);
            }
        } catch (DaoException e) {
            throw new ServiceException("Error while unblocking user", e);
        }
        return result;
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        List<User> userList = new ArrayList<>();
        try {
            userList = userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Error while finding all users", e);
        }
        return userList;
    }

    @Override
    public boolean addUser(Map<String, String> registrationParameters) throws ServiceException {
        boolean result = false;
        String name = registrationParameters.get(RequestParameter.USER_NAME);
        String surname = registrationParameters.get(RequestParameter.USER_SURNAME);
        String patronymic = registrationParameters.get(RequestParameter.USER_PATRONYMIC);
        String email = registrationParameters.get(RequestParameter.USER_EMAIL);
        String password = registrationParameters.get(RequestParameter.USER_PASSWORD);
        if (UserValidator.isRegistrationParametersCorrect(registrationParameters)) {
            if (isEmailFree(email)) {
                try {
                    String encryptedPassword = CustomCipher.encrypt(password);
                    UserBuilder userBuilder = new UserBuilder();
                    userBuilder.setName(name);
                    userBuilder.setSurname(surname);
                    userBuilder.setPatronymic(patronymic);
                    userBuilder.setEmail(email);
                    userBuilder.setRole(User.Role.USER);
                    userBuilder.setStatus(User.Status.ENABLE);
                    User user = userBuilder.getUser();
                    result = userDao.add(user, encryptedPassword);
                } catch (DaoException | NoSuchAlgorithmException e) {
                    throw new ServiceException("Error while adding user", e);
                }
            } else {
                registrationParameters.put(RequestParameter.USER_EMAIL, EMPTY_VALUE);
            }
        }
        return result;
    }

    private boolean isEmailFree(String email) throws ServiceException {
        boolean result = false;
        try {
            if (UserValidator.isEmailValid(email)) {
                Optional<User> optionalUser = userDao.findByEmail(email);
                result = optionalUser.isEmpty();
            }
        } catch (DaoException e) {
            throw new ServiceException("Error while checking free email for presence in database", e);
        }
        return result;
    }

}

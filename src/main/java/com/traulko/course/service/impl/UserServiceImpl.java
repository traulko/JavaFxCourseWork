package com.traulko.course.service.impl;

import com.traulko.course.builder.EntityTokenBuilder;
import com.traulko.course.builder.UserBuilder;
import com.traulko.course.controller.RequestParameter;
import com.traulko.course.dao.TransactionManager;
import com.traulko.course.dao.UserDao;
import com.traulko.course.dao.impl.UserDaoImpl;
import com.traulko.course.entity.EntityToken;
import com.traulko.course.entity.User;
import com.traulko.course.exception.DaoException;
import com.traulko.course.exception.ServiceException;
import com.traulko.course.exception.TransactionException;
import com.traulko.course.service.UserService;
import com.traulko.course.util.CustomCipher;
import com.traulko.course.validator.UserValidator;

import java.security.NoSuchAlgorithmException;
import java.util.*;

public class UserServiceImpl implements UserService {
    private static final String EMPTY_VALUE = "";
    private final UserDao userDao = UserDaoImpl.getInstance();
    private final TransactionManager transactionManager = TransactionManager.getInstance();

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
        Optional<User> optionalUser = Optional.empty();
        try {
            if (UserValidator.isEmailValid(email)) {
                optionalUser = userDao.findBy(email);
            }
        } catch (DaoException e) {
            throw new ServiceException("Error while getting user by email", e);
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findUserByAccountNumber(Integer accountNumber) throws ServiceException {
        Optional<User> optionalUser = Optional.empty();
        try {
            optionalUser = userDao.findBy(accountNumber);
        } catch (DaoException e) {
            throw new ServiceException("Error while getting user by account number", e);
        }
        return optionalUser;
    }

    @Override
    public boolean updateUserPassword(String email, String password, String passwordRepeat) throws ServiceException {
        boolean result = false;
        try {
            if (UserValidator.isEmailValid(email) && UserValidator.isPasswordValid(password)
                    && password.equals(passwordRepeat)) {
                String encryptedPassword = CustomCipher.encrypt(password);
                result = userDao.updatePassword(email, encryptedPassword);
            }
        } catch (NoSuchAlgorithmException | DaoException e) {
            throw new ServiceException("Error while updating user password", e);
        }
        return result;
    }

    @Override
    public Optional<User> findUserByToken(String token) throws ServiceException {
        Optional<User> optionalUser;
        try {
            String encryptedToken = CustomCipher.encrypt(token);
            optionalUser = userDao.findByToken(encryptedToken);
        } catch (DaoException | NoSuchAlgorithmException e) {
            throw new ServiceException("Error while getting user by token", e);
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
                    EntityTokenBuilder entityTokenBuilder = new EntityTokenBuilder();
                    String generatedToken = UUID.randomUUID().toString();
                    entityTokenBuilder.setTokenUuid(generatedToken);
                    EntityToken entityToken = entityTokenBuilder.getToken();
                    result = transactionManager.addUserAndToken(user, encryptedPassword, entityToken);
                    if (result) {
                        registrationParameters.put(RequestParameter.REGISTRATION_TOKEN, generatedToken);
                    }
                } catch (NoSuchAlgorithmException | TransactionException e) {
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
                Optional<User> optionalUser = userDao.findBy(email);
                result = optionalUser.isEmpty();
            }
        } catch (DaoException e) {
            throw new ServiceException("Error while checking free email for presence in database", e);
        }
        return result;
    }

}

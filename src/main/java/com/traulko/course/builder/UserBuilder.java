package com.traulko.course.builder;

import com.traulko.course.entity.User;

/**
 * The {@code UserBuilder} class represents user builder.
 *
 * @author Yan Traulko
 * @version 1.0
 */
public class UserBuilder {
    private Integer userId;
    private String email;
    private String name;
    private String surname;
    private String patronymic;
    private User.Role role;
    private User.Status status;

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets first name.
     *
     * @param name the first name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets second name.
     *
     * @param surname the second name
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Sets patronymic.
     *
     * @param patronymic the patronymic
     */
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(User.Role role) {
        this.role = role;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(User.Status status) {
        this.status = status;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return new User(userId, email, name, surname,
                patronymic, role, status);
    }
}

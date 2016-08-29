package com.epam.newsmanagement.common.service;

import com.epam.newsmanagement.common.dao.UserDAO;
import com.epam.newsmanagement.common.entity.User;
import com.epam.newsmanagement.common.exception.service.ServiceException;

/**
 * Service which provide API for operation with {@link UserDAO}
 */
public interface UserService extends EntityService<User, Long> {

    /**
     * Use {@link User} DAO layer for checking whether user was registered in system
     * @param userId is id checking user
     * @return true or false according to was or wasn't user register
     * @throws ServiceException if some problems on DAO layer
     */
    boolean checkUser(Long userId) throws ServiceException;

    /**
     * Use {@link User} DAO layer for getting user by login and password from database
     * @param login is user
     * @param password is user
     * @return user login and password that matches
     * @throws ServiceException if some problems on DAO layer
     */
    User getUser(String login, String password) throws ServiceException;
}
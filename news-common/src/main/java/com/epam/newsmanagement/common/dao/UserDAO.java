package com.epam.newsmanagement.common.dao;

import com.epam.newsmanagement.common.entity.User;
import com.epam.newsmanagement.common.exception.dao.DAOException;

/**
 * methods for {@link User} for operating with database
 */
public interface UserDAO extends EntityDAO<User, Long> {

    /**
     * Check whether user was registered in system
     * @param userId is id checking user
     * @return true or false according to was or wasn't user register
     * @throws DAOException if some problems in database
     */
    boolean checkUser(Long userId) throws DAOException;

    /**
     * get user by login and password from database
     * @param login is user
     * @param password is user
     * @return user login and password that matches
     * @throws DAOException if some problems in database
     */
    User getUser(String login, String password) throws DAOException;


}
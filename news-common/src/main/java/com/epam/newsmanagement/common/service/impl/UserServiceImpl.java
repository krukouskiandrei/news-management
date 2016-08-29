package com.epam.newsmanagement.common.service.impl;

import com.epam.newsmanagement.common.dao.UserDAO;
import com.epam.newsmanagement.common.entity.User;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import com.epam.newsmanagement.common.exception.service.ServiceException;
import com.epam.newsmanagement.common.service.EntityService;
import com.epam.newsmanagement.common.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Implementing of {@link UserService}
 */
@Service
@Transactional(rollbackFor = ServiceException.class)
public class UserServiceImpl implements UserService{

    private final static Logger logger = LogManager.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDAO userDAO;

    /**
     * Implementation of {@link EntityService#create(Serializable)}
     * @param user is parameter which need to insert in database
     * @return parameter user_id from database
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    public Long create(User user) throws ServiceException{
        try {
            return userDAO.create(user);
        }catch (DAOException e){
            logger.error("Failed to create user" + user, e);
            throw new ServiceException(e);
        }
    }

    /**
     * Implementation of {@link EntityService#getById(Long)}
     * @param userId is the field on which the search
     * @return {@link User}
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    public User getById(Long userId) throws ServiceException{
        try {
            return userDAO.getById(userId);
        }catch (DAOException e){
            logger.error("Failed to get user by user id where userId=" + userId, e);
            throw new ServiceException(e);
        }
    }

    /**
     * Implementation of {@link EntityService#getAll()}
     * @return list all users from database
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    public List<User> getAll() throws ServiceException{
        try {
            return userDAO.getAll();
        }catch (DAOException e){
            logger.error("Failed to get all users", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Implementation of {@link EntityService#countAll()}
     * @return the number users in database
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    public Long countAll() throws ServiceException{
        try {
            return userDAO.countAll();
        }catch (DAOException e){
            logger.error("Failed to count users", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Implementation {@link EntityService#update(Serializable)}
     * @param user is entity which need to update
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    public void update(User user) throws ServiceException{
        try {
            userDAO.update(user);
        }catch (DAOException e){
            logger.error("Failed to update user" + user, e);
            throw new ServiceException(e);
        }
    }

    /**
     * Implementation {@link EntityService#delete(Long)}
     * @param userId is the field on which the search
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    public void delete(Long userId) throws ServiceException{
        try {
            userDAO.delete(userId);
        }catch (DAOException e){
            logger.error("Failed to delete user by user id where userId=" + userId, e);
            throw new ServiceException(e);
        }
    }

    /**
     * Implementation {@link UserService#checkUser(Long)}
     * @param userId is id checking user
     * @return yes or no it is depends from contains or didn't contains user in database
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    public boolean checkUser(Long userId) throws ServiceException{
        try {
            return userDAO.checkUser(userId);
        }catch (DAOException e){
            logger.error("Failed to check user by user id where userId=" + userId, e);
            throw new ServiceException(e);
        }
    }

    /**
     * Implementation {@link UserDAO#getUser(String, String)}
     * @param login is user
     * @param password is user
     * @return user which login and password matches with login in password in database
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    public User getUser(String login, String password) throws ServiceException{
        try {
            return userDAO.getUser(login, password);
        }catch (DAOException e){
            logger.error("Failed to get user by login and password where login=" + login +", password=" + password, e);
            throw new ServiceException(e);
        }
    }
}
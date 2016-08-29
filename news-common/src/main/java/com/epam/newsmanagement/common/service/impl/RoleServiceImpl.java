package com.epam.newsmanagement.common.service.impl;

import com.epam.newsmanagement.common.dao.RoleDAO;
import com.epam.newsmanagement.common.entity.Role;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import com.epam.newsmanagement.common.exception.service.ServiceException;
import com.epam.newsmanagement.common.service.EntityService;
import com.epam.newsmanagement.common.service.RoleService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Implementing of {@link RoleService}
 */
@Service
@Transactional(rollbackFor = ServiceException.class)
public class RoleServiceImpl implements RoleService {

    private final static Logger logger = LogManager.getLogger(RoleServiceImpl.class);
    @Autowired
    private RoleDAO roleDAO;

    /**
     * Implementation of {@link EntityService#create(Serializable)}
     * @param role is parameter which need to insert in database
     * @return parameter user_id from database
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    public Long create(Role role) throws ServiceException{
        try {
            return roleDAO.create(role);
        }catch (DAOException e){
            logger.error("Failed to create role" + role, e);
            throw new ServiceException(e);
        }
    }

    /**
     * Implementation of {@link EntityService#getById(Long)}
     * @param userId is the field on which the search
     * @return {@link Role}
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    public Role getById(Long userId) throws ServiceException{
        try {
            return roleDAO.getById(userId);
        }catch (DAOException e){
            logger.error("Failed to get role by user id where userId=" + userId, e);
            throw new ServiceException(e);
        }
    }

    /**
     * Implementation of {@link EntityService#getAll()}
     * @return list all roles from database
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    public List<Role> getAll() throws ServiceException{
        try {
            return roleDAO.getAll();
        }catch (DAOException e){
            logger.error("Failed to get all roles", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Implementation of {@link EntityService#countAll()}
     * @return the number roles in database
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    public Long countAll() throws ServiceException{
        try {
            return roleDAO.countAll();
        }catch (DAOException e){
            logger.error("Failed to count roles", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Implementation {@link EntityService#update(Serializable)}
     * @param role is entity which need to update
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    public void update(Role role) throws ServiceException{
        try {
            roleDAO.update(role);
        }catch (DAOException e){
            logger.error("Failed to update role" + role, e);
            throw new ServiceException(e);
        }
    }

    /**
     * Implementation {@link EntityService#delete(Long)}
     * @param userID is the field on which the search
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    public void delete(Long userID) throws ServiceException{
        try {
            roleDAO.delete(userID);
        }catch (DAOException e){
            logger.error("Failed to delete role by user id where userId=" + userID, e);
            throw new ServiceException(e);
        }
    }
}
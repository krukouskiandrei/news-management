package com.epam.newsmanagement.common.dao.impl;

import com.epam.newsmanagement.common.dao.EntityDAO;
import com.epam.newsmanagement.common.dao.RoleDAO;
import com.epam.newsmanagement.common.dao.mapper.RoleMapper;
import com.epam.newsmanagement.common.entity.Role;
import com.epam.newsmanagement.common.exception.dao.DAOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.List;

/**
 * Implementing of {@link RoleDAO}
 */
@Repository
public class RoleDAOImpl implements RoleDAO{

    private final static String SQL_INSERT_ROLE = "INSERT INTO ROLES (USER_ID, ROLE_NAME) "
    											+ "VALUES(?, ?)";
    private final static String SQL_SELECT_ROLE_BY_USER_ID = "SELECT USER_ID, ROLE_NAME "
    														+ "FROM ROLES "
    														+ "WHERE USER_ID = ?";
    private final static String SQL_SELECT_ALL_ROLES = "SELECT USER_ID, ROLE_NAME "
    													+ "FROM ROLES";
    private final static String SQL_SELECT_COUNT_ALL_ROLES = "SELECT COUNT(1) "
    														+ "FROM ROLES";
    private final static String SQL_UPDATE_ROLE_BY_USER_ID = "UPDATE ROLES "
    														+ "SET ROLE_NAME = ? "
    														+ "WHERE USER_ID = ?";
    private final static String SQL_DELETE_ROLE_BY_USER_ID = "DELETE FROM ROLES "
    														+ "WHERE USER_ID = ?";
    
    private final static Logger logger = LogManager.getLogger(RoleDAOImpl.class);
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
    	this.dataSource = dataSource;
    	this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    
    /**
     * Implementation of {@link EntityDAO#create(Serializable)}
     * @param role is parameter which need to insert in database
     * @return parameter user_id from database
     * @throws DAOException if some problems in database
     */
   @Override
    public Long create(Role role) throws DAOException{
	   if(role != null){
		   jdbcTemplate.update(SQL_INSERT_ROLE, 
				   new Object[]{role.getUserId(), role.getRoleName()});
		   logger.debug("Role=" + role + " inserted in table Role;");
		   return role.getUserId();
	   }
	   throw new DAOException();
   }

    /**
     * Implementation of {@link EntityDAO#getById(Long)}
     * @param userId is the field on which the search
     * @return {@link Role}
     * @throws DAOException if some problems in database
     */
    @Override
    public Role getById(Long userId) throws DAOException{
        Role role = null;
        if(userId != null){
        	role = jdbcTemplate.queryForObject(SQL_SELECT_ROLE_BY_USER_ID, 
        			new Object[]{userId}, new RoleMapper());
        	logger.debug("Role selected by userId=" + userId + ";");
        }
        return role;
    }

    /**
     * Implementation of {@link EntityDAO#getAll()}
     * @return list all roles from database
     * @throws DAOException if some problems in database
     */
    @Override
    public List<Role> getAll() throws DAOException{
        List<Role> roleList = null;
        roleList = jdbcTemplate.query(SQL_SELECT_ALL_ROLES, 
        		new RoleMapper());
        logger.debug("All roles selected;");
        return roleList;
    }

    /**
     * Implementation of {@link EntityDAO#countAll()}
     * @return the number roles in database
     * @throws DAOException if some problems in database
     */
    public Long countAll() throws DAOException{
        Long count = null;
        count = jdbcTemplate.queryForObject(SQL_SELECT_COUNT_ALL_ROLES, Long.class);
        logger.debug("All roles counted");
        return count;
    }

    /**
     * Implementation {@link EntityDAO#update(Serializable)}
     * @param role is entity which need to update
     * @throws DAOException if some problems in database
     */
    @Override
    public void update(Role role) throws DAOException{
        if(role != null){
        	jdbcTemplate.update(SQL_UPDATE_ROLE_BY_USER_ID, 
        			new Object[]{role.getRoleName(), role.getUserId()});
        	logger.debug("Role=" + role + " updated in table Role;");
        }
    }

    /**
     * Implementation {@link EntityDAO#delete(Long)}
     * @param userID is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public void delete(Long userID) throws DAOException{
        if(userID != null){
        	jdbcTemplate.update(SQL_DELETE_ROLE_BY_USER_ID, new Object[]{userID});
        	logger.debug("Role for userId=" + userID + " deleted;");
        }
    }
}
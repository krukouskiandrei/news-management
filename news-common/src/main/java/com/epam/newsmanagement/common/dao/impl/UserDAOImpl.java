package com.epam.newsmanagement.common.dao.impl;

import com.epam.newsmanagement.common.dao.EntityDAO;
import com.epam.newsmanagement.common.dao.UserDAO;
import com.epam.newsmanagement.common.dao.mapper.LongMapper;
import com.epam.newsmanagement.common.dao.mapper.UserMapper;
import com.epam.newsmanagement.common.entity.User;
import com.epam.newsmanagement.common.exception.dao.DAOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementing of {@link UserDAO}
 */
@Repository
public class UserDAOImpl implements UserDAO {

    private final static String SQL_INSER_USER = "INSERT INTO CUSTOMER (USER_ID, USER_NAME, LOGIN, PASSWORD)" +
            									" VALUES(CUSTOMER_SEQUENCE.NEXTVAL, ?, ?, ?)";
    private final static String SQL_SELECT_USER_BY_USER_ID = "SELECT USER_ID, USER_NAME, LOGIN, PASSWORD "
    														+ "FROM CUSTOMER "
    														+ "WHERE USER_ID = ?";
    private final static String SQL_SELECT_ALL_USERS = "SELECT USER_ID, USER_NAME, LOGIN, PASSWORD "
    													+ "FROM CUSTOMER";
    private final static String SQL_SELECT_COUNT_ALL_USERS = "SELECT COUNT(1) "
    														+ "FROM CUSTOMER";
    private final static String SQL_UPDATE_USER_BY_USER_ID = "UPDATE CUSTOMER "
    														+ "SET USER_NAME = ?, LOGIN = ?, PASSWORD = ? "
    														+ "WHERE USER_ID = ?";
    private final static String SQL_DELETE_USER_BY_USER_ID = "DELETE FROM CUSTOMER "
    														+ "WHERE USER_ID = ?";
    private final static String SQL_CHECK_USER_BY_USER_ID = "SELECT USER_ID "
    														+ "FROM CUSTOMER WHERE USER_ID = ?";
    private final static String SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD = "SELECT USER_ID, USER_NAME, LOGIN, PASSWORD "
    																	+ "FROM CUSTOMER "
    																	+ "WHERE LOGIN = ? AND PASSWORD = ?";
    
    private final static Logger logger = LogManager.getLogger(UserDAOImpl.class);
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public void setDataSource(DataSource dataSource){
    	this.dataSource = dataSource;
    	this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    /**
     * Implementation of {@link EntityDAO#create(Serializable)}
     * @param user is parameter which need to insert in database
     * @return parameter user_id from database
     * @throws DAOException if some problems in database
     */
    @Override
    public Long create(User user) throws DAOException{
    	KeyHolder keyHolder = new GeneratedKeyHolder();
    	if(user != null){
    		jdbcTemplate.update(
    				new PreparedStatementCreator() {						
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement pst = con.prepareStatement(SQL_INSER_USER, new String[] {"user_id"});
							pst.setString(1, user.getUserName());
							pst.setString(2, user.getLogin());
							pst.setString(3, user.getPassword());
							return pst;
						}
					}, 
    				keyHolder);
    	}
    	return (Long)keyHolder.getKey().longValue();
    }
    
    /**
     * Implementation of {@link EntityDAO#getById(Long)}
     * @param userId is the field on which the search
     * @return {@link User}
     * @throws DAOException if some problems in database
     */
    @Override
    public User getById(Long userId) throws DAOException{
        User user = null;
        if(userId != null){
        	user = jdbcTemplate.queryForObject(SQL_SELECT_USER_BY_USER_ID, 
        			new Object[]{userId}, new UserMapper());
        	logger.debug("User selected by userId=" + userId + ";");
        }
        return user;
    }

    /**
     * Implementation of {@link EntityDAO#getAll()}
     * @return list all users from database
     * @throws DAOException if some problems in database
     */
    @Override
    public List<User> getAll() throws DAOException{
        List<User> userList = null;
        userList = jdbcTemplate.query(SQL_SELECT_ALL_USERS, new UserMapper());
        logger.debug("All users selected");
        return userList;
    }

    /**
     * Implementation of {@link EntityDAO#countAll()}
     * @return the number users in database
     * @throws DAOException if some problems in database
     */
    @Override
    public Long countAll() throws DAOException{
        Long count = null;
        count  = jdbcTemplate.queryForObject(SQL_SELECT_COUNT_ALL_USERS, Long.class);
        logger.debug("All users counted;");
        return  count;
    }

    /**
     * Implementation {@link EntityDAO#update(Serializable)}
     * @param user is entity which need to update
     * @throws DAOException if some problems in database
     */
    @Override
    public void update(User user) throws DAOException{
       if(user != null){
    	   jdbcTemplate.update(SQL_UPDATE_USER_BY_USER_ID, 
    			   new Object[]{user.getUserName(), user.getLogin(), user.getPassword(), user.getIdUser()});
    	   logger.debug("User=" + user + " updated in table Customer;");
       }
    }

    /**
     * Implementation {@link EntityDAO#delete(Long)}
     * @param userId is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public void delete(Long userId) throws DAOException{
        if(userId != null){
        	jdbcTemplate.update(SQL_DELETE_USER_BY_USER_ID, new Object[]{userId});
        	logger.debug("User whith userId=" + userId + " deleted from table Customer;");
        }
    }

    /**
     * Implementation {@link UserDAO#checkUser(Long)}
     * @param userId is id checking user
     * @return yes or no it is depends from contains or didn't contains user in database
     * @throws DAOException if some problems in database
     */
    @Override
    public boolean checkUser(Long userId) throws DAOException{
        boolean containsUser = false;
        if(userId != null){
        	List<Long> checkListUsers = jdbcTemplate.query(SQL_CHECK_USER_BY_USER_ID, 
        			new Object[]{userId}, new LongMapper());
        	if(!checkListUsers.isEmpty()){
        		containsUser = true;
        	}
        	logger.debug("User by userId=" + userId + " checked;");
        }
        return containsUser;
    }

    /**
     * Implementation {@link UserDAO#getUser(String, String)}
     * @param login is user
     * @param password is user
     * @return user which login and password matches with login in password in database
     * @throws DAOException if some problems in database
     */
    @Override
    public User getUser(String login, String password) throws DAOException{
        User user = null;
        if(login != null && password != null){
        	List<User> listUsers = jdbcTemplate.query(SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD, 
        			new Object[]{login, password}, new UserMapper());
        	if(!listUsers.isEmpty()){
        		user = listUsers.get(0);
        	}
        	logger.debug("User by login=" + login + " and password=" + password + " selected;");
        }
       return user;
    }



}
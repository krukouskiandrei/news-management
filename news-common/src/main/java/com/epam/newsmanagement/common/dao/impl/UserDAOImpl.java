package com.epam.newsmanagement.common.dao.impl;

import com.epam.newsmanagement.common.dao.EntityDAO;
import com.epam.newsmanagement.common.dao.UserDAO;
import com.epam.newsmanagement.common.dao.utils.ConnectionCloser;
import com.epam.newsmanagement.common.entity.User;
import com.epam.newsmanagement.common.exception.dao.DAOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    @Autowired
    private DataSource dataSource;
    
    /**
     * Implementation of {@link EntityDAO#create(Serializable)}
     * @param user is parameter which need to insert in database
     * @return parameter user_id from database
     * @throws DAOException if some problems in database
     */
    @Override
    public Long create(User user) throws DAOException{
    	
    	if(user != null){
        	Connection connection = null;
        	PreparedStatement statement = null;
        	ResultSet resultSet = null;
        	try{
        		connection = DataSourceUtils.doGetConnection(dataSource);
        		statement = connection.prepareStatement(SQL_INSER_USER, new String[]{"user_id"});
        		statement.setString(1, user.getUserName());
				statement.setString(2, user.getLogin());
				statement.setString(3, user.getPassword());
        		statement.executeUpdate();
        	
        		resultSet = statement.getGeneratedKeys();
        		Long userId = null;
        		if(resultSet.next()){
        			userId = resultSet.getLong(1);
        		}
        		return userId;
        	}catch(SQLException e){
        		throw new DAOException(e);
        	}finally{
        		ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
        	}
        }
        throw new DAOException();
    	
    	
    }
    
    /**
     * Implementation of {@link EntityDAO#getById(Long)}
     * @param userId is the field on which the search
     * @return {@link User}
     * @throws DAOException if some problems in database
     */
    @Override
    public User getById(Long userId) throws DAOException{
        
    	if(userId != null && userId > 0){
    		Connection connection = null;
    		PreparedStatement statement = null;
    		ResultSet resultSet = null;
    		try{
    			connection = DataSourceUtils.doGetConnection(dataSource);
    			statement = connection.prepareStatement(SQL_SELECT_USER_BY_USER_ID);
    			statement.setLong(1, userId);
        	
    			resultSet = statement.executeQuery();
    			User user = new User();
    			if(resultSet.next()){
        		user = createUser(resultSet);
    			}
    			logger.debug("User selected by userId=" + userId);
    			return user;
    		}catch(SQLException e){
    			throw new DAOException(e);
    		}finally{
    			ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
    		}
        }
    	throw new DAOException();
    	    	
    }

    
    private User createUser(ResultSet resultSet) throws SQLException{
    	User user = new User();
		user.setIdUser(resultSet.getLong(1));
		user.setUserName(resultSet.getString(2));
		user.setLogin(resultSet.getString(3));
		user.setPassword(resultSet.getString(4));
		return user;	
    }
    
    /**
     * Implementation of {@link EntityDAO#getAll()}
     * @return list all users from database
     * @throws DAOException if some problems in database
     */
    @Override
    public List<User> getAll() throws DAOException{
        
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
        	connection = DataSourceUtils.doGetConnection(dataSource);
        	statement = connection.prepareStatement(SQL_SELECT_ALL_USERS);
        	resultSet = statement.executeQuery();
        	List<User> userList = new ArrayList<>();
        	while(resultSet.next()){
        		userList.add(createUser(resultSet));
        	}
        	logger.debug("All users selected");
            return userList;
        }catch(SQLException e){
        	throw new DAOException(e);
        }finally{
        	ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
        }
    	
    	
    }

    /**
     * Implementation of {@link EntityDAO#countAll()}
     * @return the number users in database
     * @throws DAOException if some problems in database
     */
    @Override
    public Long countAll() throws DAOException{
        
    	Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
        	connection = DataSourceUtils.doGetConnection(dataSource);
        	statement = connection.createStatement();
        	resultSet = statement.executeQuery(SQL_SELECT_COUNT_ALL_USERS);
        	Long count = 0L;
        	if(resultSet.next()){
        		count = resultSet.getLong(1);
        	}
        	logger.debug("All users counted");
        	return count;
        }catch(SQLException e){
        	throw new DAOException(e);
        }finally{
        	ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
        }
    	
    	
    }

    /**
     * Implementation {@link EntityDAO#update(Serializable)}
     * @param user is entity which need to update
     * @throws DAOException if some problems in database
     */
    @Override
    public void update(User user) throws DAOException{
       
    	if(user != null){
    		Connection connection = null;
            PreparedStatement statement = null;
            try{
            	connection = DataSourceUtils.doGetConnection(dataSource);
            	statement = connection.prepareStatement(SQL_UPDATE_USER_BY_USER_ID);
            	statement.setString(1, user.getUserName());
            	statement.setString(2, user.getLogin());
            	statement.setString(3, user.getPassword());
            	statement.setLong(4, user.getIdUser());
            	statement.executeUpdate();
            	logger.debug("User=" + user + " updated in table Customer;");
            }catch(SQLException e){
            	throw new DAOException(e);
            }finally{
            	ConnectionCloser.closeConnection(connection, statement, dataSource);
            }
    	}
    	
    	
    }

    /**
     * Implementation {@link EntityDAO#delete(Long)}
     * @param userId is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public void delete(Long userId) throws DAOException{
        
    	if(userId != null && userId > 0){
    		Connection connection = null;
            PreparedStatement statement = null;
            try{
            	connection = DataSourceUtils.doGetConnection(dataSource);
            	statement = connection.prepareStatement(SQL_DELETE_USER_BY_USER_ID);
            	statement.setLong(1, userId);
            	statement.executeUpdate();
            	logger.debug("User whith userId=" + userId + " deleted from table Customer");
            }catch(SQLException e){
            	throw new DAOException(e);
            }finally{
            	ConnectionCloser.closeConnection(connection, statement, dataSource);
            }
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
        
    	if(userId != null && userId > 0){
    		boolean containsUser = false;
    		Connection connection = null;
    		PreparedStatement statement = null;
    		ResultSet resultSet = null;
    		try{
    			connection = DataSourceUtils.doGetConnection(dataSource);
    			statement = connection.prepareStatement(SQL_CHECK_USER_BY_USER_ID);
    			statement.setLong(1, userId);
        	
    			resultSet = statement.executeQuery();
    			if(resultSet.next()){
    				containsUser = true;
    			}
    			logger.debug("User by userId=" + userId + " checked;");
    			
    		}catch(SQLException e){
    			throw new DAOException(e);
    		}finally{
    			ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
    		}
    		return containsUser;
    	}
    	throw new DAOException();
    	
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
        
    	if(login != null && password != null){
    		Connection connection = null;
    		PreparedStatement statement = null;
    		ResultSet resultSet = null;
    		try{
    			connection = DataSourceUtils.doGetConnection(dataSource);
    			statement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN_AND_PASSWORD);
    			statement.setString(1, login);
    			statement.setString(2, password);
        	
    			resultSet = statement.executeQuery();
    			User user = new User();
    			if(resultSet.next()){
        		user = createUser(resultSet);
    			}
    			logger.debug("User by login=" + login + " and password=" + password + " selected;");
    			return user;
    		}catch(SQLException e){
    			throw new DAOException(e);
    		}finally{
    			ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
    		}
    	}
    	throw new DAOException();
    	
    }



}
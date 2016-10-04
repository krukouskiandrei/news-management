package com.epam.newsmanagement.common.dao.impl;

import com.epam.newsmanagement.common.dao.EntityDAO;
import com.epam.newsmanagement.common.dao.RoleDAO;
import com.epam.newsmanagement.common.dao.utils.ConnectionCloser;
import com.epam.newsmanagement.common.entity.Role;
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
    @Autowired
    private DataSource dataSource;
    
    
    /**
     * Implementation of {@link EntityDAO#create(Serializable)}
     * @param role is parameter which need to insert in database
     * @return parameter user_id from database
     * @throws DAOException if some problems in database
     */
   @Override
    public Long create(Role role) throws DAOException{
	   if(role != null){
       	Connection connection = null;
       	PreparedStatement statement = null;
       	ResultSet resultSet = null;
       	try{
       		connection = DataSourceUtils.doGetConnection(dataSource);
       		statement = connection.prepareStatement(SQL_INSERT_ROLE, new String[]{"USER_ID"});
       		statement.setLong(1, role.getUserId());
       		statement.setString(2, role.getRoleName());
       		statement.executeUpdate();
       	
       		resultSet = statement.getGeneratedKeys();
       		Long userId = null;
       		if(resultSet.next()){
       			userId = resultSet.getLong(1);
       		}
       		logger.debug("Role=" + role + " inserted in table Role;");
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
     * @return {@link Role}
     * @throws DAOException if some problems in database
     */
    @Override
    public Role getById(Long userId) throws DAOException{
    	if(userId != null && userId > 0){
    		Connection connection = null;
    		PreparedStatement statement = null;
    		ResultSet resultSet = null;
    		try{
    			connection = DataSourceUtils.doGetConnection(dataSource);
    			statement = connection.prepareStatement(SQL_SELECT_ROLE_BY_USER_ID);
    			statement.setLong(1, userId);
        	
    			resultSet = statement.executeQuery();
    			Role role = new Role();
    			if(resultSet.next()){
        		role = createRole(resultSet);
    			}
    			logger.debug("Role selected by userId=" + userId + ";");
    			return role;
    		}catch(SQLException e){
    			throw new DAOException(e);
    		}finally{
    			ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
    		}
        }
    	throw new DAOException();
    	
    }
    
    private Role createRole(ResultSet resultSet) throws SQLException{
    	Role role = new Role();
    	role.setUserId(resultSet.getLong(1));
		role.setRoleName(resultSet.getString(2));
    	return role;    	
    }
    /**
     * Implementation of {@link EntityDAO#getAll()}
     * @return list all roles from database
     * @throws DAOException if some problems in database
     */
    @Override
    public List<Role> getAll() throws DAOException{
    	

    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
        	connection = DataSourceUtils.doGetConnection(dataSource);
        	statement = connection.prepareStatement(SQL_SELECT_ALL_ROLES);
        	resultSet = statement.executeQuery();
        	List<Role> roleList = new ArrayList<>();
        	while(resultSet.next()){
        		roleList.add(createRole(resultSet));
        	}
        	logger.debug("All roles selected;");
            return roleList;
        }catch(SQLException e){
        	throw new DAOException(e);
        }finally{
        	ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
        }
    	
    }

    /**
     * Implementation of {@link EntityDAO#countAll()}
     * @return the number roles in database
     * @throws DAOException if some problems in database
     */
    public Long countAll() throws DAOException{
        
    	Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
        	connection = DataSourceUtils.doGetConnection(dataSource);
        	statement = connection.createStatement();
        	resultSet = statement.executeQuery(SQL_SELECT_COUNT_ALL_ROLES);
        	Long count = 0L;
        	if(resultSet.next()){
        		count = resultSet.getLong(1);
        	}
        	logger.debug("All roles counted");
        	return count;
        }catch(SQLException e){
        	throw new DAOException(e);
        }finally{
        	ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
        }
    	
    	
    }

    /**
     * Implementation {@link EntityDAO#update(Serializable)}
     * @param role is entity which need to update
     * @throws DAOException if some problems in database
     */
    @Override
    public void update(Role role) throws DAOException{
        
    	if(role != null){
    		Connection connection = null;
            PreparedStatement statement = null;
            try{
            	connection = DataSourceUtils.doGetConnection(dataSource);
            	statement = connection.prepareStatement(SQL_UPDATE_ROLE_BY_USER_ID);
            	statement.setString(1, role.getRoleName());
            	statement.setLong(2, role.getUserId());
            	statement.executeUpdate();
            	logger.debug("Role=" + role + " updated in table Role;");
            }catch(SQLException e){
            	throw new DAOException(e);
            }finally{
            	ConnectionCloser.closeConnection(connection, statement, dataSource);
            }
    	}
    	
    	
    }

    /**
     * Implementation {@link EntityDAO#delete(Long)}
     * @param userID is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public void delete(Long userID) throws DAOException{
        
    	if(userID != null && userID > 0){
    		Connection connection = null;
            PreparedStatement statement = null;
            try{
            	connection = DataSourceUtils.doGetConnection(dataSource);
            	statement = connection.prepareStatement(SQL_DELETE_ROLE_BY_USER_ID);
            	statement.setLong(1, userID);
            	statement.executeUpdate();
            	logger.debug("Role for userId=" + userID + " deleted;");
            }catch(SQLException e){
            	throw new DAOException(e);
            }finally{
            	ConnectionCloser.closeConnection(connection, statement, dataSource);
            }
    	}
    	
    	
    }
}
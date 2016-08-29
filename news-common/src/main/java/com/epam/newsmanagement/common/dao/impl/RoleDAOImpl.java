package com.epam.newsmanagement.common.dao.impl;

import com.epam.newsmanagement.common.dao.EntityDAO;
import com.epam.newsmanagement.common.dao.RoleDAO;
import com.epam.newsmanagement.common.dao.util.ConnectionCloser;
import com.epam.newsmanagement.common.entity.Role;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementing of {@link RoleDAO}
 */
@Repository
public class RoleDAOImpl implements RoleDAO{

    private final static String INSERT_ROLE_SQL = "INSERT INTO ROLES (USER_ID, ROLE_NAME) VALUES(?, ?)";
    private final static String SELECT_ROLE_BY_USER_ID_SQL = "SELECT USER_ID, ROLE_NAME FROM ROLES WHERE USER_ID = ?";
    private final static String SELECT_ALL_ROLES_SQL = "SELECT USER_ID, ROLE_NAME FROM ROLES";
    private final static String USER_ID = "USER_ID";
    private final static String SELECT_COUNT_ALL_ROLES_SQL = "SELECT COUNT(USER_ID) FROM ROLES";
    private final static String UPDATE_ROLE_BY_USER_ID_SQL = "UPDATE ROLES SET ROLE_NAME = ? WHERE USER_ID = ?";
    private final static String DELETE_ROLE_BY_USER_ID_SQL = "DELETE FROM ROLES WHERE USER_ID = ?";
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
        Long userId = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_ROLE_SQL, new String[]{USER_ID});

            preparedStatement.setLong(1, role.getUserId());
            preparedStatement.setString(2, role.getRoleName());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                userId = resultSet.getLong(1);
                role.setUserId(userId);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement, resultSet);
        }
       return userId;
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ROLE_BY_USER_ID_SQL);

            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                role = new Role();
                role.setUserId(resultSet.getLong(1));
                role.setRoleName(resultSet.getString(2));
            }
            }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement, resultSet);
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
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_ROLES_SQL);

            roleList = new ArrayList<Role>();
            while (resultSet.next()){
                Role role = createRole(resultSet);
                roleList.add(role);
            }
            }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, statement, resultSet);
        }
        return roleList;
    }

    /**
     * create {@link Role} which get from database
     * @param resultSet contains {@link Role}
     * @return created role
     * @throws DAOException if some problems in database
     */
    private Role createRole(ResultSet resultSet) throws DAOException{
        Role role = null;
        try {
            role = new Role();
            role.setUserId(resultSet.getLong(1));
            role.setRoleName(resultSet.getString(2));
        }catch (SQLException e){
            throw new DAOException(e);
        }
        return role;
    }

    /**
     * Implementation of {@link EntityDAO#countAll()}
     * @return the number roles in database
     * @throws DAOException if some problems in database
     */
    public Long countAll() throws DAOException{
        Long count = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_COUNT_ALL_ROLES_SQL);
            count = 0L;
            if (resultSet.next()){
                count = resultSet.getLong(1);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, statement, resultSet);
        }
        return count;
    }

    /**
     * Implementation {@link EntityDAO#update(Serializable)}
     * @param role is entity which need to update
     * @throws DAOException if some problems in database
     */
    @Override
    public void update(Role role) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_ROLE_BY_USER_ID_SQL);
            preparedStatement.setString(1, role.getRoleName());
            preparedStatement.setLong(2, role.getUserId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement);
        }
    }

    /**
     * Implementation {@link EntityDAO#delete(Long)}
     * @param userID is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public void delete(Long userID) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_ROLE_BY_USER_ID_SQL);
            preparedStatement.setLong(1, userID);
            preparedStatement.executeUpdate();
            }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement);
        }
    }
}
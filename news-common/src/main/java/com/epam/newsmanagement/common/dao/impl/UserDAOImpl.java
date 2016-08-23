package com.epam.newsmanagement.common.dao.impl;

import com.epam.newsmanagement.common.dao.EntityDAO;
import com.epam.newsmanagement.common.dao.UserDAO;
import com.epam.newsmanagement.common.dao.util.ConnectionCloser;
import com.epam.newsmanagement.common.entity.User;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Implementing of {@link UserDAO}
 */
@Repository
public class UserDAOImpl implements UserDAO {

    private final static String INSER_USER_SQL = "INSERT INTO CUSTOMER (USER_ID, USER_NAME, LOGIN, PASSWORD)" +
            " VALUES(CUSTOMER_SEQUENCE.NEXTVAL, ?, ?, ?);";
    private final static String USER_ID = "USER_ID";
    private final static String SELECT_USER_BY_USER_ID_SQL = "SELECT USER_ID, USER_NAME, " +
            "LOGIN, PASSWORD FROM CUSTOMER WHERE USER_ID = ?";
    private final static String SELECT_ALL_USERS_SQL = "SELECT USER_ID, USER_NAME, LOGIN, PASSWORD FROM CUSTOMER";
    private final static String SELECT_COUNT_ALL_USERS_SQL = "SELECT COUNT(USER_ID) FROM CUSTOMER";
    private final static String UPDATE_USER_BY_USER_ID_SQL = "UPDATE CUSTOMER SET USER_NAME = ?, " +
            "LOGIN = ?, PASSWORD = ? WHERE USER_ID = ?";
    private final static String DELETE_USER_BY_USER_ID_SQL = "DELETE FROM CUSTOMER WHERE USER_ID = ?";
    private final static String CHECK_USER_BY_USER_ID_SQL = "SELECT USER_ID FROM CUSTOMER WHERE USER_ID = ?";
    private final static String SELECT_USER_BY_LOGIN_AND_PASSWORD_SQL = "SELECT USER_ID, USER_NAME, " +
            "LOGIN, PASSWORD FROM CUSTOMER WHERE LOGIN = ? AND PASSWORD = ?";
    @Autowired
    DataSource dataSource;

    /**
     * Implementation of {@link EntityDAO#create(Serializable)}
     * @param user is parameter which need to insert in database
     * @return parameter user_id from database
     * @throws DAOException if some problems in database
     */
    @Override
    public Long create(User user) throws DAOException{
        Long userId = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(INSER_USER_SQL, new String[]{USER_ID});

            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                userId = resultSet.getLong(1);
                user.setIdUser(userId);
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
     * @return {@link User}
     * @throws DAOException if some problems in database
     */
    @Override
    public User getById(Long userId) throws DAOException{
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_USER_ID_SQL);

            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                user = new User();
                user.setIdUser(resultSet.getLong(1));
                user.setUserName(resultSet.getString(2));
                user.setLogin(resultSet.getString(3));
                user.setPassword(resultSet.getString(4));
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement, resultSet);
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
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_USERS_SQL);

            userList = new ArrayList<User>();
            while (resultSet.next()){
                User user = createUser(resultSet);
                userList.add(user);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, statement, resultSet);
        }
        return userList;
    }

    /**
     * create {@link User} which get from database
     * @param resultSet contains {@link User}
     * @return created user
     * @throws DAOException if some problems in database
     */
    private User createUser(ResultSet resultSet) throws DAOException{
        User user = null;
        try{
            user = new User();
            user.setIdUser(resultSet.getLong(1));
            user.setUserName(resultSet.getString(2));
            user.setLogin(resultSet.getString(3));
            user.setPassword(resultSet.getString(4));
        }catch (SQLException e){
            throw new DAOException(e);
        }
        return user;
    }

    /**
     * Implementation of {@link EntityDAO#countAll()}
     * @return the number users in database
     * @throws DAOException if some problems in database
     */
    @Override
    public Long countAll() throws DAOException{
        Long count = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_COUNT_ALL_USERS_SQL);
            count = 0L;
            if (resultSet.next()){
                count = resultSet.getLong(1);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, statement, resultSet);
        }
        return  count;
    }

    /**
     * Implementation {@link EntityDAO#update(Serializable)}
     * @param user is entity which need to update
     * @throws DAOException if some problems in database
     */
    @Override
    public void update(User user) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER_BY_USER_ID_SQL);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement);
        }
    }

    /**
     * Implementation {@link EntityDAO#delete(Long)}
     * @param userId is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public void delete(Long userId) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement  = connection.prepareStatement(DELETE_USER_BY_USER_ID_SQL);
            preparedStatement.setLong(1, userId);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement);
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(CHECK_USER_BY_USER_ID_SQL);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                containsUser = true;
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement, resultSet);
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN_AND_PASSWORD_SQL);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                user = new User();
                user.setIdUser(resultSet.getLong(1));
                user.setUserName(resultSet.getString(2));
                user.setLogin(resultSet.getString(3));
                user.setPassword(resultSet.getString(4));
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement, resultSet);
        }
        return user;
    }



}

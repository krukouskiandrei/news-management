package com.epam.newsmanagement.common.dao.impl;

import com.epam.newsmanagement.common.dao.AuthorDAO;
import com.epam.newsmanagement.common.dao.EntityDAO;
import com.epam.newsmanagement.common.dao.util.ConnectionCloser;
import com.epam.newsmanagement.common.entity.Author;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  Implementing of {@link AuthorDAO}
 */
@Repository
public class AuthorDAOImpl implements AuthorDAO {

    private final static String INSER_AUTHOR_SQL = "INSERT INTO AUTHOR(AUTHOR_ID, AUTHOR_NAME, EXPIRED) " +
            "VALUES(AUTHOR_SEQUENCE.NEXTVAL, ?, ?);";
    private final static String SELECT_AUTHOR_BY_AUTHOR_ID_SQL = "SELECT AUTHOR_ID, AUTHOR_NAME, EXPIRED " +
            "FROM AUTHOR WHERE AUTHOR_ID = ?";
    private final static String SELECT_ALL_AUTHORES_SQL = "SELECT AUTHOR_ID, AUTHOR_NAME, EXPIRED " +
            "FROM AUTHOR";
    private final static String AUTHOR_ID = "AUTHOR_ID";
    private final static String SELECT_COUNT_ALL_AUTHOR_SQL = "SELECT COUNT(AUTHOR_ID) FROM AUTHOR";
    private final static String UPDATE_AUTHOR_BY_AUTHOR_ID_SQL = "UPDATE AUTHOR SET AUTHOR_NAME = ?, " +
            "EXPIRED = ? WHERE AUTHOR_ID = ?";
    private final static String DELETE_AUTHOR_BY_AUTHOR_ID_SQL = "DELETE FROM AUTHOR WHERE AUTHOR_ID = ?";
    private final static String SELECT_AUTHOR_BY_NEWS_ID_SQL = "SELECT AUTHOR.AUTHOR_ID, "
    		+ "AUTHOR.AUTHOR_NAME, AUTHOR.EXPIRED FROM AUTHOR, NEWS_AUTHOR"
    		+ " WHERE NEWS_AUTHOR.NEWS_ID = ? AND NEWS_AUTHOR.AUTHOR_ID = AUTHOR.AUTHOR_ID";    
    
    @Autowired
    private DataSource dataSource;

    /**
     * Implementation of {@link EntityDAO#create(Serializable)}
     * @param author is parameter which need to insert in database
     * @return parameter author_id from database
     * @throws DAOException if some problems in database
     */
    @Override
    public Long create(Author author) throws DAOException{
        Long authorId = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(INSER_AUTHOR_SQL, new String[]{AUTHOR_ID});

            preparedStatement.setString(1, author.getAuthorName());
            preparedStatement.setTimestamp(2, author.getExpiredDate());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                authorId = resultSet.getLong(1);
                author.setIdAuthor(authorId);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement, resultSet);
        }
        return authorId;
    }

    /**
     * Implementation of {@link EntityDAO#getById(Long)}
     * @param authorId is the field on which the search
     * @return {@link Author}
     * @throws DAOException if some problems in database
     */
    @Override
    public Author getById(Long authorId) throws DAOException{
        Author author = null;
        Connection connection =null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_AUTHOR_ID_SQL);

            preparedStatement.setLong(1, authorId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                author = createAuthor(resultSet);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement, resultSet);
        }
        return author;
    }

    /**
     * create {@link Author} which get from database
     * @param resultSet contains {@link Author}
     * @return created role
     * @throws DAOException if some problems in database
     */
    private Author createAuthor(ResultSet resultSet) throws DAOException{
        Author author = null;
        try {
            author = new Author();
            author.setIdAuthor(resultSet.getLong(1));
            author.setAuthorName(resultSet.getString(2));
            author.setExpiredDate(resultSet.getTimestamp(3));
        }catch (SQLException e){
            throw new DAOException(e);
        }
        return author;
    }

    /**
     * Implementation of {@link EntityDAO#getAll()}
     * @return list all authors from database
     * @throws DAOException if some problems in database
     */
    @Override
    public List<Author> getAll() throws DAOException{
        List<Author> authorList = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_AUTHORES_SQL);

            authorList = new ArrayList<Author>();
            while (resultSet.next()){
                Author author = createAuthor(resultSet);
                authorList.add(author);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, statement, resultSet);
        }
        return authorList;
    }

    /**
     * Implementation of {@link EntityDAO#countAll()}
     * @return the number authors in database
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
            resultSet = statement.executeQuery(SELECT_COUNT_ALL_AUTHOR_SQL);
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
     * @param author is entity which need to update
     * @throws DAOException if some problems in database
     */
    @Override
    public void update(Author author) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_AUTHOR_BY_AUTHOR_ID_SQL);
            preparedStatement.setString(1, author.getAuthorName());
            preparedStatement.setTimestamp(2, author.getExpiredDate());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement);
        }
    }

    /**
     * Implementation {@link EntityDAO#delete(Long)}
     * @param authorId is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public void delete(Long authorId) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_AUTHOR_BY_AUTHOR_ID_SQL);
            preparedStatement.setLong(1, authorId);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement);
        }
    }
    
    /**
     * Implementation {@link AuthorDAO#getAuthorForNews(Long)}
     * @param newsId is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public Author getAuthorForNews(Long newsId) throws DAOException{
    	Author author = null;
    	Connection connection = null;
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;
    	try{
    		connection = dataSource.getConnection();
    		preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_NEWS_ID_SQL);
    		preparedStatement.setLong(1, newsId);
    		resultSet = preparedStatement.executeQuery();
    		if(resultSet.next()){
    			author = createAuthor(resultSet);
    		}
    	}catch(SQLException e){
    		throw new DAOException(e);
    	}finally {
			ConnectionCloser.closeConnection(connection, dataSource, preparedStatement, resultSet);
		}
    	return author;
    }
}
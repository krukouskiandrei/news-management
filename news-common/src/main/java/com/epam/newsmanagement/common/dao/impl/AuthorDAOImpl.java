package com.epam.newsmanagement.common.dao.impl;

import com.epam.newsmanagement.common.dao.AuthorDAO;
import com.epam.newsmanagement.common.dao.EntityDAO;
import com.epam.newsmanagement.common.dao.utils.ConnectionCloser;
import com.epam.newsmanagement.common.entity.Author;
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
 *  Implementing of {@link AuthorDAO}
 */
@Repository
public class AuthorDAOImpl implements AuthorDAO {

    private final static String SQL_INSER_AUTHOR = "INSERT INTO AUTHOR(AUTHOR_ID, AUTHOR_NAME, EXPIRED) "
    												+ "VALUES(AUTHOR_SEQUENCE.NEXTVAL, ?, ?)";
    private final static String SQL_SELECT_AUTHOR_BY_AUTHOR_ID = "SELECT AUTHOR_ID, AUTHOR_NAME, EXPIRED " +
            														"FROM AUTHOR "
            														+ "WHERE AUTHOR_ID = ?";
    private final static String SQL_SELECT_ALL_AUTHORES = "SELECT AUTHOR_ID, AUTHOR_NAME, EXPIRED " +
            												"FROM AUTHOR";
    private final static String SQL_SELECT_COUNT_ALL_AUTHOR = "SELECT COUNT(1) "
    														+ "FROM AUTHOR";
    private final static String SQL_UPDATE_AUTHOR_BY_AUTHOR_ID = "UPDATE AUTHOR "
    															+ "SET AUTHOR_NAME = ?, EXPIRED = ? "
    															+ "WHERE AUTHOR_ID = ?";
    private final static String SQL_DELETE_AUTHOR_BY_AUTHOR_ID = "DELETE FROM AUTHOR "
    															+ "WHERE AUTHOR_ID = ?";
    private final static String SQL_SELECT_AUTHOR_BY_NEWS_ID = "SELECT AUTHOR.AUTHOR_ID, AUTHOR.AUTHOR_NAME, AUTHOR.EXPIRED "
    															+ "FROM AUTHOR, NEWS_AUTHOR "
    															+ "WHERE NEWS_AUTHOR.NEWS_ID = ? AND NEWS_AUTHOR.AUTHOR_ID = AUTHOR.AUTHOR_ID";    
    
    private final static Logger logger = LogManager.getLogger(AuthorDAOImpl.class);
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
        if(author != null){
        	Connection connection = null;
        	PreparedStatement statement = null;
        	ResultSet resultSet = null;
        	try{
        		connection = DataSourceUtils.doGetConnection(dataSource);
        		statement = connection.prepareStatement(SQL_INSER_AUTHOR, new String[]{"author_id"});
        		statement.setString(1, author.getAuthorName());
        		statement.setTimestamp(2, author.getExpiredDate());
        		statement.executeUpdate();
        	
        		resultSet = statement.getGeneratedKeys();
        		Long authorId = null;
        		if(resultSet.next()){
        			authorId = resultSet.getLong(1);
        		}
        		return authorId;
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
     * @param authorId is the field on which the search
     * @return {@link Author}
     * @throws DAOException if some problems in database
     */
    @Override
    public Author getById(Long authorId) throws DAOException{
    	if(authorId != null && authorId > 0){
    		Connection connection = null;
    		PreparedStatement statement = null;
    		ResultSet resultSet = null;
    		try{
    			connection = DataSourceUtils.doGetConnection(dataSource);
    			statement = connection.prepareStatement(SQL_SELECT_AUTHOR_BY_AUTHOR_ID);
    			statement.setLong(1, authorId);
        	
    			resultSet = statement.executeQuery();
    			Author author = new Author();
    			if(resultSet.next()){
        		author = createAuthor(resultSet);
    			}
    			logger.debug("Author selected by authorId=" + authorId + ";");
    			return author;
    		}catch(SQLException e){
    			throw new DAOException(e);
    		}finally{
    			ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
    		}
        }
    	throw new DAOException();
    }
    
    private Author createAuthor(ResultSet resultSet) throws SQLException{
    	Author author = new Author();
    	author.setIdAuthor(resultSet.getLong(1));
    	author.setAuthorName(resultSet.getString(2));
    	author.setExpiredDate(resultSet.getTimestamp(3));
    	return author;    	
    }
    /**
     * Implementation of {@link EntityDAO#getAll()}
     * @return list all authors from database
     * @throws DAOException if some problems in database
     */
    @Override
    public List<Author> getAll() throws DAOException{
    	
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
        	connection = DataSourceUtils.doGetConnection(dataSource);
        	statement = connection.prepareStatement(SQL_SELECT_ALL_AUTHORES);
        	resultSet = statement.executeQuery();
        	List<Author> authorList = new ArrayList<>();
        	while(resultSet.next()){
        		authorList.add(createAuthor(resultSet));
        	}
        	logger.debug("Selected all authors");
            return authorList;
        }catch(SQLException e){
        	throw new DAOException(e);
        }finally{
        	ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
        }
    	
    }

    /**
     * Implementation of {@link EntityDAO#countAll()}
     * @return the number authors in database
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
        	resultSet = statement.executeQuery(SQL_SELECT_COUNT_ALL_AUTHOR);
        	Long count = 0L;
        	if(resultSet.next()){
        		count = resultSet.getLong(1);
        	}
        	logger.debug("All authors counted");
        	return count;
        }catch(SQLException e){
        	throw new DAOException(e);
        }finally{
        	ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
        }
    }

    /**
     * Implementation {@link EntityDAO#update(Serializable)}
     * @param author is entity which need to update
     * @throws DAOException if some problems in database
     */
    @Override
    public void update(Author author) throws DAOException{
    	if(author != null){
    		Connection connection = null;
            PreparedStatement statement = null;
            try{
            	connection = DataSourceUtils.doGetConnection(dataSource);
            	statement = connection.prepareStatement(SQL_UPDATE_AUTHOR_BY_AUTHOR_ID);
            	statement.setString(1, author.getAuthorName());
            	statement.setTimestamp(2, author.getExpiredDate());
            	statement.setLong(3, author.getIdAuthor());
            	statement.executeUpdate();
            	logger.debug("Author=" + author + " updated in table Author;");
            }catch(SQLException e){
            	throw new DAOException(e);
            }finally{
            	ConnectionCloser.closeConnection(connection, statement, dataSource);
            }
    	}
    }

    /**
     * Implementation {@link EntityDAO#delete(Long)}
     * @param authorId is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public void delete(Long authorId) throws DAOException{
    	if(authorId != null && authorId > 0){
    		Connection connection = null;
            PreparedStatement statement = null;
            try{
            	connection = DataSourceUtils.doGetConnection(dataSource);
            	statement = connection.prepareStatement(SQL_DELETE_AUTHOR_BY_AUTHOR_ID);
            	statement.setLong(1, authorId);
            	statement.executeUpdate();
            	logger.debug("Author with authorId=" + authorId + " deleted from table Author;");
            }catch(SQLException e){
            	throw new DAOException(e);
            }finally{
            	ConnectionCloser.closeConnection(connection, statement, dataSource);
            }
    	}
    }
    
    /**
     * Implementation {@link AuthorDAO#getAuthorForNews(Long)}
     * @param newsId is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public Author getAuthorForNews(Long newsId) throws DAOException{
    	if(newsId != null && newsId > 0){
    		Connection connection = null;
    		PreparedStatement statement = null;
    		ResultSet resultSet = null;
    		try{
    			connection = DataSourceUtils.doGetConnection(dataSource);
    			statement = connection.prepareStatement(SQL_SELECT_AUTHOR_BY_NEWS_ID);
    			statement.setLong(1, newsId);
        	
    			resultSet = statement.executeQuery();
    			Author author = new Author();
    			if(resultSet.next()){
        		author = createAuthor(resultSet);
    			}
    			logger.debug("Author selected by authorId=" + newsId + ";");
    			return author;
    		}catch(SQLException e){
    			throw new DAOException(e);
    		}finally{
    			ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
    		}
        }
    	throw new DAOException();
    	
    }
}
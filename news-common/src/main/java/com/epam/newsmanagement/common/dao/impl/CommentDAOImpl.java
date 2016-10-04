package com.epam.newsmanagement.common.dao.impl;

import com.epam.newsmanagement.common.dao.CommentDAO;
import com.epam.newsmanagement.common.dao.EntityDAO;
import com.epam.newsmanagement.common.dao.utils.ConnectionCloser;
import com.epam.newsmanagement.common.entity.Comment;
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
 *Implementing of {@link CommentDAOImpl}
 */
@Repository
public class CommentDAOImpl implements CommentDAO{

    private final static String SQL_INSERT_COMMENT = "INSERT INTO COMMENTS(COMMENT_ID, NEWS_ID, COMMENT_TEXT, CREATION_DATE) "
    												+ "VALUES(COMMENT_SEQUENCE.NEXTVAL, ?, ?, ?)";
    private final static String SQL_SELECT_COMMENT_BY_COMMENT_ID = "SELECT COMMENT_ID, NEWS_ID, COMMENT_TEXT, CREATION_DATE "
    																+ "FROM COMMENTS "
    																+ "WHERE COMMENT_ID = ?";
    private final static String SQL_SELECT_ALL_COMMENTS = "SELECT COMMENT_ID, NEWS_ID, COMMENT_TEXT, CREATION_DATE "
    													+ "FROM COMMENTS";
    private final static String SQL_SELECT_COUNT_ALL_COMMENTS = "SELECT COUNT(1) "
    															+ "FROM COMMENTS";
    private final static String SQL_UPDATE_COMMENT_BY_COMMENT_ID = "UPDATE COMMENTS "
    																+ "SET NEWS_ID = ?, COMMENT_TEXT = ?, CREATION_DATE = ? "
    																+ "WHERE COMMENT_ID = ?";
    private final static String SQL_DELETE_COMMENT_BY_COMMENT_ID = "DELETE FROM COMMENTS "
    																+ "WHERE COMMENT_ID = ?";
    private final static String SQL_SELECT_COMMENT_BY_NEWS_ID = "SELECT COMMENT_ID, NEWS_ID, COMMENT_TEXT, CREATION_DATE "
    															+ "FROM COMMENTS "
    															+ "WHERE NEWS_ID = ?";
    private final static String SQL_DELETE_COMMENT_BY_NEWS_ID = "DELETE FROM COMMENTS "
    															+ "WHERE NEWS_ID = ?";

    private final static Logger logger = LogManager.getLogger(CommentDAOImpl.class);
    @Autowired
    private DataSource dataSource;
    
    /**
     * Implementation of {@link EntityDAO#create(Serializable)}
     * @param comment is parameter which need to insert in database
     * @return parameter comment_id from database
     * @throws DAOException if some problems in database
     */
    @Override
    public Long create(Comment comment) throws DAOException{
    	if(comment != null){
        	Connection connection = null;
        	PreparedStatement statement = null;
        	ResultSet resultSet = null;
        	try{
        		connection = DataSourceUtils.doGetConnection(dataSource);
        		statement = connection.prepareStatement(SQL_INSERT_COMMENT, new String[]{"comment_id"});
        		statement.setLong(1, comment.getIdNews());
        		statement.setString(2, comment.getCommentText());
        		statement.setTimestamp(3, comment.getCreationDate());
        		statement.executeUpdate();
        	
        		resultSet = statement.getGeneratedKeys();
        		Long commentId = null;
        		if(resultSet.next()){
        			commentId = resultSet.getLong(1);
        		}
        		return commentId;
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
     * @param commentId is the field on which the search
     * @return {@link Comment}
     * @throws DAOException if some problems in database
     */
    @Override
    public Comment getById(Long commentId) throws DAOException{
    	if(commentId != null && commentId > 0){
    		Connection connection = null;
    		PreparedStatement statement = null;
    		ResultSet resultSet = null;
    		try{
    			connection = DataSourceUtils.doGetConnection(dataSource);
    			statement = connection.prepareStatement(SQL_SELECT_COMMENT_BY_COMMENT_ID);
    			statement.setLong(1, commentId);
        	
    			resultSet = statement.executeQuery();
    			Comment comment = new Comment();
    			if(resultSet.next()){
        		comment = createComment(resultSet);
    			}
    			logger.debug("Selected comment by commentId=" + commentId + ";");
    			return comment;
    		}catch(SQLException e){
    			throw new DAOException(e);
    		}finally{
    			ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
    		}
        }
    	throw new DAOException();
    	
    }

    private Comment createComment(ResultSet resultSet) throws SQLException{
    	Comment comment = new Comment();
    	comment.setIdComment(resultSet.getLong(1));
		comment.setIdNews(resultSet.getLong(2));
		comment.setCommentText(resultSet.getString(3));
		comment.setCreationDate(resultSet.getTimestamp(4));
    	return comment;    	
    }
    
    /**
     * Implementation of {@link EntityDAO#getAll()}
     * @return list all comments from database
     * @throws DAOException if some problems in database
     */
    @Override
    public List<Comment> getAll() throws DAOException {
        
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
        	connection = DataSourceUtils.doGetConnection(dataSource);
        	statement = connection.prepareStatement(SQL_SELECT_ALL_COMMENTS);
        	resultSet = statement.executeQuery();
        	List<Comment> commentList = new ArrayList<>();
        	while(resultSet.next()){
        		commentList.add(createComment(resultSet));
        	}
        	logger.debug("All comments selected;");
        	return commentList;
        }catch(SQLException e){
        	throw new DAOException(e);
        }finally{
        	ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
        }
    	    	
    }

    /**
     * Implementation of {@link EntityDAO#countAll()}
     * @return the number commentes in database
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
        	resultSet = statement.executeQuery(SQL_SELECT_COUNT_ALL_COMMENTS);
        	Long count = 0L;
        	if(resultSet.next()){
        		count = resultSet.getLong(1);
        	}
        	logger.debug("All comments counted");
        	return count;
        }catch(SQLException e){
        	throw new DAOException(e);
        }finally{
        	ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
        }
    	
    }

    /**
     * Implementation {@link EntityDAO#update(Serializable)}
     * @param comment is entity which need to update
     * @throws DAOException if some problems in database
     */
    @Override
    public void update(Comment comment) throws DAOException{
        
    	if(comment != null){
    		Connection connection = null;
            PreparedStatement statement = null;
            try{
            	connection = DataSourceUtils.doGetConnection(dataSource);
            	statement = connection.prepareStatement(SQL_UPDATE_COMMENT_BY_COMMENT_ID);
            	statement.setLong(1, comment.getIdNews());
            	statement.setString(2, comment.getCommentText());
            	statement.setTimestamp(3, comment.getCreationDate());
            	statement.setLong(4, comment.getIdComment());
            	statement.executeUpdate();
            	logger.debug("Comment=" + comment + " updated in table Comments;");
            }catch(SQLException e){
            	throw new DAOException(e);
            }finally{
            	ConnectionCloser.closeConnection(connection, statement, dataSource);
            }
    	}
    	
    }

    /**
     * Implementation {@link EntityDAO#delete(Long)}
     * @param commentId is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public void delete(Long commentId) throws DAOException{
    	if(commentId != null && commentId > 0){
    		Connection connection = null;
            PreparedStatement statement = null;
            try{
            	connection = DataSourceUtils.doGetConnection(dataSource);
            	statement = connection.prepareStatement(SQL_DELETE_COMMENT_BY_COMMENT_ID);
            	statement.setLong(1, commentId);
            	statement.executeUpdate();
            	logger.debug("Comment with commentId=" + commentId + " deleted from table Comment;");
            }catch(SQLException e){
            	throw new DAOException(e);
            }finally{
            	ConnectionCloser.closeConnection(connection, statement, dataSource);
            }
    	}
    	
    }

    /**
     * Implementation of {@link EntityDAO#getAll()}
     * @param newsId is the field on which the search
     * @return list comments from database for news
     * @throws DAOException if some problems in database
     */
    @Override
    public List<Comment> getCommentList(Long newsId) throws DAOException{
        
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
        	connection = DataSourceUtils.doGetConnection(dataSource);
        	statement = connection.prepareStatement(SQL_SELECT_COMMENT_BY_NEWS_ID);
        	statement.setLong(1, newsId);
        	resultSet = statement.executeQuery();
        	List<Comment> commentList = new ArrayList<>();
        	while(resultSet.next()){
        		commentList.add(createComment(resultSet));
        	}
        	logger.debug("Selected comments by newsId=" + newsId + ";");
        	return commentList;
        }catch(SQLException e){
        	throw new DAOException(e);
        }finally{
        	ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
        }
    	
    }

    /**
     * Implementation {@link EntityDAO#delete(Long)}
     * @param newsId is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public void deleteCommentByNews(Long newsId) throws DAOException{
    	if(newsId != null && newsId > 0){
    		Connection connection = null;
            PreparedStatement statement = null;
            try{
            	connection = DataSourceUtils.doGetConnection(dataSource);
            	statement = connection.prepareStatement(SQL_DELETE_COMMENT_BY_NEWS_ID);
            	statement.setLong(1, newsId);
            	statement.executeUpdate();
            	logger.debug("All comments for news where newsId=" + newsId + " deleted");
            }catch(SQLException e){
            	throw new DAOException(e);
            }finally{
            	ConnectionCloser.closeConnection(connection, statement, dataSource);
            }
    	}
    	
    }

}
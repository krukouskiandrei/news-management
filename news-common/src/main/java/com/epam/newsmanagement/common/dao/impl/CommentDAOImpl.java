package com.epam.newsmanagement.common.dao.impl;

import com.epam.newsmanagement.common.dao.CommentDAO;
import com.epam.newsmanagement.common.dao.EntityDAO;
import com.epam.newsmanagement.common.dao.mapper.CommentMapper;
import com.epam.newsmanagement.common.entity.Comment;
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
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
   
    @Autowired
    public void setDataSource(DataSource dataSource){
    	this.dataSource = dataSource;
    	this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    /**
     * Implementation of {@link EntityDAO#create(Serializable)}
     * @param comment is parameter which need to insert in database
     * @return parameter comment_id from database
     * @throws DAOException if some problems in database
     */
    @Override
    public Long create(Comment comment) throws DAOException{
        KeyHolder keyHolder = new GeneratedKeyHolder();
    	if(comment != null){
    		jdbcTemplate.update(
    				new PreparedStatementCreator() {						
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement pst = con.prepareStatement(SQL_INSERT_COMMENT, new String[] {"comment_id"});
							pst.setLong(1, comment.getIdNews());
							pst.setString(2, comment.getCommentText());
							pst.setTimestamp(3, comment.getCreationDate());
							return pst;
						}
					}, 
    				keyHolder);
    	}
    	return (Long)keyHolder.getKey().longValue();
    }

    /**
     * Implementation of {@link EntityDAO#getById(Long)}
     * @param commentId is the field on which the search
     * @return {@link Comment}
     * @throws DAOException if some problems in database
     */
    @Override
    public Comment getById(Long commentId) throws DAOException{
        Comment comment = null;
        if(commentId != null){
        	comment = jdbcTemplate.queryForObject(SQL_SELECT_COMMENT_BY_COMMENT_ID, 
        			new Object[]{commentId}, new CommentMapper());
        	logger.debug("Selected comment by commentId=" + commentId + ";");
        }
        return comment;
    }

    /**
     * Implementation of {@link EntityDAO#getAll()}
     * @return list all comments from database
     * @throws DAOException if some problems in database
     */
    @Override
    public List<Comment> getAll() throws DAOException {
        List<Comment> commentList = null;
        commentList = jdbcTemplate.query(SQL_SELECT_ALL_COMMENTS, new CommentMapper());
        logger.debug("All comments selected;");
        return commentList;
    }

    /**
     * Implementation of {@link EntityDAO#countAll()}
     * @return the number commentes in database
     * @throws DAOException if some problems in database
     */
    @Override
    public Long countAll() throws DAOException{
        Long count = null;
        count = jdbcTemplate.queryForObject(SQL_SELECT_COUNT_ALL_COMMENTS, Long.class);
        logger.debug("All comments counted");
        return count;
    }

    /**
     * Implementation {@link EntityDAO#update(Serializable)}
     * @param comment is entity which need to update
     * @throws DAOException if some problems in database
     */
    @Override
    public void update(Comment comment) throws DAOException{
        if(comment != null){
        	jdbcTemplate.update(SQL_UPDATE_COMMENT_BY_COMMENT_ID, 
        			new Object[]{comment.getIdNews(), comment.getCommentText(), 
        					comment.getCreationDate(), comment.getIdComment()});
        	logger.debug("Comment=" + comment + " updated in table Comments;");
        }
    }

    /**
     * Implementation {@link EntityDAO#delete(Long)}
     * @param commentId is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public void delete(Long commentId) throws DAOException{
        if(commentId != null){
        	jdbcTemplate.update(SQL_DELETE_COMMENT_BY_COMMENT_ID, 
        			new Object[]{commentId});
        	logger.debug("Comment with commentId=" + commentId + " deleted from table Comment;");
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
        List<Comment> commentList = null;
        if(newsId != null){
        	commentList = jdbcTemplate.query(SQL_SELECT_COMMENT_BY_NEWS_ID, 
        			new Object[]{newsId}, new CommentMapper());
        	logger.debug("Selected comments by newsId=" + newsId + ";");
        }
        return commentList;
    }

    /**
     * Implementation {@link EntityDAO#delete(Long)}
     * @param newsId is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public void deleteCommentByNews(Long newsId) throws DAOException{
        if(newsId != null){
        	jdbcTemplate.update(SQL_DELETE_COMMENT_BY_NEWS_ID, 
        			new Object[]{newsId});
        	logger.debug("All comments for news where newsId=" + newsId + " deleted");
        }
    }

}
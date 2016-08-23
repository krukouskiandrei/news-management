package com.epam.newsmanagement.common.dao.impl;

import com.epam.newsmanagement.common.dao.CommentDAO;
import com.epam.newsmanagement.common.dao.EntityDAO;
import com.epam.newsmanagement.common.dao.util.ConnectionCloser;
import com.epam.newsmanagement.common.entity.Comment;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *Implementing of {@link CommentDAOImpl}
 */
@Repository
public class CommentDAOImpl implements CommentDAO{

    private final static String INSERT_COMMENT_SQL = "INSERT INTO COMMENTS(COMMENT_ID, NEWS_ID, " +
            "COMMENT_TEXT, CREATION_DATE) VALUES(COMMENT_SEQUENCE.NEXTVAL, ?, ?, ?)";
    private final static String SELECT_COMMENT_BY_COMMENT_ID_SQL = "SELECT COMMENT_ID, NEWS_ID, COMMENT_TEXT, CREATION_DATE" +
            " FROM COMMENTS WHERE COMMENT_ID = ?";
    private final static String SELECT_ALL_COMMENTS_SQL = "SELECT COMMENT_ID, NEWS_ID, COMMENT_TEXT, CREATION_DATE FROM COMMENTS";
    private final static String COMMENT_ID = "COMMENT_ID";
    private final static String SELECT_COUNT_ALL_COMMENTS_SQL = "SELECT COUNT(COMMENT_ID) FROM COMMENTS";
    private final static String UPDATE_COMMENT_BY_COMMENT_ID_SQL = "UPDATE COMMENTS SET NEWS_ID = ?, COMMENT_TEXT = ?, " +
            "CREATION_DATE = ? WHERE COMMENT_ID = ?";
    private final static String DELETE_COMMENT_BY_COMMENT_ID_SQL = "DELETE FROM COMMENTS WHERE COMMENT_ID = ?";
    private final static String SELECT_COMMENT_BY_NEWS_ID_SQL = "SELECT COMMENT_ID, NEWS_ID, COMMENT_TEXT, CREATION_DATE " +
            "FROM COMMENTS WHERE NEWS_ID = ?";
    private final static String DELETE_COMMENT_BY_NEWS_ID_SQL = "DELETE FROM COMMENTS WHERE NEWS_ID = ?";

    @Autowired
    DataSource dataSource;

    /**
     * Implementation of {@link EntityDAO#create(Serializable)}
     * @param comment is parameter which need to insert in database
     * @return parameter comment_id from database
     * @throws DAOException if some problems in database
     */
    @Override
    public Long create(Comment comment) throws DAOException{
        Long commentId = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_COMMENT_SQL, new String[]{COMMENT_ID});

            preparedStatement.setLong(1, comment.getIdNews());
            preparedStatement.setString(2, comment.getCommentText());
            preparedStatement.setTimestamp(3, comment.getCreationDate());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                commentId = resultSet.getLong(1);
                comment.setIdComment(commentId);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement, resultSet);
        }
        return commentId;
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_COMMENT_BY_COMMENT_ID_SQL);

            preparedStatement.setLong(1, commentId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                comment = createComment(resultSet);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement, resultSet);
        }
        return comment;
    }

    /**
     * create {@link Comment} which get from database
     * @param resultSet contains {@link Comment}
     * @return created comment
     * @throws DAOException if some problems in database
     */
    private Comment createComment(ResultSet resultSet) throws DAOException{
        Comment comment = null;
        try{
            comment = new Comment();
            comment.setIdComment(resultSet.getLong(1));
            comment.setIdNews(resultSet.getLong(2));
            comment.setCommentText(resultSet.getString(3));
            comment.setCreationDate(resultSet.getTimestamp(4));
        }catch (SQLException e){
            throw new DAOException(e);
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
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_COMMENTS_SQL);

            commentList = new ArrayList<Comment>();
            while (resultSet.next()){
                Comment comment = createComment(resultSet);
                commentList.add(comment);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, statement, resultSet);
        }
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
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_COUNT_ALL_COMMENTS_SQL);
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
     * @param comment is entity which need to update
     * @throws DAOException if some problems in database
     */
    @Override
    public void update(Comment comment) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_COMMENT_BY_COMMENT_ID_SQL);
            preparedStatement.setLong(1, comment.getIdNews());
            preparedStatement.setString(2, comment.getCommentText());
            preparedStatement.setTimestamp(3, comment.getCreationDate());
            preparedStatement.setLong(4, comment.getIdComment());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement);
        }
    }

    /**
     * Implementation {@link EntityDAO#delete(Long)}
     * @param commentId is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public void delete(Long commentId) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_COMMENT_BY_COMMENT_ID_SQL);
            preparedStatement.setLong(1, commentId);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement);
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_COMMENT_BY_NEWS_ID_SQL);

            preparedStatement.setLong(1, newsId);
            resultSet = preparedStatement.executeQuery();

            commentList = new ArrayList<Comment>();
            while (resultSet.next()){
                Comment comment = createComment(resultSet);
                commentList.add(comment);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement, resultSet);
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_COMMENT_BY_NEWS_ID_SQL);
            preparedStatement.setLong(1, newsId);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement);
        }
    }

}

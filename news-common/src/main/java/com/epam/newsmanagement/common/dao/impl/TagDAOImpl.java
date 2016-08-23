package com.epam.newsmanagement.common.dao.impl;

import com.epam.newsmanagement.common.dao.EntityDAO;
import com.epam.newsmanagement.common.dao.TagDAO;
import com.epam.newsmanagement.common.dao.util.ConnectionCloser;
import com.epam.newsmanagement.common.entity.Tag;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementing of {@link TagDAO}
 */
@Repository
public class TagDAOImpl implements TagDAO {

    private final static String INSERT_TAG_SQL = "INSERT INTO TAG(TAG_ID, TAG_NAME) VALUES(TAG_SEQUENCE.NEXTVAL, ?)";
    private final static String SELECT_TAG_BY_TAG_ID_SQL = "SELECT TAG_ID, TAG_NAME FROM TAG WHERE TAG_ID = ?";
    private final static String SELECT_ALL_TAGS_SQL = "SELECT TAG_ID, TAG_NAME FROM TAG";
    private final static String TAG_ID = "TAG_ID";
    private final static String SELECT_COUNT_ALL_TAGS_SQL = "SELECT COUNT(TAG_ID) FROM TAG";
    private final static String UPDATE_TAG_BY_TAG_ID_SQL = "UPDATE TAG SET TAG_NAME = ? WHERE TAG_ID = ?";
    private final static String DELETE_TAG_BY_TAG_ID_SQL = "DELETE FROM TAG WHERE TAG_ID = ?";

    @Autowired
    DataSource dataSource;

    /**
     * Implementation of {@link EntityDAO#create(Serializable)}
     * @param tag is parameter which need to insert in database
     * @return parameter tag_id from database
     * @throws DAOException if some problems in database
     */
    @Override
    public Long create(Tag tag) throws DAOException {
        Long tagId = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_TAG_SQL, new String[]{TAG_ID});

            preparedStatement.setString(1, tag.getTagName());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                tagId = resultSet.getLong(1);
                tag.setIdTag(tagId);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement, resultSet);
        }
        return tagId;
    }

    /**
     * Implementation of {@link EntityDAO#getById(Long)}
     * @param tagId is the field on which the search
     * @return {@link Tag}
     * @throws DAOException if some problems in database
     */
    @Override
    public Tag getById(Long tagId) throws DAOException{
        Tag tag = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_TAG_BY_TAG_ID_SQL);

            preparedStatement.setLong(1, tagId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                tag = createTag(resultSet);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement, resultSet);
        }
        return tag;
    }

    /**
     * create {@link Tag} which get from database
     * @param resultSet contains {@link Tag}
     * @return created role
     * @throws DAOException if some problems in database
     */
    private Tag createTag(ResultSet resultSet) throws  DAOException{
        Tag tag = null;
        try {
            tag = new Tag();
            tag.setIdTag(resultSet.getLong(1));
            tag.setTagName(resultSet.getString(2));
        }catch (SQLException e){
            throw new DAOException(e);
        }
        return tag;
    }

    /**
     * Implementation of {@link EntityDAO#getAll()}
     * @return list all tags from database
     * @throws DAOException if some problems in database
     */
    @Override
    public List<Tag> getAll() throws DAOException{
        List<Tag> tagList = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_TAGS_SQL);

            tagList = new ArrayList<Tag>();
            while (resultSet.next()){
                Tag news = createTag(resultSet);
                tagList.add(news);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, statement, resultSet);
        }
        return tagList;
    }

    /**
     * Implementation of {@link EntityDAO#countAll()}
     * @return the number tags in database
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
            resultSet = statement.executeQuery(SELECT_COUNT_ALL_TAGS_SQL);
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
     * @param tag is entity which need to update
     * @throws DAOException if some problems in database
     */
    @Override
    public void update(Tag tag) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_TAG_BY_TAG_ID_SQL);
            preparedStatement.setString(1, tag.getTagName());
            preparedStatement.setLong(2, tag.getIdTag());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement);
        }
    }

    /**
     * Implementation {@link EntityDAO#delete(Long)}
     * @param tagId is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public void delete(Long tagId) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_TAG_BY_TAG_ID_SQL);
            preparedStatement.setLong(1, tagId);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement);
        }
    }
}

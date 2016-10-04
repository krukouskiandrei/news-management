package com.epam.newsmanagement.common.dao.impl;

import com.epam.newsmanagement.common.dao.EntityDAO;
import com.epam.newsmanagement.common.dao.TagDAO;
import com.epam.newsmanagement.common.dao.utils.ConnectionCloser;
import com.epam.newsmanagement.common.entity.Tag;
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
 * Implementing of {@link TagDAO}
 */
@Repository
public class TagDAOImpl implements TagDAO {

    private final static String SQL_INSERT_TAG = "INSERT INTO TAG(TAG_ID, TAG_NAME) "
    											+ "VALUES(TAG_SEQUENCE.NEXTVAL, ?)";
    private final static String SQL_SELECT_TAG_BY_TAG_ID = "SELECT TAG_ID, TAG_NAME "
    														+ "FROM TAG "
    														+ "WHERE TAG_ID = ?";
    private final static String SQL_SELECT_ALL_TAGS = "SELECT TAG_ID, TAG_NAME "
    												+ "FROM TAG";
    private final static String SQL_SELECT_COUNT_ALL_TAGS = "SELECT COUNT(1) "
    														+ "FROM TAG";
    private final static String SQL_UPDATE_TAG_BY_TAG_ID = "UPDATE TAG "
    													+ "SET TAG_NAME = ? "
    													+ "WHERE TAG_ID = ?";
    private final static String SQL_DELETE_TAG_BY_TAG_ID = "DELETE FROM TAG "
    														+ "WHERE TAG_ID = ?";
    private final static String SQL_SELECT_TAG_BY_NEWS_ID = "SELECT TAG.TAG_ID, TAG.TAG_NAME "
    														+ "FROM TAG, NEWS_TAG "
    														+ "WHERE NEWS_TAG.NEWS_ID = ? AND NEWS_TAG.TAG_ID = TAG.TAG_ID";
    
    private final static Logger logger = LogManager.getLogger(TagDAOImpl.class);
    @Autowired
    private DataSource dataSource;
    
    
    /**
     * Implementation of {@link EntityDAO#create(Serializable)}
     * @param tag is parameter which need to insert in database
     * @return parameter tag_id from database
     * @throws DAOException if some problems in database
     */
    @Override
    public Long create(Tag tag) throws DAOException {
    	
    	if(tag != null){
        	Connection connection = null;
        	PreparedStatement statement = null;
        	ResultSet resultSet = null;
        	try{
        		connection = DataSourceUtils.doGetConnection(dataSource);
        		statement = connection.prepareStatement(SQL_INSERT_TAG, new String[]{"tag_id"});
        		statement.setString(1, tag.getTagName());
        		statement.executeUpdate();
        	
        		resultSet = statement.getGeneratedKeys();
        		Long tagId = null;
        		if(resultSet.next()){
        			tagId = resultSet.getLong(1);
        		}
        		return tagId;
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
     * @param tagId is the field on which the search
     * @return {@link Tag}
     * @throws DAOException if some problems in database
     */
    @Override
    public Tag getById(Long tagId) throws DAOException{
        
    	if(tagId != null && tagId > 0){
    		Connection connection = null;
    		PreparedStatement statement = null;
    		ResultSet resultSet = null;
    		try{
    			connection = DataSourceUtils.doGetConnection(dataSource);
    			statement = connection.prepareStatement(SQL_SELECT_TAG_BY_TAG_ID);
    			statement.setLong(1, tagId);
        	
    			resultSet = statement.executeQuery();
    			Tag tag = new Tag();
    			if(resultSet.next()){
    				tag = createTag(resultSet);
    			}
    			logger.debug("Tag selected by tagId=" + tagId);
    			return tag;
    		}catch(SQLException e){
    			throw new DAOException(e);
    		}finally{
    			ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
    		}
        }
    	throw new DAOException();
    	
    }

    
    private Tag createTag(ResultSet resultSet) throws SQLException{
    	Tag tag = new Tag();
    	tag.setIdTag(resultSet.getLong(1));
		tag.setTagName(resultSet.getString(2));
		return tag;   	
    }
    
    /**
     * Implementation of {@link EntityDAO#getAll()}
     * @return list all tags from database
     * @throws DAOException if some problems in database
     */
    @Override
    public List<Tag> getAll() throws DAOException{
        
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
        	connection = DataSourceUtils.doGetConnection(dataSource);
        	statement = connection.prepareStatement(SQL_SELECT_ALL_TAGS);
        	resultSet = statement.executeQuery();
        	List<Tag> tagList = new ArrayList<>();
        	while(resultSet.next()){
        		tagList.add(createTag(resultSet));
        	}
            logger.debug("All tags selected;");
            return tagList;
        }catch(SQLException e){
        	throw new DAOException(e);
        }finally{
        	ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
        }
    	
    }

    /**
     * Implementation of {@link EntityDAO#countAll()}
     * @return the number tags in database
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
        	resultSet = statement.executeQuery(SQL_SELECT_COUNT_ALL_TAGS);
        	Long count = 0L;
        	if(resultSet.next()){
        		count = resultSet.getLong(1);
        	}
        	logger.debug("All tags counted");
        	return count;
        }catch(SQLException e){
        	throw new DAOException(e);
        }finally{
        	ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
        }
    	
    	
    }

    /**
     * Implementation {@link EntityDAO#update(Serializable)}
     * @param tag is entity which need to update
     * @throws DAOException if some problems in database
     */
    @Override
    public void update(Tag tag) throws DAOException{
       
    	if(tag != null){
    		Connection connection = null;
            PreparedStatement statement = null;
            try{
            	connection = DataSourceUtils.doGetConnection(dataSource);
            	statement = connection.prepareStatement(SQL_UPDATE_TAG_BY_TAG_ID);
            	statement.setString(1, tag.getTagName());
            	statement.setLong(2, tag.getIdTag());
            	statement.executeUpdate();
            	logger.debug("Tag=" + tag + " updated");
            }catch(SQLException e){
            	throw new DAOException(e);
            }finally{
            	ConnectionCloser.closeConnection(connection, statement, dataSource);
            }
    	}
    	
    }

    /**
     * Implementation {@link EntityDAO#delete(Long)}
     * @param tagId is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public void delete(Long tagId) throws DAOException{
    	
    	if(tagId != null && tagId > 0){
    		Connection connection = null;
            PreparedStatement statement = null;
            try{
            	connection = DataSourceUtils.doGetConnection(dataSource);
            	statement = connection.prepareStatement(SQL_DELETE_TAG_BY_TAG_ID);
            	statement.setLong(1, tagId);
            	statement.executeUpdate();
            	logger.debug("Tag by tagId=" + tagId + " deleted");
            }catch(SQLException e){
            	throw new DAOException(e);
            }finally{
            	ConnectionCloser.closeConnection(connection, statement, dataSource);
            }
    	}
    	
    }
    
    /**
     * Implementation {@link TagDAO#getAllTagsForNews(Long)
     * @param newsId is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public List<Tag> getAllTagsForNews(Long newsId) throws DAOException{
    	
    	if(newsId != null && newsId > 0){
    		Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try{
            	connection = DataSourceUtils.doGetConnection(dataSource);
            	statement = connection.prepareStatement(SQL_SELECT_TAG_BY_NEWS_ID);
            	statement.setLong(1, newsId);
            	resultSet = statement.executeQuery();
            	List<Tag> tagList = new ArrayList<>();
            	while(resultSet.next()){
            		tagList.add(createTag(resultSet));
            	}
                logger.debug("All tags selected;");
                return tagList;
            }catch(SQLException e){
            	throw new DAOException(e);
            }finally{
            	ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
            }
    	}
    	throw new DAOException();
    	
    }
}
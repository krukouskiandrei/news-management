package com.epam.newsmanagement.common.dao.impl;

import com.epam.newsmanagement.common.dao.EntityDAO;
import com.epam.newsmanagement.common.dao.TagDAO;
import com.epam.newsmanagement.common.dao.mapper.TagMapper;
import com.epam.newsmanagement.common.entity.Tag;
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
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public void setDataSource(DataSource dataSource){
    	this.dataSource = dataSource;
    	this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    
    /**
     * Implementation of {@link EntityDAO#create(Serializable)}
     * @param tag is parameter which need to insert in database
     * @return parameter tag_id from database
     * @throws DAOException if some problems in database
     */
    @Override
    public Long create(Tag tag) throws DAOException {
    	KeyHolder keyHolder = new GeneratedKeyHolder();
    	if(tag != null){
    		jdbcTemplate.update(
    				new PreparedStatementCreator() {						
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement pst = con.prepareStatement(SQL_INSERT_TAG, new String[] {"tag_id"});
							pst.setString(1, tag.getTagName());
							return pst;
						}
					}, 
    				keyHolder);
    	}
    	return (Long)keyHolder.getKey().longValue();
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
        if(tagId != null){
        	tag = jdbcTemplate.queryForObject(SQL_SELECT_TAG_BY_TAG_ID, 
        			new Object[]{tagId}, new TagMapper());
        	logger.debug("Tag selected by tagId=" + tagId + ";");
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
        tagList = jdbcTemplate.query(SQL_SELECT_ALL_TAGS, new TagMapper());
        logger.debug("All tags selected;");
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
        count = jdbcTemplate.queryForObject(SQL_SELECT_COUNT_ALL_TAGS, Long.class);
        logger.debug("All tags counted;");
        return count;
    }

    /**
     * Implementation {@link EntityDAO#update(Serializable)}
     * @param tag is entity which need to update
     * @throws DAOException if some problems in database
     */
    @Override
    public void update(Tag tag) throws DAOException{
        if(tag != null){
        	jdbcTemplate.update(SQL_UPDATE_TAG_BY_TAG_ID, 
        			new Object[]{tag.getTagName(), tag.getIdTag()});
        	logger.debug("Tag=" + tag + " updated;");
        }
    }

    /**
     * Implementation {@link EntityDAO#delete(Long)}
     * @param tagId is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public void delete(Long tagId) throws DAOException{
        if(tagId != null){
        	jdbcTemplate.update(SQL_DELETE_TAG_BY_TAG_ID, new Object[]{tagId});
        	logger.debug("Tag by tagId=" + tagId + " deleted;");
        }
    }
    
    /**
     * Implementation {@link TagDAO#getAllTagsForNews(Long)
     * @param newsId is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public List<Tag> getAllTagsForNews(Long newsId) throws DAOException{
    	List<Tag> listTag = null;
    	if(newsId != null){
    		listTag = jdbcTemplate.query(SQL_SELECT_TAG_BY_NEWS_ID, 
    				new Object[]{newsId}, new TagMapper());
    		logger.debug("All tags selected for news where newsId=" + newsId);
    	}    	
    	return listTag;
    }
}
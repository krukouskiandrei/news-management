package com.epam.newsmanagement.common.dao.impl;

import com.epam.newsmanagement.common.dao.AuthorDAO;
import com.epam.newsmanagement.common.dao.EntityDAO;
import com.epam.newsmanagement.common.dao.mapper.AuthorMapper;
import com.epam.newsmanagement.common.entity.Author;
import com.epam.newsmanagement.common.exception.dao.DAOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.List;

/**
 *  Implementing of {@link AuthorDAO}
 */
@Repository
public class AuthorDAOImpl implements AuthorDAO {

    private final static String SQL_INSER_AUTHOR = "INSERT INTO AUTHOR(AUTHOR_ID, AUTHOR_NAME, EXPIRED) "
    												+ "VALUES(AUTHOR_SEQUENCE.NEXTVAL, ?, ?);";
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
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public void setDataSource(DataSource dataSource){
    	this.dataSource = dataSource;
    	this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    /**
     * Implementation of {@link EntityDAO#create(Serializable)}
     * @param author is parameter which need to insert in database
     * @return parameter author_id from database
     * @throws DAOException if some problems in database
     */
    @Override
    public void create(Author author) throws DAOException{
        if(author != null){
        	jdbcTemplate.update(SQL_INSER_AUTHOR, 
        			new Object[]{author.getAuthorName(), author.getExpiredDate()});
        	logger.debug("Author=" + author + " inserted in table Author;");
        }
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
        if(authorId != null){
        	author = jdbcTemplate.queryForObject(SQL_SELECT_AUTHOR_BY_AUTHOR_ID, 
        			new Object[]{authorId}, new AuthorMapper());
        	logger.debug("Author selected by authorId=" + authorId + ";");
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
        authorList = jdbcTemplate.query(SQL_SELECT_ALL_AUTHORES, new AuthorMapper());
        logger.debug("Selected all authors");
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
        count = jdbcTemplate.queryForObject(SQL_SELECT_COUNT_ALL_AUTHOR, Long.class);
        logger.debug("All authors counted");
        return count;
    }

    /**
     * Implementation {@link EntityDAO#update(Serializable)}
     * @param author is entity which need to update
     * @throws DAOException if some problems in database
     */
    @Override
    public void update(Author author) throws DAOException{
    	if(author != null){
        	jdbcTemplate.update(SQL_UPDATE_AUTHOR_BY_AUTHOR_ID, 
        			new Object[]{author.getAuthorName(), author.getExpiredDate(), author.getIdAuthor()});
        	logger.debug("Author=" + author + " updated in table Author;");
        }
    }

    /**
     * Implementation {@link EntityDAO#delete(Long)}
     * @param authorId is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public void delete(Long authorId) throws DAOException{
    	if(authorId != null){
        	jdbcTemplate.update(SQL_DELETE_AUTHOR_BY_AUTHOR_ID, 
        			new Object[]{authorId});
        	logger.debug("Author with authorId=" + authorId + " deleted from table Author;");
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
    	if(newsId != null){
    		List<Author> listAuthor = jdbcTemplate.query(SQL_SELECT_AUTHOR_BY_NEWS_ID, 
    				new Object[]{newsId}, new AuthorMapper());
    		if(!listAuthor.isEmpty()){
    			author = listAuthor.get(0);
    		}
    		logger.debug("Author selected by newsId=" + newsId + ";");
    	}
    	return author;
    }
}
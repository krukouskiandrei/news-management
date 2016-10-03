package com.epam.newsmanagement.common.dao.impl;

import com.epam.newsmanagement.common.dao.EntityDAO;
import com.epam.newsmanagement.common.dao.NewsDAO;
import com.epam.newsmanagement.common.dao.mapper.NewsMapper;
import com.epam.newsmanagement.common.dao.utils.FilterQueryBuilder;
import com.epam.newsmanagement.common.entity.News;
import com.epam.newsmanagement.common.entity.SearchParameter;
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
 * Implementing of {@link NewsDAO}
 */
@Repository
public class NewsDAOImpl implements NewsDAO {

    private final static String SQL_INSERT_NEWS = "INSERT INTO NEWS(NEWS_ID, TITLE, SHORT_TEXT, FULL_TEXT, CREATION_DATE, MODIFICATION_DATE) "
    											+ "VALUES(NEWS_SEQUENCE.NEXTVAL, ?, ?, ?, ?, ?)";
    private final static String SQL_SELECT_NEWS_BY_NEWS_ID = "SELECT NEWS_ID, TITLE, SHORT_TEXT, FULL_TEXT, CREATION_DATE, MODIFICATION_DATE "
    														+ "FROM NEWS "
    														+ "WHERE NEWS_ID = ?";
    private final static String SQL_SELECT_ALL_NEWSES = "SELECT N.NEWS_ID, N.TITLE, N.SHORT_TEXT, N.FULL_TEXT, N.CREATION_DATE, N.MODIFICATION_DATE, NVL(NQ.QT,0) QT "
    													+ "FROM NEWS N "
    													+ "LEFT JOIN ("
    														+ "SELECT NEWS_ID, COUNT(NEWS_ID) QT "
    														+ "FROM COMMENTS "
    														+ "GROUP BY NEWS_ID "
    														+ "ORDER BY QT DESC"
    													+ ") NQ ON N.NEWS_ID = NQ.NEWS_ID "
    													+ "ORDER BY QT DESC, N.MODIFICATION_DATE DESC";
    private final static String SQL_SELECT_COUNT_ALL_NEWSES = "SELECT COUNT(1) "
    														+ "FROM NEWS";
    private final static String SQL_UPDATE_NEWS_BY_NEWS_ID = "UPDATE NEWS "
    														+ "SET TITLE = ?, SHORT_TEXT = ?, FULL_TEXT = ?, CREATION_DATE = ?, MODIFICATION_DATE = ? "
    														+ "WHERE NEWS_ID = ?";
    private final static String SQL_DELETE_NEWS_BY_NEWS_ID = "DELETE FROM NEWS "
    														+ "WHERE NEWS_ID = ?";
    private final static String SQL_SELECT_NEWS_BY_AUTHOR_ID = "SELECT NEWS.NEWS_ID, NEWS.TITLE, NEWS.SHORT_TEXT, NEWS.FULL_TEXT, NEWS.CREATION_DATE, NEWS.MODIFICATION_DATE "
    															+ "FROM NEWS, NEWS_AUTHOR "
    															+ "WHERE NEWS.NEWS_ID = NEWS_AUTHOR.NEWS_ID AND NEWS_AUTHOR.AUTHOR_ID = ?";
    private final static String SQL_SELECT_NEWS_BY_TAG_ID = "SELECT NEWS.NEWS_ID, NEWS.TITLE, NEWS.SHORT_TEXT, NEWS.FULL_TEXT, NEWS.CREATION_DATE, NEWS.MODIFICATION_DATE "
    														+ "FROM NEWS, NEWS_TAG "
    														+ "WHERE NEWS.NEWS_ID = NEWS_TAG.NEWS_ID AND NEWS_TAG.TAG_ID = ?";
    private final static String SQL_SELECT_NEWS_BY_AUTHOR_AND_TAG = "SELECT NEWS.NEWS_ID, NEWS.TITLE, NEWS.SHORT_TEXT, NEWS.FULL_TEXT, NEWS.CREATION_DATE, NEWS.MODIFICATION_DATE "
    																+ "FROM NEWS, NEWS_TAG, NEWS_AUTHOR "
    																+ "WHERE NEWS.NEWS_ID = NEWS_TAG.NEWS_ID AND NEWS_TAG.TAG_ID = ? AND NEWS.NEWS_ID = NEWS_AUTHOR.NEWS_ID AND NEWS_AUTHOR.AUTHOR_ID = ?";
    private final static String SQL_SELECT_NEWSES_FROM_TO_ORDER = "SELECT * "
    															+ "FROM ("
    																+ "SELECT QRN.*, ROWNUM RNUM "
    																+ "FROM ("
    																	+ "SELECT N.NEWS_ID, N.TITLE, N.SHORT_TEXT, N.FULL_TEXT, N.CREATION_DATE, N.MODIFICATION_DATE, COUNT(C.NEWS_ID) as CC "
    																	+ "FROM NEWS N "
    																	+ "LEFT JOIN COMMENTS C ON N.NEWS_ID = C.NEWS_ID "
    																	+ "GROUP BY N.NEWS_ID, N.TITLE, N.SHORT_TEXT, N.FULL_TEXT, N.CREATION_DATE, N.MODIFICATION_DATE "
    																	+ "ORDER BY CC DESC, N.MODIFICATION_DATE DESC"
    																+ ") QRN "
    																+ "WHERE ROWNUM <= ?) "
    															+ "WHERE RNUM >= ?"; /*Select news order by	creation date 
    																							and count comments by more population (use ROWNUM, pagination)*/		
    private final static String SQL_CREATE_LINK_NEWS_AUTHOR = "INSERT INTO NEWS_AUTHOR(NEWS_ID, AUTHOR_ID) "
															+ "VALUES(?, ?)";
    private final static String SQL_CREATE_LINK_NEWS_TAG = "INSERT INTO NEWS_TAG(NEWS_ID, TAG_ID) "
															+ "VALUES(?, ?)";
    private final static Logger logger = LogManager.getLogger(NewsDAOImpl.class);
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
   
    @Autowired
    public void setDataSource(DataSource dataSource){
    	this.dataSource = dataSource;
    	this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }


    /**
     * Implementation of {@link EntityDAO#create(Serializable)}
     * @param news is parameter which need to insert in database
     * @return parameter news_id from database
     * @throws DAOException if some problems in database
     */
    @Override
    public Long create(News news) throws DAOException{
    	KeyHolder keyHolder = new GeneratedKeyHolder();
    	if(news != null){
    		jdbcTemplate.update(
    				new PreparedStatementCreator() {						
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							PreparedStatement pst = con.prepareStatement(SQL_INSERT_NEWS, new String[] {"news_id"});
							pst.setString(1, news.getTitle());
							pst.setString(2, news.getShortText());
							pst.setString(3, news.getFullText());
							pst.setTimestamp(4, news.getCreationDate());
							pst.setDate(5, news.getModificationDate());
							return pst;
						}
					}, 
    				keyHolder);
    	}
    	return (Long)keyHolder.getKey().longValue();
    }

    /**
     * Implementation of {@link EntityDAO#getById(Long)}
     * @param newsId is the field on which the search
     * @return {@link News}
     * @throws DAOException if some problems in database
     */
    @Override
    public News getById(Long newsId) throws DAOException{
        News news = null;
        if(newsId != null){
        	news = jdbcTemplate.queryForObject(SQL_SELECT_NEWS_BY_NEWS_ID, 
        			new Object[]{newsId}, new NewsMapper());
        	logger.debug("News selected by newsId=" + newsId + ";");
        }
        return news;
    }

    /**
     * Implementation of {@link EntityDAO#getAll()}
     * @return list all newses from database
     * @throws DAOException if some problems in database
     */
    @Override
    public List<News> getAll() throws DAOException{
        List<News> newsList = null;
        newsList = jdbcTemplate.query(SQL_SELECT_ALL_NEWSES, new NewsMapper());
        logger.debug("Selected all news;");
        return newsList;
    }

    /**
     * Implementation of {@link EntityDAO#countAll()}
     * @return the number newses in database
     * @throws DAOException if some problems in database
     */
    @Override
    public Long countAll() throws DAOException{
        Long count = null;
        count = jdbcTemplate.queryForObject(SQL_SELECT_COUNT_ALL_NEWSES, Long.class);
        logger.debug("All news counted;");
        return count;
    }

    /**
     * Implementation {@link EntityDAO#update(Serializable)}
     * @param news is entity which need to update
     * @throws DAOException if some problems in database
     */
    @Override
    public void update(News news) throws DAOException{
        if(news != null){
        	jdbcTemplate.update(SQL_UPDATE_NEWS_BY_NEWS_ID, 
        			new Object[]{news.getTitle(), news.getShortText(), news.getFullText(), 
        					news.getCreationDate(), news.getModificationDate(), news.getIdNews()});
        	logger.debug("News=" + news + " updated in table News;");
        }
    }
    /**
     * Implementation {@link EntityDAO#delete(Long)}
     * @param newsID is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public void delete(Long newsID) throws DAOException{
        if(newsID != null){
        	jdbcTemplate.update(SQL_DELETE_NEWS_BY_NEWS_ID, 
        			new Object[]{newsID});
        	logger.debug("News with newsId=" + newsID + " deleted from table News;");
        }
    }
    /**
     * Implementation {@link NewsDAO#paginationNews(int, int)}}
     * @param from and to is position news in all list
     * @throws DAOException if some problems in database
     */
    @Override
    public List<News> paginationNews(int from, int to) throws DAOException{
    	List<News> listNews = null;
    	if(from >= 0 && to >= 0 && from <= to){
    		listNews = jdbcTemplate.query(SQL_SELECT_NEWSES_FROM_TO_ORDER, 
    				new Object[]{to, from}, new NewsMapper());
    		logger.debug("Selected news by order from=" + from + " to=" + to +";");
    	}
    	return listNews;
    }
    /**
     * Implementation {@link NewsDAO#getNewsByAuthor(Long)}
     */
    @Override
    public List<News> getNewsByAuthor(Long authorId) throws DAOException{
    	List<News> listNews = null;
    	if(authorId != null){
    		listNews = jdbcTemplate.query(SQL_SELECT_NEWS_BY_AUTHOR_ID, 
    				new Object[]{authorId}, new NewsMapper());
    		logger.debug("Selected news by authorId=" + authorId + ";");
    	}
    	return listNews;
    }
    /**
     * Implementation {@link NewsDAO#getNewsByTag(Long)}
     */
    @Override
    public List<News> getNewsByTag(Long tagId) throws DAOException{
    	List<News> listNews = null;
    	if(tagId != null){
    		listNews = jdbcTemplate.query(SQL_SELECT_NEWS_BY_TAG_ID, 
    				new Object[]{tagId}, new NewsMapper());
    		logger.debug("Selected news by tagId=" + tagId + ";");
    	}
    	return listNews;
    }
    /**
     * Implementation {@link NewsDAO#getNewsByAuthorAndTag(Long, Long)}
     */
    @Override
    public List<News> getNewsByAuthorAndTag(Long tagId, Long authorId) throws DAOException{
    	List<News> listNews = null;
    	if(tagId != null && authorId != null){
    		listNews = jdbcTemplate.query(SQL_SELECT_NEWS_BY_AUTHOR_AND_TAG, 
    				new Object[]{tagId, authorId}, new NewsMapper());
    	}
    	return listNews;
    }
    
    /**
     * Implementation {@link NewsDAO#createNewsAuthorLink(Long, Long)}}
     */
    @Override
    public void createNewsAuthorLink(Long newsId, Long authorId) throws DAOException{
    	if(newsId != null && authorId != null){
    		jdbcTemplate.update(SQL_CREATE_LINK_NEWS_AUTHOR, new Object[]{newsId, authorId});
    	}
    }
    /**
     * Implementation {@link NewsDAO#createNewsTagLink(Long, Long)}
     */
    @Override
    public void createNewsTagLink(Long newsId, Long tagId) throws DAOException{
    	if(newsId != null && tagId != null){
    		jdbcTemplate.update(SQL_CREATE_LINK_NEWS_TAG, new Object[]{newsId, tagId});
    	}
    }
    /**
     * Implementation {@link NewsDAO#filterNews(SearchParameter)
     */
    @Override
    public List<News> filterNews(SearchParameter searchParameter) throws DAOException{
    	List<News> listNews = null;
    	if(searchParameter != null){
    		listNews = jdbcTemplate.query(FilterQueryBuilder.buildFilterQuery(searchParameter), new NewsMapper());
    	}
    	return listNews;
    }
    /**
     * Implementation {@link NewsDAO#filterNews(SearchParameter, int, int)
     */
    @Override 
    public List<News> filterNews(SearchParameter searchParameter, int from, int to) throws DAOException{
    	List<News> listNews = null;
    	if(searchParameter != null && from >= 0 && to >= 0 && to >= from){
    		listNews = jdbcTemplate.query(FilterQueryBuilder.buildFilterQuery(searchParameter, from, to), new NewsMapper());
    	}
    	return listNews;
    }
}
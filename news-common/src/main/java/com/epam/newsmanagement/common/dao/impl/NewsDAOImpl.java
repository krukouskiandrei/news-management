package com.epam.newsmanagement.common.dao.impl;

import com.epam.newsmanagement.common.dao.EntityDAO;
import com.epam.newsmanagement.common.dao.NewsDAO;
import com.epam.newsmanagement.common.dao.utils.ConnectionCloser;
import com.epam.newsmanagement.common.dao.utils.FilterQueryBuilder;
import com.epam.newsmanagement.common.entity.News;
import com.epam.newsmanagement.common.entity.SearchParameter;
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
    private final static String SQL_DELETE_LINK_NEWS_TAG = "DELETE FROM NEWS_TAG "
    														+ "WHERE NEWS_ID = ?";
    private final static String SQL_DELETE_LINK_NEWS_AUTHOR = "DELETE FROM NEWS_AUTHOR "
    														+ "WHERE NEWS_ID = ?";
    private final static Logger logger = LogManager.getLogger(NewsDAOImpl.class);
    @Autowired
    private DataSource dataSource;
    


    /**
     * Implementation of {@link EntityDAO#create(Serializable)}
     * @param news is parameter which need to insert in database
     * @return parameter news_id from database
     * @throws DAOException if some problems in database
     */
    @Override
    public Long create(News news) throws DAOException{
    	
    	if(news != null){
        	Connection connection = null;
        	PreparedStatement statement = null;
        	ResultSet resultSet = null;
        	try{
        		connection = DataSourceUtils.doGetConnection(dataSource);
        		statement = connection.prepareStatement(SQL_INSERT_NEWS, new String[]{"news_id"});
        		statement.setString(1, news.getTitle());
        		statement.setString(2, news.getShortText());
        		statement.setString(3, news.getFullText());
        		statement.setTimestamp(4, news.getCreationDate());
        		statement.setDate(5, news.getModificationDate());
        		statement.executeUpdate();
        	
        		resultSet = statement.getGeneratedKeys();
        		Long newsId = null;
        		if(resultSet.next()){
        			newsId = resultSet.getLong(1);
        		}
        		return newsId;
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
     * @param newsId is the field on which the search
     * @return {@link News}
     * @throws DAOException if some problems in database
     */
    @Override
    public News getById(Long newsId) throws DAOException{
        
    	if(newsId != null && newsId > 0){
    		Connection connection = null;
    		PreparedStatement statement = null;
    		ResultSet resultSet = null;
    		try{
    			connection = DataSourceUtils.doGetConnection(dataSource);
    			statement = connection.prepareStatement(SQL_SELECT_NEWS_BY_NEWS_ID);
    			statement.setLong(1, newsId);
        	
    			resultSet = statement.executeQuery();
    			News news = new News();
    			if(resultSet.next()){
        		news = createNews(resultSet);
    			}
    			logger.debug("News selected by newsId=" + newsId);
    			return news;
    		}catch(SQLException e){
    			throw new DAOException(e);
    		}finally{
    			ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
    		}
        }
    	throw new DAOException();
    	    	
    }

    private News createNews(ResultSet resultSet) throws SQLException{
    	News news = new News();
    	news.setIdNews(resultSet.getLong(1));
		news.setTitle(resultSet.getString(2));
		news.setShortText(resultSet.getString(3));
		news.setFullText(resultSet.getString(4));
		news.setCreationDate(resultSet.getTimestamp(5));
		news.setModificationDate(resultSet.getDate(6));
    	return news;    	
    }
    
    /**
     * Implementation of {@link EntityDAO#getAll()}
     * @return list all newses from database
     * @throws DAOException if some problems in database
     */
    @Override
    public List<News> getAll() throws DAOException{
        
    	Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
        	connection = DataSourceUtils.doGetConnection(dataSource);
        	statement = connection.prepareStatement(SQL_SELECT_ALL_NEWSES);
        	resultSet = statement.executeQuery();
        	List<News> newsList = new ArrayList<>();
        	while(resultSet.next()){
        		newsList.add(createNews(resultSet));
        	}
        	logger.debug("Selected all news");
            return newsList;
        }catch(SQLException e){
        	throw new DAOException(e);
        }finally{
        	ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
        }
    	
    }

    /**
     * Implementation of {@link EntityDAO#countAll()}
     * @return the number newses in database
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
        	resultSet = statement.executeQuery(SQL_SELECT_COUNT_ALL_NEWSES);
        	Long count = 0L;
        	if(resultSet.next()){
        		count = resultSet.getLong(1);
        	}
        	logger.debug("All news counted");
        	return count;
        }catch(SQLException e){
        	throw new DAOException(e);
        }finally{
        	ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
        }
    	
    }

    /**
     * Implementation {@link EntityDAO#update(Serializable)}
     * @param news is entity which need to update
     * @throws DAOException if some problems in database
     */
    @Override
    public void update(News news) throws DAOException{
        
    	if(news != null){
    		Connection connection = null;
            PreparedStatement statement = null;
            try{
            	connection = DataSourceUtils.doGetConnection(dataSource);
            	statement = connection.prepareStatement(SQL_UPDATE_NEWS_BY_NEWS_ID);
            	statement.setString(1, news.getTitle());
            	statement.setString(2, news.getShortText());
            	statement.setString(3, news.getFullText());
            	statement.setTimestamp(4, news.getCreationDate());
            	statement.setDate(5, news.getModificationDate());
            	statement.setLong(6, news.getIdNews());
            	statement.executeUpdate();
            	logger.debug("News=" + news + " updated in table News;");
            }catch(SQLException e){
            	throw new DAOException(e);
            }finally{
            	ConnectionCloser.closeConnection(connection, statement, dataSource);
            }
    	}
    	
    }
    /**
     * Implementation {@link EntityDAO#delete(Long)}
     * @param newsID is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public void delete(Long newsID) throws DAOException{

    	if(newsID != null && newsID > 0){
    		Connection connection = null;
            PreparedStatement statement = null;
            try{
            	connection = DataSourceUtils.doGetConnection(dataSource);
            	statement = connection.prepareStatement(SQL_DELETE_NEWS_BY_NEWS_ID);
            	statement.setLong(1, newsID);
            	statement.executeUpdate();
            	logger.debug("News with newsId=" + newsID + " deleted from table News;");
            }catch(SQLException e){
            	throw new DAOException(e);
            }finally{
            	ConnectionCloser.closeConnection(connection, statement, dataSource);
            }
    	}
    	
    }
    /**
     * Implementation {@link NewsDAO#paginationNews(int, int)}}
     * @param from and to is position news in all list
     * @throws DAOException if some problems in database
     */
    @Override
    public List<News> paginationNews(int from, int to) throws DAOException{
    	if(from >= 0 && to >= 0 && from <= to){
    		Connection connection = null;
    		PreparedStatement statement = null;
    		ResultSet resultSet = null;
    		try{
    			connection = DataSourceUtils.doGetConnection(dataSource);
    			statement = connection.prepareStatement(SQL_SELECT_NEWSES_FROM_TO_ORDER);
    			statement.setInt(1, to);
    			statement.setInt(2, from);
    			resultSet = statement.executeQuery();
    			List<News> newsList = new ArrayList<>();
    			while(resultSet.next()){
    				newsList.add(createNews(resultSet));
    			}
    			logger.debug("Selected news by order from=" + from + " to=" + to +";");
    			return newsList;
    		}catch(SQLException e){
    			throw new DAOException(e);
    		}finally{
    			ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
    		}
    	}
    	throw new DAOException();
    	
    }
    /**
     * Implementation {@link NewsDAO#getNewsByAuthor(Long)}
     */
    @Override
    public List<News> getNewsByAuthor(Long authorId) throws DAOException{
    	if(authorId != null && authorId > 0){
    		Connection connection = null;
    		PreparedStatement statement = null;
    		ResultSet resultSet = null;
    		try{
    			connection = DataSourceUtils.doGetConnection(dataSource);
    			statement = connection.prepareStatement(SQL_SELECT_NEWS_BY_AUTHOR_ID);
    			statement.setLong(1, authorId);
    			resultSet = statement.executeQuery();
    			List<News> newsList = new ArrayList<>();
    			while(resultSet.next()){
    				newsList.add(createNews(resultSet));
    			}
    			logger.debug("Selected news by authorId=" + authorId + ";");
    			return newsList;
    		}catch(SQLException e){
    			throw new DAOException(e);
    		}finally{
    			ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
    		}
    	}
    	throw new DAOException();
    	
    }
    /**
     * Implementation {@link NewsDAO#getNewsByTag(Long)}
     */
    @Override
    public List<News> getNewsByTag(Long tagId) throws DAOException{
    	
    	if(tagId != null && tagId > 0){
    		Connection connection = null;
    		PreparedStatement statement = null;
    		ResultSet resultSet = null;
    		try{
    			connection = DataSourceUtils.doGetConnection(dataSource);
    			statement = connection.prepareStatement(SQL_SELECT_NEWS_BY_TAG_ID);
    			statement.setLong(1, tagId);
    			resultSet = statement.executeQuery();
    			List<News> newsList = new ArrayList<>();
    			while(resultSet.next()){
    				newsList.add(createNews(resultSet));
    			}
    			logger.debug("Selected news by tagId=" + tagId + ";");
    			return newsList;
    		}catch(SQLException e){
    			throw new DAOException(e);
    		}finally{
    			ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
    		}
    	}
    	throw new DAOException();
    	
    }
    /**
     * Implementation {@link NewsDAO#getNewsByAuthorAndTag(Long, Long)}
     */
    @Override
    public List<News> getNewsByAuthorAndTag(Long tagId, Long authorId) throws DAOException{
    	
    	if(tagId != null && authorId != null && tagId > 0 && authorId > 0){
    		Connection connection = null;
    		PreparedStatement statement = null;
    		ResultSet resultSet = null;
    		try{
    			connection = DataSourceUtils.doGetConnection(dataSource);
    			statement = connection.prepareStatement(SQL_SELECT_NEWS_BY_AUTHOR_AND_TAG);
    			statement.setLong(1, tagId);
    			statement.setLong(2, authorId);
    			resultSet = statement.executeQuery();
    			List<News> newsList = new ArrayList<>();
    			while(resultSet.next()){
    				newsList.add(createNews(resultSet));
    			}
    			logger.debug("Selected news by tagId=" + tagId + " and authorId=" + authorId);
    			return newsList;
    		}catch(SQLException e){
    			throw new DAOException(e);
    		}finally{
    			ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
    		}
    	}
    	throw new DAOException();
    	
    }
    
    /**
     * Implementation {@link NewsDAO#createNewsAuthorLink(Long, Long)}}
     */
    @Override
    public void createNewsAuthorLink(Long newsId, Long authorId) throws DAOException{
    	if(newsId != null && authorId != null && newsId > 0 && authorId > 0){
    		Connection connection = null;
    		PreparedStatement statement = null;
    		try{
    			connection = DataSourceUtils.doGetConnection(dataSource);
    			statement = connection.prepareStatement(SQL_CREATE_LINK_NEWS_AUTHOR);
    			statement.setLong(1, newsId);
    			statement.setLong(2, authorId);
    			statement.executeUpdate();    			
    		}catch(SQLException e){
    			throw new DAOException(e);
    		}finally{
    			ConnectionCloser.closeConnection(connection, statement, dataSource);
    		}
    	}
    	    	
    }
    /**
     * Implementation {@link NewsDAO#createNewsTagLink(Long, Long)}
     */
    @Override
    public void createNewsTagLink(Long newsId, Long tagId) throws DAOException{
    	if(newsId != null && tagId != null && newsId > 0 && tagId > 0){
    		Connection connection = null;
    		PreparedStatement statement = null;
    		try{
    			connection = DataSourceUtils.doGetConnection(dataSource);
    			statement = connection.prepareStatement(SQL_CREATE_LINK_NEWS_TAG);
    			statement.setLong(1, newsId);
    			statement.setLong(2, tagId);
    			statement.executeUpdate();    			
    		}catch(SQLException e){
    			throw new DAOException(e);
    		}finally{
    			ConnectionCloser.closeConnection(connection, statement, dataSource);
    		}
    	}
    	
    }
    /**
     * Implementation {@link NewsDAO#filterNews(SearchParameter)
     */
    @Override
    public List<News> filterNews(SearchParameter searchParameter) throws DAOException{
    	if(searchParameter != null){
    		Connection connection = null;
    		PreparedStatement statement = null;
    		ResultSet resultSet = null;
    		try{
    			connection = DataSourceUtils.doGetConnection(dataSource);
    			statement = connection.prepareStatement(FilterQueryBuilder.buildFilterQuery(searchParameter));
    			resultSet = statement.executeQuery();
    			List<News> newsList = new ArrayList<>();
    			while(resultSet.next()){
    				newsList.add(createNews(resultSet));
    			}
    			return newsList;
    		}catch(SQLException e){
    			throw new DAOException(e);
    		}finally{
    			ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
    		}
    	}
    	throw new DAOException();
    	
    }
    /**
     * Implementation {@link NewsDAO#filterNews(SearchParameter, int, int)
     */
    @Override 
    public List<News> filterNews(SearchParameter searchParameter, int from, int to) throws DAOException{
    	
    	if(searchParameter != null){
    		Connection connection = null;
    		PreparedStatement statement = null;
    		ResultSet resultSet = null;
    		try{
    			connection = DataSourceUtils.doGetConnection(dataSource);
    			statement = connection.prepareStatement(FilterQueryBuilder.buildFilterQuery(searchParameter, from, to));
    			resultSet = statement.executeQuery();
    			List<News> newsList = new ArrayList<>();
    			while(resultSet.next()){
    				newsList.add(createNews(resultSet));
    			}
    			return newsList;
    		}catch(SQLException e){
    			throw new DAOException(e);
    		}finally{
    			ConnectionCloser.closeConnection(connection, statement, dataSource, resultSet);
    		}
    	}
    	throw new DAOException();
    	
    }
    /**
     * Implementation {@link NewsDAO#deleteNewsTagLinks(Long)
     */
    @Override
    public void deleteNewsTagLinks(Long newsId) throws DAOException{
    	
    	if(newsId != null  && newsId > 0){
    		Connection connection = null;
    		PreparedStatement statement = null;
    		try{
    			connection = DataSourceUtils.doGetConnection(dataSource);
    			statement = connection.prepareStatement(SQL_DELETE_LINK_NEWS_TAG);
    			statement.setLong(1, newsId);
    			statement.executeUpdate(); 
    			logger.debug("Deleted all rows in table NEWS_TAG for newsId=" + newsId);
    		}catch(SQLException e){
    			throw new DAOException(e);
    		}finally{
    			ConnectionCloser.closeConnection(connection, statement, dataSource);
    		}
    	}
    	
    }
    /**
     * Implementation {@link NewsDAO#deleteNewsAuthorLink(Long)
     */
    @Override
    public void deleteNewsAuthorLink(Long newsId) throws DAOException{
    	
    	if(newsId != null  && newsId > 0){
    		Connection connection = null;
    		PreparedStatement statement = null;
    		try{
    			connection = DataSourceUtils.doGetConnection(dataSource);
    			statement = connection.prepareStatement(SQL_DELETE_LINK_NEWS_AUTHOR);
    			statement.setLong(1, newsId);
    			statement.executeUpdate(); 
    			logger.debug("Deleted row in table NEWS_AUTHOR for newsId=" + newsId);
    		}catch(SQLException e){
    			throw new DAOException(e);
    		}finally{
    			ConnectionCloser.closeConnection(connection, statement, dataSource);
    		}
    	}
    	
    }
}
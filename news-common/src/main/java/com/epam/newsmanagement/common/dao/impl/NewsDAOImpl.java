package com.epam.newsmanagement.common.dao.impl;

import com.epam.newsmanagement.common.dao.EntityDAO;
import com.epam.newsmanagement.common.dao.NewsDAO;
import com.epam.newsmanagement.common.dao.util.ConnectionCloser;
import com.epam.newsmanagement.common.entity.News;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementing of {@link NewsDAO}
 */
@Repository
public class NewsDAOImpl implements NewsDAO {

    private final static String INSERT_NEWS_SQL = "INSERT INTO NEWS(NEWS_ID, TITLE, SHORT_TEXT, " +
            "FULL_TEXT, CREATION_DATE, MODIFICATION_DATE) VALUES(NEWS_SEQUENCE.NEXTVAL, ?, ?, ?, ?, ?";
    private final static String SELECT_NEWS_BY_NEWS_ID_SQL = "SELECT NEWS_ID, TITLE, SHORT_TEXT, FULL_TEXT," +
            " CREATION_DATE, MODIFICATION_DATE FROM NEWS WHERE NEWS_ID = ?";
    private final static String SELECT_ALL_NEWSES_SQL = "SELECT NEWS_ID, TITLE, SHORT_TEXT, FULL_TEXT," +
            " CREATION_DATE, MODIFICATION_DATE FROM NEWS";
    private final static String NEWS_ID = "NEWS_ID";
    private final static String SELECT_COUNT_ALL_NEWSES_SQL = "SELECT COUNT(NEWS_ID) FROM NEWS";
    private final static String UPDATE_NEWS_BY_NEWS_ID_SQL = "UPDATE NEWS SET TITLE = ?, SHORT_TEXT = ?, FULL_TEXT = ?, " +
            "CREATION_DATE = ?, MODIFICATION_DATE = ? WHERE NEWS_ID = ?";
    private final static String DELETE_NEWS_BY_NEWS_ID_SQL = "DELETE FROM NEWS WHERE NEWS_ID = ?";
    private final static String SELECT_NEWS_BY_AUTHOR_ID_SQL = "SELECT NEWS.NEWS_ID, NEWS.TITLE, "
    		+ "NEWS.SHORT_TEXT, NEWS.FULL_TEXT, NEWS.CREATION_DATE, NEWS.MODIFICATION_DATE "
    		+ "FROM NEWS, NEWS_AUTHOR WHERE NEWS.NEWS_ID = NEWS_AUTHOR.NEWS_ID AND NEWS_AUTHOR.AUTHOR_ID = ?";
    private final static String SELECT_NEWS_BY_TAG_ID_SQL = "SELECT NEWS.NEWS_ID, NEWS.TITLE, "
    		+ "NEWS.SHORT_TEXT, NEWS.FULL_TEXT, NEWS.CREATION_DATE, NEWS.MODIFICATION_DATE "
    		+ "FROM NEWS, NEWS_TAG WHERE NEWS.NEWS_ID = NEWS_TAG.NEWS_ID AND NEWS_TAG.TAG_ID = ?";
    private final static String SELECT_NEWS_BY_AUTHOR_AND_TAG_SQL = "SELECT NEWS.NEWS_ID, NEWS.TITLE, "
    		+ "NEWS.SHORT_TEXT, NEWS.FULL_TEXT, NEWS.CREATION_DATE, NEWS.MODIFICATION_DATE FROM NEWS, "
    		+ "NEWS_TAG, NEWS_AUTHOR WHERE NEWS.NEWS_ID = NEWS_TAG.NEWS_ID AND NEWS_TAG.TAG_ID = ? "
    		+ "AND NEWS.NEWS_ID = NEWS_AUTHOR.NEWS_ID AND NEWS_AUTHOR.AUTHOR_ID = ?";
    private final static String SELECT_NEWSES_FROM_TO_ORDER_SQL = "SELECT * FROM (SELECT a.*, "
    		+ "ROWNUM rnum FROM (SELECT n.NEWS_ID, n.TITLE, n.SHORT_TEXT, n.FULL_TEXT, n.CREATION_DATE, "
    		+ "n.MODIFICATION_DATE, COUNT(c.NEWS_ID) as co FROM NEWS n LEFT JOIN COMMENTS c ON n.NEWS_ID "
    		+ "= c.NEWS_ID GROUP BY n.NEWS_ID, n.TITLE, n.SHORT_TEXT, n.FULL_TEXT, n.CREATION_DATE, "
    		+ "n.MODIFICATION_DATE ORDER BY n.CREATION_DATE DESC, co DESC) a WHERE ROWNUM <= ?) WHERE rnum >= ?"; /*Select news order by 
    																												creation date and 
    																												count comments by more
    																												population (use ROWNUM,
    																												 pagination)*/		
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
        Long newsId = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_NEWS_SQL, new String[]{NEWS_ID});

            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getShortText());
            preparedStatement.setString(3, news.getFullText());
            preparedStatement.setTimestamp(4, news.getCreationDate());
            preparedStatement.setDate(5, news.getModificationDate());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                newsId = resultSet.getLong(1);
                news.setIdNews(newsId);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement, resultSet);
        }
        return newsId;
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_NEWS_BY_NEWS_ID_SQL);

            preparedStatement.setLong(1, newsId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                news = createNews(resultSet);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement, resultSet);
        }
        return news;
    }

    /**
     * create {@link News} which get from database
     * @param resultSet contains {@link News}
     * @return created role
     * @throws DAOException if some problems in database
     */
    private News createNews(ResultSet resultSet) throws  DAOException{
        News news = null;
        try {
            news = new News();
            news.setIdNews(resultSet.getLong(1));
            news.setTitle(resultSet.getString(2));
            news.setShortText(resultSet.getString(3));
            news.setFullText(resultSet.getString(4));
            news.setCreationDate(resultSet.getTimestamp(5));
            news.setModificationDate(resultSet.getDate(6));
        }catch (SQLException e){
            throw new DAOException(e);
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
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_NEWSES_SQL);

            newsList = new ArrayList<News>();
            while (resultSet.next()){
                News news = createNews(resultSet);
                newsList.add(news);
            }
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, statement, resultSet);
        }
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
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_COUNT_ALL_NEWSES_SQL);
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
     * @param news is entity which need to update
     * @throws DAOException if some problems in database
     */
    @Override
    public void update(News news) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_NEWS_BY_NEWS_ID_SQL);
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getShortText());
            preparedStatement.setString(3, news.getFullText());
            preparedStatement.setTimestamp(4, news.getCreationDate());
            preparedStatement.setDate(5, news.getModificationDate());
            preparedStatement.setLong(6, news.getIdNews());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement);
        }
    }
    /**
     * Implementation {@link EntityDAO#delete(Long)}
     * @param newsID is the field on which the search
     * @throws DAOException if some problems in database
     */
    @Override
    public void delete(Long newsID) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_NEWS_BY_NEWS_ID_SQL);
            preparedStatement.setLong(1, newsID);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            ConnectionCloser.closeConnection(connection, dataSource, preparedStatement);
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
    	Connection connection = null;
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;
    	try{
    		connection = dataSource.getConnection();
    		preparedStatement = connection.prepareStatement(SELECT_NEWSES_FROM_TO_ORDER_SQL);
    		preparedStatement.setInt(1, to);
    		preparedStatement.setInt(2,  from);
    		resultSet = preparedStatement.executeQuery();
    		listNews =  new ArrayList<>();
    		while(resultSet.next()){
    			News news = createNews(resultSet);
    			listNews.add(news);
    		}
    	}catch(SQLException e){
    		throw new DAOException(e);
    	}finally{
    		ConnectionCloser.closeConnection(connection, dataSource, preparedStatement, resultSet);
    	}
    	return listNews;
    }
    /**
     * Implementation {@link NewsDAO#getNewsByAuthor(Long)}
     */
    @Override
    public List<News> getNewsByAuthor(Long authorId) throws DAOException{
    	List<News> listNews = null;
    	Connection connection = null;
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;
    	try{
    		connection = dataSource.getConnection();
    		preparedStatement = connection.prepareStatement(SELECT_NEWS_BY_AUTHOR_ID_SQL);
    		preparedStatement.setLong(1, authorId);
    		resultSet = preparedStatement.executeQuery();
    		listNews = new ArrayList<>();
    		while(resultSet.next()){
    			News news = createNews(resultSet);
    			listNews.add(news);
    		}
    	}catch(SQLException e){
    		throw new DAOException(e);
    	}finally {
			ConnectionCloser.closeConnection(connection, dataSource, preparedStatement, resultSet);
		}
    	return listNews;
    }
    /**
     * Implementation {@link NewsDAO#getNewsByTag(Long)}
     */
    @Override
    public List<News> getNewsByTag(Long tagId) throws DAOException{
    	List<News> listNews = null;
    	Connection connection = null;
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;
    	try{
    		connection = dataSource.getConnection();
    		preparedStatement = connection.prepareStatement(SELECT_NEWS_BY_TAG_ID_SQL);
    		preparedStatement.setLong(1,  tagId);
    		resultSet = preparedStatement.executeQuery();
    		listNews = new ArrayList<>();
    		while(resultSet.next()){
    			News news = createNews(resultSet);
    			listNews.add(news);
    		}
    	}catch(SQLException e){
    		throw new DAOException(e);
    	}finally{
    		ConnectionCloser.closeConnection(connection, dataSource, preparedStatement, resultSet);
    	}
    	return listNews;
    }
    /**
     * Implementation {@link NewsDAO#getNewsByAuthorAndTag(Long, Long)}
     */
    @Override
    public List<News> getNewsByAuthorAndTag(Long tagId, Long authorId) throws DAOException{
    	List<News> listNews = null;
    	Connection connection = null;
    	PreparedStatement preparedStatement = null;
    	ResultSet resultSet = null;
    	try{
    		connection = dataSource.getConnection();
    		preparedStatement = connection.prepareStatement(SELECT_NEWS_BY_AUTHOR_AND_TAG_SQL);
    		preparedStatement.setLong(1, tagId);
    		preparedStatement.setLong(2, authorId);
    		resultSet = preparedStatement.executeQuery();
    		listNews = new ArrayList<>();
    		while(resultSet.next()){
    			News news = createNews(resultSet);
    			listNews.add(news);
    		}
    	}catch(SQLException e){
    		throw new DAOException(e);
    	}finally{
    		ConnectionCloser.closeConnection(connection, dataSource, preparedStatement, resultSet);
    	}
    	return listNews;
    }
}
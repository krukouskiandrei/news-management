package com.epam.newsmanagement.common.service.impl;

import com.epam.newsmanagement.common.dao.AuthorDAO;
import com.epam.newsmanagement.common.dao.CommentDAO;
import com.epam.newsmanagement.common.dao.NewsDAO;
import com.epam.newsmanagement.common.dao.TagDAO;
import com.epam.newsmanagement.common.entity.Author;
import com.epam.newsmanagement.common.entity.Comment;
import com.epam.newsmanagement.common.entity.News;
import com.epam.newsmanagement.common.entity.NewsInfo;
import com.epam.newsmanagement.common.entity.SearchParameter;
import com.epam.newsmanagement.common.entity.Tag;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import com.epam.newsmanagement.common.exception.service.ServiceException;
import com.epam.newsmanagement.common.service.EntityService;
import com.epam.newsmanagement.common.service.NewsService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementing of {@link NewsService}
 */
@Service
@Transactional(rollbackFor = ServiceException.class)
public class NewsServiceImpl implements NewsService{

    private final static Logger logger = LogManager.getLogger(NewsServiceImpl.class);
    @Autowired
    private NewsDAO newsDAO;
    @Autowired
    private TagDAO tagDAO;
    @Autowired
    private AuthorDAO authorDAO;
    @Autowired
    private CommentDAO commentDAO;

    /**
     * Implementation of {@link EntityService#create(Serializable)}
     * @param news is parameter which need to insert in database
     * @return parameter news_id from database
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    public void create(News news) throws ServiceException{
        if(news != null){
        	try {
        		newsDAO.create(news);
        	}catch (DAOException e){
        		logger.error("Failed to create news" + news, e);
        		throw new ServiceException(e);
        	}
        }
    }

    /**
     * Implementation of {@link EntityService#getById(Long)}
     * @param newsId is the field on which the search
     * @return {@link News}
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    public News getById(Long newsId) throws ServiceException{
        if(newsId != null){
        	try {
        		return newsDAO.getById(newsId);
        	}catch (DAOException e){
        		logger.error("Failed to get news by news id where newsId=" + newsId, e);
        		throw new ServiceException(e);
        	}
        }
        throw new ServiceException();
    }

    /**
     * Implementation of {@link EntityService#getAll()}
     * @return list all newses from database
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    public List<News> getAll() throws ServiceException{
        try {
            return newsDAO.getAll();
        }catch (DAOException e){
            logger.error("Failed to get all newses", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Implementation of {@link EntityService#countAll()}
     * @return the number newses in database
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    public Long countAll() throws ServiceException{
        try {
            return newsDAO.countAll();
        }catch (DAOException e){
            logger.error("Failed to count newses", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Implementation {@link EntityService#update(Serializable)}
     * @param news is entity which need to update
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    public void update(News news) throws ServiceException{
        if(news != null){
        	try {
        		newsDAO.update(news);
        	}catch (DAOException e){
        		logger.error("Failed to update news" + news, e);
        		throw new ServiceException(e);
        	}
        }
    }

    /**
     * Implementation {@link EntityService#delete(Long)}
     * @param newsID is the field on which the search
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    public void delete(Long newsID) throws ServiceException{
        if(newsID != null){
        	try {
        		newsDAO.delete(newsID);
        	}catch (DAOException e){
        		logger.error("Failed to delete news where newsId=" + newsID, e);
        		throw new ServiceException(e);
        	}
        }
    }
    /**
     * Implementation {@link NewsService#getAllNewsWithInfo()}
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    public List<NewsInfo> getAllNewsWithInfo() throws ServiceException{
    	List<NewsInfo> listNewsInfo = null;
    	List<News> listNews = null;
    	listNews = getAll();
    	listNewsInfo = getInfoNews(listNews);
    	return listNewsInfo;
    }
    
    /**
     * Implementation {@link NewsService#paginationNews(int, int)}
     * throws ServiceException if some problems on DAO layer
     */
    @Override
    public List<NewsInfo> paginationNews(int from, int to) throws ServiceException{
    	List<NewsInfo> listNewsInfo = null;
    	List<News> listNews = null;
    	if(from >= 0 && to >= 0){
    		try{
    			listNews = newsDAO.paginationNews(from, to);
    		}catch(DAOException e){
    			logger.error("Failed to get news by position from=" + from + ", to=" + to, e);
    			throw new ServiceException(e);
    		}
    		listNewsInfo = getInfoNews(listNews);
    	}
    	return listNewsInfo;
    }
    /**
     * Implementation {@link NewsService#searchNews(SearchParameter)
     */
    @Override
    public List<NewsInfo> searchNews(SearchParameter searchParameter) throws ServiceException{
    	List<NewsInfo> listNewsInfo = null;
    	List<News> listNews = null;
    	if(searchParameter != null){
    		if(searchParameter.getAuthor() != null && !searchParameter.getTagList().isEmpty()){
    			try{
    				listNews = newsDAO.getNewsByAuthorAndTag(searchParameter.getTagList().get(0).getIdTag(),
    					searchParameter.getAuthor().getIdAuthor());
    			}catch(DAOException e){
    				logger.error("Failed to get news by authorId=" + searchParameter.getAuthor().getIdAuthor()
    					+ " and tagId=" + searchParameter.getTagList().get(0).getIdTag(), e);
    				throw new ServiceException(e);
    			}
    		}else{
    			if(searchParameter.getAuthor() != null){
    				try{
    					listNews = newsDAO.getNewsByAuthor(searchParameter.getAuthor().getIdAuthor());
    				}catch(DAOException e){
    					logger.error("Failed to get news by authorId=" + searchParameter.getAuthor().getIdAuthor(), e);
    					throw new ServiceException(e);
    				}
    			}else{
    				try{
    					listNews = newsDAO.getNewsByTag(searchParameter.getTagList().get(0).getIdTag());
    				}catch(DAOException e){
    					logger.error("Failed to get news by tagId=" + searchParameter.getTagList().get(0).getIdTag(), e);
    					throw new ServiceException(e);
    				}
    			}
    		}
    		listNewsInfo = getInfoNews(listNews);
    	}
    	return listNewsInfo;
    }
    /**
     * get list {@link NewsInfo} 
     * @param listNews is list news for which need to find Info
     * @return list {@link NewsInfo}
     * @throws ServiceException if some problems on DAO layer
     */
    private List<NewsInfo> getInfoNews(List<News> listNews) throws ServiceException{
    	List<NewsInfo> listNewsInfo = null;
    	if(listNews != null){
    		Iterator<News> newsIterator = listNews.iterator();
    		listNewsInfo = new ArrayList<>();
    		while(newsIterator.hasNext()){
    			NewsInfo newsInfo = new NewsInfo();
    			News news = newsIterator.next();
    			newsInfo = getInfoForNews(news.getIdNews());
    			newsInfo.setNews(news);
    			listNewsInfo.add(newsInfo);
    		}
    	}
    	return listNewsInfo;
    }
        
    /**
     * get {@link NewsInfo} without {@link News} 
     * @param newsId is parameter by need to find NewsInfo
     * @return {@link NewsInfo}
     * @throws ServiceException if some problems on DAO layer
     */
    private NewsInfo getInfoForNews(Long newsId) throws ServiceException{
    	if(newsId != null){
    		NewsInfo newsInfo = new NewsInfo();
    		Author author = null;
    		try{
    			author = authorDAO.getAuthorForNews(newsId);    	
    		}catch(DAOException e){
    			logger.error("Failed to get author for news, where newsId=" + newsId, e);
    			throw new ServiceException(e);
    		}
    		newsInfo.setAuthor(author);
    		List<Tag> listTag = null;
    		try{
    			listTag = tagDAO.getAllTagsForNews(newsId);
    		}catch(DAOException e){
    			logger.error("Failed to get list tag for news, where newsId=" + newsId, e);
    			throw new ServiceException(e);
    		}
    		newsInfo.setTags(listTag);
    		List<Comment> listComment = null;
    		try{
    			listComment = commentDAO.getCommentList(newsId);
    		}catch(DAOException e){
    			logger.error("Failed to get list comments for news, where newsId=" + newsId, e);
    			throw new ServiceException(e);
    		}
    		newsInfo.setComments(listComment);
    	
    		return newsInfo;
    	}
    	throw new ServiceException();
    }
}
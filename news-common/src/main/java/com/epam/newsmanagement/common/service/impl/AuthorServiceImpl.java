package com.epam.newsmanagement.common.service.impl;

import com.epam.newsmanagement.common.dao.AuthorDAO;
import com.epam.newsmanagement.common.entity.Author;
import com.epam.newsmanagement.common.entity.News;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import com.epam.newsmanagement.common.exception.service.ServiceException;
import com.epam.newsmanagement.common.service.AuthorService;
import com.epam.newsmanagement.common.service.EntityService;
import com.epam.newsmanagement.common.service.NewsService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Implementing of {@link AuthorService}
 */
@Service
@Transactional(rollbackFor = ServiceException.class)
public class AuthorServiceImpl implements AuthorService {

    private final static Logger logger = LogManager.getLogger(AuthorServiceImpl.class);
    @Autowired
    private AuthorDAO authorDAO;
    @Autowired
    private NewsService newsService;

    /**
     * Implementation of {@link EntityService#create(Serializable)}
     * @param author is parameter which need to insert in database
     * @return parameter author_id from database
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    @Transactional
    public Long create(Author author) throws ServiceException{
        if(author != null){
        	try {
        		return authorDAO.create(author);
        	}catch (DAOException e){
        		logger.error("Failed to create author" + author, e);
        		throw new ServiceException(e);
        	}
    	}
        throw new ServiceException();
    }

    /**
     * Implementation of {@link EntityService#getById(Long)}
     * @param authorId is the field on which the search
     * @return {@link Author}
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    @Transactional(readOnly = true)
    public Author getById(Long authorId) throws ServiceException{
        if(authorId != null){
        	try {
        		return authorDAO.getById(authorId);
        	}catch (DAOException e){
        		logger.error("Failed to get author by id=" + authorId , e);
        		throw new ServiceException(e);
        	}
        }
        throw new ServiceException();
    }

    /**
     * Implementation of {@link EntityService#getAll()}
     * @return list all authors from database
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    @Transactional(readOnly = true)
    public List<Author> getAll() throws ServiceException{
        try{
            return authorDAO.getAll();
        }catch (DAOException e){
            logger.error("Failed to get all authors", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Implementation of {@link EntityService#countAll()}
     * @return the number authors in database
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    @Transactional(readOnly = true)
    public Long countAll() throws ServiceException{
        try{
            return authorDAO.countAll();
        }catch (DAOException e){
            logger.error("Failed to count authors", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Implementation {@link EntityService#update(Serializable)}
     * @param author is entity which need to update
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    @Transactional
    public void update(Author author) throws ServiceException{
        if(author != null){
        	try{
        		authorDAO.update(author);
        	}catch (DAOException e){
        		logger.error("Failed to update author" + author, e);
        		throw new ServiceException(e);
        	}
        }
    }

    /**
     * Implementation {@link EntityService#delete(Long)}
     * @param authorId is the field on which the search
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    @Transactional
    public void delete(Long authorId) throws ServiceException{
        if(authorId != null && authorId > 0){
        	List<News> listNews;
        	listNews = newsService.getNewsByAuthorId(authorId);
        	Iterator<News> newsIterator = listNews.iterator();
        	while(newsIterator.hasNext()){
        		newsService.deleteFullNews(newsIterator.next().getIdNews());        		
        	}
        	try {
        		authorDAO.delete(authorId);
        	}catch (DAOException e){
        		logger.error("Failed to delete author by id=" + authorId, e);
        		throw new ServiceException(e);
        	}
        }
    }


}
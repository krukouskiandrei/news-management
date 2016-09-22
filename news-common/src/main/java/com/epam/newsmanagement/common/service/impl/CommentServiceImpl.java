package com.epam.newsmanagement.common.service.impl;

import com.epam.newsmanagement.common.dao.CommentDAO;
import com.epam.newsmanagement.common.entity.Comment;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import com.epam.newsmanagement.common.exception.service.ServiceException;
import com.epam.newsmanagement.common.service.CommentService;
import com.epam.newsmanagement.common.service.EntityService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Implementing of {@link CommentService}
 */
@Service
@Transactional(rollbackFor = ServiceException.class)
public class CommentServiceImpl implements CommentService{

    private final static Logger logger = LogManager.getLogger(CommentServiceImpl.class);
    @Autowired
    private CommentDAO commentDAO;

    /**
     * Implementation of {@link EntityService#create(Serializable)}
     * @param comment is parameter which need to insert in database
     * @return parameter comment_id from database
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    @Transactional
    public void create(Comment comment) throws ServiceException{
        if(comment != null){
        	try {
        		commentDAO.create(comment);
        	}catch (DAOException e){
        		logger.error("Failed to create comment" + comment, e);
        		throw new ServiceException(e);
        	}
        }
    }

    /**
     * Implementation of {@link EntityService#getById(Long)}
     * @param commentId is the field on which the search
     * @return {@link Comment}
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    @Transactional(readOnly = true)
    public Comment getById(Long commentId) throws ServiceException{
        if(commentId != null){
        	try {
        		return commentDAO.getById(commentId);
        	}catch (DAOException e){
        		logger.error("Failed to get comment by id=" + commentId, e);
        		throw new ServiceException(e);
        	}
        }
        throw new ServiceException();
    }

    /**
     * Implementation of {@link EntityService#getAll()}
     * @return list all comments from database
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAll() throws ServiceException{
        try {
            return commentDAO.getAll();
        }catch (DAOException e){
            logger.error("Failed to get all comments", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Implementation of {@link EntityService#countAll()}
     * @return the number commentes in database
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    @Transactional(readOnly = true)
    public Long countAll() throws ServiceException{
        try {
            return commentDAO.countAll();
        }catch (DAOException e){
            logger.error("Failed to count comments", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Implementation {@link EntityService#update(Serializable)}
     * @param comment is entity which need to update
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    @Transactional
    public void update(Comment comment) throws ServiceException{
        if(comment != null){
        	try{
        		commentDAO.update(comment);
        	}catch (DAOException e){
        		logger.error("Failed to update comment" + comment, e);
        		throw new ServiceException(e);
        	}
        }
    }

    /**
     * Implementation {@link EntityService#delete(Long)}
     * @param commentId is the field on which the search
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    @Transactional
    public void delete(Long commentId) throws ServiceException{
        if(commentId != null){
        	try {
        		commentDAO.delete(commentId);
        	}catch (DAOException e){
        		logger.error("Failed to delete comment by id=" + commentId, e);
        		throw new ServiceException(e);
        	}
        }
    }

    /**
     * Implementation of {@link EntityService#getAll()}
     * @param newsId is the field on which the search
     * @return list comments from database for news
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentList(Long newsId) throws ServiceException{
        if(newsId != null){
        	try {
        		return commentDAO.getCommentList(newsId);
        	}catch (DAOException e){
        		logger.error("Failed to get comment list by news id where newsId=" + newsId, e);
        		throw new ServiceException(e);
        	}
        }
        throw new ServiceException();
    }

    /**
     * Implementation {@link EntityService#delete(Long)}
     * @param newsId is the field on which the search
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    @Transactional
    public void deleteCommentByNews(Long newsId) throws ServiceException{
        if(newsId != null){
        	try {
        		commentDAO.deleteCommentByNews(newsId);
        	}catch (DAOException e){
        		logger.error("Failed to delete comments by news where newsId=" + newsId, e);
        		throw new ServiceException(e);
        	}
        }
    }
}
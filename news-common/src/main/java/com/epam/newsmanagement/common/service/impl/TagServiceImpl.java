package com.epam.newsmanagement.common.service.impl;

import com.epam.newsmanagement.common.dao.TagDAO;
import com.epam.newsmanagement.common.entity.Tag;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import com.epam.newsmanagement.common.exception.service.ServiceException;
import com.epam.newsmanagement.common.service.EntityService;
import com.epam.newsmanagement.common.service.TagService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Implementing of {@link TagService}
 */
@Service
@Transactional(rollbackFor = ServiceException.class)
public class TagServiceImpl implements TagService{

    private final static Logger logger = LogManager.getLogger(TagServiceImpl.class);
    @Autowired
    private TagDAO tagDAO;

    /**
     * Implementation of {@link EntityService#create(Serializable)}
     * @param tag is parameter which need to insert in database
     * @return parameter tag_id from database
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    @Transactional
    public Long create(Tag tag) throws ServiceException{
        if(tag != null){
        	try {
        		return tagDAO.create(tag);
        	}catch (DAOException e){
        		logger.error("Failed to create tag" + tag, e);
        		throw new ServiceException(e);
        	}
        }
        throw new ServiceException();
    }

    /**
     * Implementation of {@link EntityService#getById(Long)}
     * @param tagId is the field on which the search
     * @return {@link Tag}
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    @Transactional(readOnly = true)
    public Tag getById(Long tagId) throws ServiceException{
        if(tagId != null){
        	try {
        		return tagDAO.getById(tagId);
        	}catch (DAOException e){
        		logger.error("Failed to get tag by tag id where tagId=" + tagId, e);
        		throw new ServiceException(e);
        	}
        }
        throw new ServiceException();
    }

    /**
     * Implementation of {@link EntityService#getAll()}
     * @return list all tags from database
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    @Transactional(readOnly = true)
    public List<Tag> getAll() throws ServiceException{
        try {
            return tagDAO.getAll();
        }catch (DAOException e){
            logger.error("Failed to get all tags", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Implementation of {@link EntityService#countAll()}
     * @return the number tags in database
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    @Transactional(readOnly = true)
    public Long countAll() throws ServiceException{
        try {
            return tagDAO.countAll();
        }catch (DAOException e){
            logger.error("Failed to count tags", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Implementation {@link EntityService#update(Serializable)}
     * @param tag is entity which need to update
     * @throws ServiceException if some problems on DAO layer
     */
    @Override
    @Transactional
    public void update(Tag tag) throws ServiceException{
        if(tag != null){
        	try {
        		tagDAO.update(tag);
        	}catch (DAOException e){
        		logger.error("Failed to update tag" + tag, e);
        		throw new ServiceException(e);
        	}
    	}
    }

    /**
     * Implementation {@link EntityService#delete(Long)}
     * @param tagId is the field on which the search
     * @throws ServiceException if some problems in database
     */
    @Override
    @Transactional
    public void delete(Long tagId) throws ServiceException{
        if(tagId != null){
        	try {
        		tagDAO.delete(tagId);
        	}catch (DAOException e){
        		logger.error("Failed to delete tag by tag id where tagId=" + tagId, e);
        		throw new ServiceException(e);
        	}
        }
    }
}
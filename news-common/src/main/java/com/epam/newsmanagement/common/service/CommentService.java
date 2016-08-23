package com.epam.newsmanagement.common.service;

import com.epam.newsmanagement.common.dao.CommentDAO;
import com.epam.newsmanagement.common.entity.Comment;
import com.epam.newsmanagement.common.exception.service.ServiceException;

import java.util.List;

/**
 * Service which provide API for operation with {@link CommentDAO}
 */
public interface CommentService extends EntityService<Comment, Long> {

    /**
     * Use {@link Comment} DAO layer for getting comments for news
     * @param newsId is news id
     * @return list comments for news
     * @throws ServiceException if some problems on DAO layer
     */
    List<Comment> getCommentList(Long newsId) throws ServiceException;

    /**
     * Use {@link Comment} DAO layer for deleting comments for news
     * @param newsId is news id
     * @throws ServiceException if some problems on DAO layer
     */
    void deleteCommentByNews(Long newsId) throws ServiceException;

}

package com.epam.newsmanagement.common.dao;

import com.epam.newsmanagement.common.entity.Comment;
import com.epam.newsmanagement.common.exception.dao.DAOException;

import java.util.List;

/**
 * methods for {@link Comment} for operating with database
 */
public interface CommentDAO extends EntityDAO<Comment, Long> {

    /**
     * get comments for news
     * @param newsId is news id
     * @return list comments for news
     * @throws DAOException id some problems in database
     */
    List<Comment> getCommentList(Long newsId) throws DAOException;

    /**
     * delete comments for news
     * @param newsId is news id
     * @throws DAOException if some problems in database
     */
    void deleteCommentByNews(Long newsId) throws DAOException;

}
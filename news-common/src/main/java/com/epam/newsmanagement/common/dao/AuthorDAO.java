package com.epam.newsmanagement.common.dao;

import com.epam.newsmanagement.common.entity.Author;
import com.epam.newsmanagement.common.exception.dao.DAOException;

import java.util.List;

/**
 * methods for {@link Author} for operating with database
 */
public interface AuthorDAO extends EntityDAO<Author, Long> {

    /**
     * find authors for news by news id
     * @param newsId is id of news
     * @return  all authors for news
     * @throws DAOException if some problems in database
     */
    List<Author> getAuthorList(Long newsId) throws DAOException;

    /**
     * find available authors
     * @return available authors
     * @throws DAOException if some problems in database
     */
    List<Author> getAvailableAuthorList() throws DAOException;

    /**
     * delete tabel between author and news
     * @param authorId is id author
     * @throws DAOException if some problems in database
     */
    void deleteTableBetweenAuthorNews(Long authorId) throws DAOException;

}

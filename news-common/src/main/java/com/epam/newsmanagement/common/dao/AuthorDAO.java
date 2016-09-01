package com.epam.newsmanagement.common.dao;

import com.epam.newsmanagement.common.entity.Author;
import com.epam.newsmanagement.common.exception.dao.DAOException;

/**
 * methods for {@link Author} for operating with database
 */
public interface AuthorDAO extends EntityDAO<Author, Long> {

	/**
	 * get Author for news
	 * @param newsId is id news for which need to find author
	 * @return author for news
	 * @throws DAOException if some problems in database
	 */
	Author getAuthorForNews(Long newsId) throws DAOException;

}
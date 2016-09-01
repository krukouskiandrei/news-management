package com.epam.newsmanagement.common.dao;

import java.util.List;

import com.epam.newsmanagement.common.entity.Tag;
import com.epam.newsmanagement.common.exception.dao.DAOException;

/**
 * methods for {@link Tag} for operating with database
 */
public interface TagDAO extends EntityDAO<Tag, Long> {

	/**
	 * get all tags for news by news id
	 * @param newsId 
	 * @return list {@link Tag} for news where news id anyway newsId
	 * @throws DAOException if some problems in database
	 */
	List<Tag> getAllTagsForNews(Long newsId) throws DAOException;

}
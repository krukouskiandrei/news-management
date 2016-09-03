package com.epam.newsmanagement.common.dao;

import java.util.List;

import com.epam.newsmanagement.common.entity.News;
import com.epam.newsmanagement.common.exception.dao.DAOException;


/**
 * methods for {@link News} for operating with database
 */
public interface NewsDAO extends EntityDAO<News, Long> {
	
	/**
	 * Get news by position in common list  news, sorted by creation date and count comments
	 * @param from min position in all list news	
	 * @param to max position in all list news
	 * @return list {@link News} order by population
	 */
	List<News> paginationNews(int from, int to) throws DAOException;

}
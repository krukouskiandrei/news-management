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
	
	/**
	 * Get news by creation author, uses news_author table from database
	 * @param authorId is parameter for which need to search 
	 * @return list {@link News} which have common author
	 * @throws DAOException if some problem in database
	 */
	List<News> getNewsByAuthor(Long authorId) throws DAOException;
	
	/**
	 * Get news by tag, uses news_tag table from database
	 * @param tagId is parameter for which need to search
	 * @return list {@link News} which have common tag
	 * @throws DAOException if some problem in database
	 */
	List<News> getNewsByTag(Long tagId) throws DAOException;
	
	/**
	 * Get news by tag and author, uses news_tag and news_author table from database
	 * @param tagId is parameter for which need to search 
	 * @param authorId id parameter for which need to search
	 * @return list {@link News} which have common author and tag
	 * @throws DAOException if some problem in database
	 */
	List<News> getNewsByAuthorAndTag(Long tagId, Long authorId) throws DAOException;
}
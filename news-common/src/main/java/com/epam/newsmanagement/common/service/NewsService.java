package com.epam.newsmanagement.common.service;

import java.util.List;

import com.epam.newsmanagement.common.dao.NewsDAO;
import com.epam.newsmanagement.common.entity.News;
import com.epam.newsmanagement.common.entity.NewsInfo;
import com.epam.newsmanagement.common.exception.service.ServiceException;

/**
 * Service which provide API for operation with {@link NewsDAO}
 */
public interface NewsService extends EntityService<News, Long> {

	/**
	 * get list all news with information about 
	 * Authors, Tags, Comments
	 * @return list {@link NewsInfo}, each object list contains all information 
	 * about news: authors, tags, comments
	 */
	List<NewsInfo> getAllNewsWithInfo() throws ServiceException;


}
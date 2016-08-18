package com.epam.newsmanagement.common.dao;

import com.epam.newsmanagement.common.entity.News;
import com.epam.newsmanagement.common.entity.SearchParameter;
import com.epam.newsmanagement.common.exception.dao.DAOException;

import java.util.List;

/**
 * methods for {@link News} for operating with database
 */
public interface NewsDAO extends EntityDAO<News, Long> {


}

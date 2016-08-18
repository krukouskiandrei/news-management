package com.epam.newsmanagement.common.dao;

import com.epam.newsmanagement.common.entity.Tag;
import com.epam.newsmanagement.common.exception.dao.DAOException;

import java.util.List;

/**
 * methods for {@link Tag} for operating with database
 */
public interface TagDAO extends EntityDAO<Tag, Long> {

    /**
     * find tags for the news
     * @param newsId is news id which tags need to search
     * @return news tags
     * @throws DAOException if some problems in database
     */
    List<Tag> getNewsTagList(Long newsId) throws DAOException;

}

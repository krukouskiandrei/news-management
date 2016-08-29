package com.epam.newsmanagement.common.service;

import com.epam.newsmanagement.common.dao.AuthorDAO;
import com.epam.newsmanagement.common.entity.Author;

/**
 * Service which provide API for operation with {@link AuthorDAO}
 */
public interface AuthorService extends EntityService<Author, Long> {

}
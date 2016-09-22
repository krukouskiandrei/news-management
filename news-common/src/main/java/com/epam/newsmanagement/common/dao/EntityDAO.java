package com.epam.newsmanagement.common.dao;

import com.epam.newsmanagement.common.exception.dao.DAOException;

import java.io.Serializable;
import java.util.List;

/**
 * Common operations for all entities {@link Entity}
 * @param <Entity> type of Entity
 * @param <ID> id of Entity
 */
public interface EntityDAO<Entity extends Serializable, ID> {

    /**
     * create {@link Entity} in database
     * @param object is entity which should be created in database
     * @return id of created entity
     * @throws DAOException if some problems in database
     */
    ID create(Entity object) throws DAOException;

    /**
     * find {@link Entity} by id
     * @param key id {@link Entity}
     * @return {@link Entity} from database
     * @throws DAOException if some problems in database
     */
    Entity getById(ID key) throws DAOException;

    /**
     * get all {@link Entity} from database
     * @return all {@link Entity} from database
     * @throws DAOException if some problems in database
     */
    List<Entity> getAll() throws DAOException;

    /**
     * count all {@link Entity} in database
     * @return quantity {@link Entity} in database
     * @throws DAOException if some problems in database
     */
    ID countAll() throws DAOException;

    /**
     * Update {@link Entity} in database
     * @param object it is {@link Entity} which need to update
     * @throws DAOException if some problems in database
     */
    void update(Entity object) throws DAOException;

    /**
     * Delete {@link Entity} from database
     * @param key it is id {@link Entity} in database
     * @throws DAOException if some problems in database
     */
    void delete(ID key) throws DAOException;
}
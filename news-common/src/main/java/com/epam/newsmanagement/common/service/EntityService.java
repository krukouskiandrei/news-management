package com.epam.newsmanagement.common.service;

import com.epam.newsmanagement.common.exception.service.ServiceException;

import java.io.Serializable;
import java.util.List;

/**
 * Service which provide API for DAO layer
 */
public interface EntityService<Entity extends Serializable, ID> {

    /**
     * Use {@link Entity} DAO layer for creating {@link Entity} object in database
     * @param object is entity which should be created in database
     * @return id of created entity
     * @throws ServiceException if some problems on DAO layer
     */
    void create(Entity object) throws ServiceException;

    /**
     * Use {@link Entity} DAO layer for finding {@link Entity} by id in database
     * @param key id {@link Entity}
     * @return {@link Entity} from database
     * @throws ServiceException if some problems on DAO layer
     */
    Entity getById(ID key) throws ServiceException;

    /**
     * Use {@link Entity} DAO layer for getting all {@link Entity} from database
     * @return all {@link Entity} from database
     * @throws ServiceException if some problems on DAO layer
     */
    List<Entity> getAll() throws ServiceException;

    /**
     * Use {@link Entity} DAO layer for counting all {@link Entity} in database
     * @return quantity {@link Entity} in database
     * @throws ServiceException if some problems on DAO layer
     */
    ID countAll() throws ServiceException;

    /**
     * Use {@link Entity} DAO layer for updating {@link Entity} in database
     * @param object it is {@link Entity} which need to update
     * @throws ServiceException if some problems on DAO layer
     */
    void update(Entity object) throws ServiceException;

    /**
     * Use {@link Entity} DAO layer for deleting {@link Entity} from database
     * @param key it is id {@link Entity} in database
     * @throws ServiceException if some problems on DAO layer
     */
    void delete(ID key) throws ServiceException;
}
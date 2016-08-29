package com.epam.newsmanagement.common.service.impl;

import com.epam.newsmanagement.common.dao.TagDAO;
import com.epam.newsmanagement.common.entity.Tag;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import com.epam.newsmanagement.common.exception.service.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *testing {@link TagServiceImpl}
 */
public class TagServiceImplTest {

    @Mock
    private TagDAO tagDAO;
    @InjectMocks
    private TagServiceImpl tagService;

    /**
     * initializing mock elements
     */
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    /**
     * testing method crete()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testCreate() throws ServiceException, DAOException {
        Tag tag = new Tag();
        Long tagIdTo = new Long(1);
        when(tagDAO.create(tag)).thenReturn(tagIdTo);
        Long tagIdFrom = tagService.create(tag);
        Assert.assertEquals(tagIdTo, tagIdFrom);
        verify(tagDAO).create(tag);
    }

    /**
     * testing method getById()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testGetById() throws ServiceException, DAOException{
        Tag tagTo = new Tag();
        Long tagId = new Long(1);
        when(tagDAO.getById(tagId)).thenReturn(tagTo);
        Tag tagFrom = tagService.getById(tagId);
        Assert.assertEquals(tagFrom, tagTo);
        verify(tagDAO).getById(tagId);
    }

    /**
     * testing method getAll()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testGetAll() throws ServiceException, DAOException{
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        tag1.setIdTag(new Long(1));
        tag2.setIdTag(new Long(2));
        List<Tag> tagListTo = new ArrayList<>();
        tagListTo.add(tag1);
        tagListTo.add(tag2);
        when(tagDAO.getAll()).thenReturn(tagListTo);
        List<Tag> tagListFrom = tagService.getAll();
        Assert.assertEquals(tagListTo, tagListFrom);
        verify(tagDAO).getAll();
    }

    /**
     * testing method countAll()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testCountAll() throws ServiceException, DAOException{
        Long countTo = new Long(2);
        when(tagDAO.countAll()).thenReturn(countTo);
        Long countFrom = tagService.countAll();
        Assert.assertEquals(countFrom, countTo);
        verify(tagDAO).countAll();
    }

    /**
     * testing method update()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testUpdate() throws ServiceException, DAOException{
        Tag tag = new Tag();
        tagService.update(tag);
        verify(tagDAO).update(tag);
    }

    /**
     * testing method delete()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testDelete() throws ServiceException, DAOException{
        Long tagId = new Long(1);
        tagService.delete(tagId);
        verify(tagDAO).delete(tagId);
    }

}
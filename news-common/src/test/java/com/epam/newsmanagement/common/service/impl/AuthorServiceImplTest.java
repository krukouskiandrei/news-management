package com.epam.newsmanagement.common.service.impl;

import com.epam.newsmanagement.common.dao.AuthorDAO;
import com.epam.newsmanagement.common.entity.Author;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import com.epam.newsmanagement.common.exception.service.ServiceException;
import com.epam.newsmanagement.common.service.NewsService;

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
 * testing {@link AuthorServiceImpl}
 */
public class AuthorServiceImplTest {

    @Mock
    private AuthorDAO authorDAO;
    @Mock
    private NewsService newsService;
    @InjectMocks
    private AuthorServiceImpl authorService;

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
    public void testCreate() throws ServiceException, DAOException{
        Author author = new Author();
        authorDAO.create(author);
        verify(authorDAO).create(author);
    }

    /**
     * testing method getById()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testGetById() throws ServiceException, DAOException{
        Author authorTo = new Author();
        Long authorId = new Long(1);
        when(authorDAO.getById(authorId)).thenReturn(authorTo);
        Author authorFrom = authorService.getById(authorId);
        Assert.assertEquals(authorFrom, authorTo);
        verify(authorDAO).getById(authorId);
    }

    /**
     * testing method getAll()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testGetAll() throws ServiceException, DAOException{
        Author author1 = new Author();
        Author author2 = new Author();
        author1.setIdAuthor(new Long(1));
        author2.setIdAuthor(new Long(2));
        List<Author> authorListTo = new ArrayList<>();
        authorListTo.add(author1);
        authorListTo.add(author2);
        when(authorDAO.getAll()).thenReturn(authorListTo);
        List<Author> authorListFrom = authorService.getAll();
        Assert.assertEquals(authorListTo, authorListFrom);
        verify(authorDAO).getAll();
    }

    /**
     * testing method countAll()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testCountAll() throws ServiceException, DAOException{
        Long countTo = new Long(2);
        when(authorDAO.countAll()).thenReturn(countTo);
        Long countFrom = authorService.countAll();
        Assert.assertEquals(countFrom, countTo);
        verify(authorDAO).countAll();
    }

    /**
     * testing method update()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testUpdate() throws ServiceException, DAOException{
        Author author = new Author();
        authorService.update(author);
        verify(authorDAO).update(author);
    }

    /**
     * testing method delete()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testDelete() throws ServiceException, DAOException{
        Long authorId = new Long(1);
        authorService.delete(authorId);
        verify(authorDAO).delete(authorId);
    }

}
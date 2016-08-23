package com.epam.newsmanagement.common.service.impl;

import com.epam.newsmanagement.common.dao.NewsDAO;
import com.epam.newsmanagement.common.entity.News;
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
 * testing {@link AuthorServiceImpl}
 */
public class NewsServiceImplTest {

    @Mock
    private NewsDAO newsDAO;
    @InjectMocks
    private NewsServiceImpl newsService;

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
        News news = new News();
        Long newsIdTo = new Long(1);
        when(newsDAO.create(news)).thenReturn(newsIdTo);
        Long newsIdFrom = newsService.create(news);
        Assert.assertEquals(newsIdTo, newsIdFrom);
        verify(newsDAO).create(news);
    }

    /**
     * testing method getById()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testGetById() throws ServiceException, DAOException{
        News newsTo = new News();
        String newsName = "News name";
        Long newsId = new Long(1);
        when(newsDAO.getById(newsId)).thenReturn(newsTo);
        News newsFrom = newsService.getById(newsId);
        Assert.assertEquals(newsFrom, newsTo);
        verify(newsDAO).getById(newsId);
    }

    /**
     * testing method getAll()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testGetAll() throws ServiceException, DAOException{
        News news1 = new News();
        News news2 = new News();
        news1.setIdNews(new Long(1));
        news2.setIdNews(new Long(2));
        List<News> newsListTo = new ArrayList<>();
        newsListTo.add(news1);
        newsListTo.add(news2);
        when(newsDAO.getAll()).thenReturn(newsListTo);
        List<News> newsListFrom = newsService.getAll();
        Assert.assertEquals(newsListTo, newsListFrom);
        verify(newsDAO).getAll();
    }

    /**
     * testing method countAll()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testCountAll() throws ServiceException, DAOException{
        Long countTo = new Long(2);
        when(newsDAO.countAll()).thenReturn(countTo);
        Long countFrom = newsService.countAll();
        Assert.assertEquals(countFrom, countTo);
        verify(newsDAO).countAll();
    }

    /**
     * testing method update()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testUpdate() throws ServiceException, DAOException{
        News news = new News();
        newsService.update(news);
        verify(newsDAO).update(news);
    }

    /**
     * testing method delete()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testDelete() throws ServiceException, DAOException{
        Long newsId = new Long(1);
        newsService.delete(newsId);
        verify(newsDAO).delete(newsId);
    }

}

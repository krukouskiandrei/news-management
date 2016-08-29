package com.epam.newsmanagement.common.service.impl;

import com.epam.newsmanagement.common.dao.CommentDAO;
import com.epam.newsmanagement.common.entity.Comment;
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
 * testing {@link CommentServiceImpl}
 */
public class CommentServiceImplTest {

    @Mock
    private CommentDAO commentDAO;
    @InjectMocks
    private CommentServiceImpl commentService;

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
        Comment comment = new Comment();
        Long commentIdTo = new Long(1);
        when(commentDAO.create(comment)).thenReturn(commentIdTo);
        Long commentIdFrom = commentService.create(comment);
        Assert.assertEquals(commentIdTo, commentIdFrom);
        verify(commentDAO).create(comment);
    }

    /**
     * testing method getById()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testGetById() throws ServiceException, DAOException{
        Comment commentTo = new Comment();
        Long commentId = new Long(1);
        when(commentDAO.getById(commentId)).thenReturn(commentTo);
        Comment commentFrom = commentService.getById(commentId);
        Assert.assertEquals(commentFrom, commentTo);
        verify(commentDAO).getById(commentId);
    }

    /**
     * testing method getAll()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testGetAll() throws ServiceException, DAOException{
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        comment1.setIdComment(new Long(1));
        comment2.setIdComment(new Long(2));
        List<Comment> commentListTo = new ArrayList<>();
        commentListTo.add(comment1);
        commentListTo.add(comment2);
        when(commentDAO.getAll()).thenReturn(commentListTo);
        List<Comment> authorListFrom = commentService.getAll();
        Assert.assertEquals(commentListTo, authorListFrom);
        verify(commentDAO).getAll();
    }

    /**
     * testing method countAll()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testCountAll() throws ServiceException, DAOException{
        Long countTo = new Long(2);
        when(commentDAO.countAll()).thenReturn(countTo);
        Long countFrom = commentService.countAll();
        Assert.assertEquals(countFrom, countTo);
        verify(commentDAO).countAll();
    }

    /**
     * testing method update()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testUpdate() throws ServiceException, DAOException{
        Comment comment = new Comment();
        commentService.update(comment);
        verify(commentDAO).update(comment);
    }

    /**
     * testing method delete()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testDelete() throws ServiceException, DAOException{
        Long commentId = new Long(1);
        commentService.delete(commentId);
        verify(commentDAO).delete(commentId);
    }

    /**
     * testing method getCommentList()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testGetCommentList() throws ServiceException, DAOException{
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        comment1.setIdComment(new Long(1));
        comment2.setIdComment(new Long(2));
        List<Comment> commentListTo = new ArrayList<>();
        commentListTo.add(comment1);
        commentListTo.add(comment2);
        Long newsId = new Long(1);
        when(commentDAO.getCommentList(newsId)).thenReturn(commentListTo);
        List<Comment> authorListFrom = commentService.getCommentList(newsId);
        Assert.assertEquals(commentListTo, authorListFrom);
        verify(commentDAO).getCommentList(newsId);
    }

    @Test
    public void testDeleteCommentByNews() throws ServiceException, DAOException{
        Long newsId = new Long(1);
        commentService.deleteCommentByNews(newsId);
        verify(commentDAO).deleteCommentByNews(newsId);
    }



}
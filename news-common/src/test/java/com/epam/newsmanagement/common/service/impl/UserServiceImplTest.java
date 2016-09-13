package com.epam.newsmanagement.common.service.impl;

import com.epam.newsmanagement.common.dao.UserDAO;
import com.epam.newsmanagement.common.entity.User;
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
 * testing {@link UserServiceImpl}
 */
public class UserServiceImplTest {

    @Mock
    private UserDAO userDAO;
    @InjectMocks
    private UserServiceImpl userService;

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
        User user = new User();
        userService.create(user);
        verify(userDAO).create(user);
    }

    /**
     * testing method getById()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testGetById() throws ServiceException, DAOException{
        User userTo = new User();
        Long userId = new Long(1);
        when(userDAO.getById(userId)).thenReturn(userTo);
        User userFrom = userService.getById(userId);
        Assert.assertEquals(userFrom, userTo);
        verify(userDAO).getById(userId);
    }

    /**
     * testing method getAll()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testGetAll() throws ServiceException, DAOException{
        User user1 = new User();
        User user2 = new User();
        user1.setIdUser(new Long(1));
        user2.setIdUser(new Long(2));
        List<User> userListTo = new ArrayList<>();
        userListTo.add(user1);
        userListTo.add(user2);
        when(userDAO.getAll()).thenReturn(userListTo);
        List<User> userListFrom = userService.getAll();
        Assert.assertEquals(userListTo, userListFrom);
        verify(userDAO).getAll();
    }

    /**
     * testing method countAll()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testCountAll() throws ServiceException, DAOException{
        Long countTo = new Long(2);
        when(userDAO.countAll()).thenReturn(countTo);
        Long countFrom = userService.countAll();
        Assert.assertEquals(countFrom, countTo);
        verify(userDAO).countAll();
    }

    /**
     * testing method update()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testUpdate() throws ServiceException, DAOException{
        User user = new User();
        userService.update(user);
        verify(userDAO).update(user);
    }

    /**
     * testing method delete()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testDelete() throws ServiceException, DAOException{
        Long userId = new Long(1);
        userService.delete(userId);
        verify(userDAO).delete(userId);
    }

    /**
     * testing method checkUser()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testCheckUser() throws ServiceException, DAOException{
        Long userId = new Long(1);
        Boolean resultTo = true;
        when(userDAO.checkUser(userId)).thenReturn(resultTo);
        Boolean resultFrom = userService.checkUser(userId);
        Assert.assertEquals(resultFrom, resultTo);
        verify(userDAO).checkUser(userId);
    }

    /**
     * testing method getUser()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testGetUser() throws ServiceException, DAOException{
        String login = "Login";
        String password = "Password";
        User userTo = new User();
        when(userDAO.getUser(login, password)).thenReturn(userTo);
        User userFrom = userService.getUser(login, password);
        Assert.assertEquals(userFrom, userTo);
        verify(userDAO).getUser(login, password);
    }

}
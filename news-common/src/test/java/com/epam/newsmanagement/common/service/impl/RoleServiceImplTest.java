package com.epam.newsmanagement.common.service.impl;

import com.epam.newsmanagement.common.dao.RoleDAO;
import com.epam.newsmanagement.common.entity.Role;
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
 * testing {@link RoleServiceImpl}
 */
public class RoleServiceImplTest {

    @Mock
    private RoleDAO roleDAO;
    @InjectMocks
    private RoleServiceImpl roleService;

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
        Role role = new Role();
        roleService.create(role);
        verify(roleDAO).create(role);
    }

    /**
     * testing method getById()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testGetById() throws ServiceException, DAOException{
        Role roleTo = new Role();
        Long userId = new Long(1);
        when(roleDAO.getById(userId)).thenReturn(roleTo);
        Role roleFrom = roleService.getById(userId);
        Assert.assertEquals(roleFrom, roleTo);
        verify(roleDAO).getById(userId);
    }

    /**
     * testing method getAll()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testGetAll() throws ServiceException, DAOException{
        Role role1 = new Role();
        Role role2 = new Role();
        role1.setUserId(new Long(1));
        role2.setUserId(new Long(2));
        List<Role> roleListTo = new ArrayList<>();
        roleListTo.add(role1);
        roleListTo.add(role2);
        when(roleDAO.getAll()).thenReturn(roleListTo);
        List<Role> roleListFrom = roleService.getAll();
        Assert.assertEquals(roleListTo, roleListFrom);
        verify(roleDAO).getAll();
    }

    /**
     * testing method countAll()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testCountAll() throws ServiceException, DAOException{
        Long countTo = new Long(2);
        when(roleDAO.countAll()).thenReturn(countTo);
        Long countFrom = roleService.countAll();
        Assert.assertEquals(countFrom, countTo);
        verify(roleDAO).countAll();
    }

    /**
     * testing method update()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testUpdate() throws ServiceException, DAOException{
        Role role = new Role();
        roleService.update(role);
        verify(roleDAO).update(role);
    }

    /**
     * testing method delete()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testDelete() throws ServiceException, DAOException{
        Long userId = new Long(1);
        roleService.delete(userId);
        verify(roleDAO).delete(userId);
    }

}
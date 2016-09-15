package com.epam.newsmanagement.common.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.epam.newsmanagement.common.dao.RoleDAO;
import com.epam.newsmanagement.common.dao.UserDAO;
import com.epam.newsmanagement.common.entity.Role;
import com.epam.newsmanagement.common.entity.User;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import com.epam.newsmanagement.common.utils.AppConfigTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

/**
 * testing entity {@link RoleDAOImpl}
 * @author Andrei_Krukouski
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigTest.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class, TransactionalTestExecutionListener.class})
@DatabaseSetup(value = "/schema/RoleDAOImplTest.xml", type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = "/schema/RoleDAOImplTest.xml", type = DatabaseOperation.DELETE_ALL)
public class RoleDAOImplTest {
	
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private UserDAO userDAO;
	
	/**
	 * testing method countAll()
	 * @throws DAOException
	 */
	@Test
	public void countAllTest() throws DAOException{
		Long expectedSize = 2L;
		Assert.assertEquals(expectedSize, roleDAO.countAll());
	}
	
	/**
	 * testing method create(Role role)
	 * @throws DAOException
	 */
	@Test
	public void createTest() throws DAOException{
		User expectedUser = new User();
		String userName = "Max";
		String userLogin = "max";
		String userPassword = "max01";
		expectedUser.setUserName(userName);
		expectedUser.setLogin(userLogin);
		expectedUser.setPassword(userPassword);
		Long expectedUserId = userDAO.create(expectedUser);
		Role expectedRole = new Role();
		String roleName = "user";
		expectedRole.setUserId(expectedUserId);
		expectedRole.setRoleName(roleName);
		Long actualUserId = roleDAO.create(expectedRole);
		Assert.assertNotNull(actualUserId);
		Assert.assertEquals(expectedUserId, actualUserId);
		Role actualRole = roleDAO.getById(actualUserId);
		Assert.assertEquals(expectedRole, actualRole);
	}
	
	/**
	 * testing method getById(Long userId)
	 * @throws DAOException
	 */
	@Test
	public void getByIdTest() throws DAOException{
		Role expectedRole = new Role();
		String roleName = "admin";
		Long userId = 1L;
		expectedRole.setRoleName(roleName);
		expectedRole.setUserId(userId);
		Role actualRole = roleDAO.getById(userId);
		Assert.assertEquals(expectedRole, actualRole);
	}
	
	/**
	 * testing method getAll()
	 * @throws DAOException
	 */
	@Test
	public void getAllTest() throws DAOException{
		Role expectedRole1 = new Role();
		String roleName1 = "admin";
		Long userId1 = 1L;
		expectedRole1.setRoleName(roleName1);
		expectedRole1.setUserId(userId1);
		Role expectedRole2 = new Role();
		String roleName2 = "client";
		Long userId2 = 2L;
		expectedRole2.setRoleName(roleName2);
		expectedRole2.setUserId(userId2);
		List<Role> expectedListRole = new ArrayList<>();
		expectedListRole.add(expectedRole1);
		expectedListRole.add(expectedRole2);
		List<Role> actualListRole = roleDAO.getAll();
		Assert.assertEquals(expectedListRole, actualListRole);
	}
	
	/**
	 * testing method update(Role role)
	 * @throws DAOException
	 */
	@Test
	public void updateTest() throws DAOException{
		Role expectedRole = new Role();
		String roleName = "user";
		Long userId = 1L;
		expectedRole.setRoleName(roleName);
		expectedRole.setUserId(userId);
		roleDAO.update(expectedRole);
		Role actualRole = roleDAO.getById(userId);
		Assert.assertEquals(expectedRole, actualRole);
	}
	
	/**
	 * testing method delete(Long userId)
	 * @throws DAOException
	 */
	@Test
	public void deleteTest() throws DAOException{
		Long expectedCountRole = roleDAO.countAll();
		Long userId = 1L;
		roleDAO.delete(userId);
		Long actualCountRole = roleDAO.countAll();
		actualCountRole += 1L;
		Assert.assertEquals(expectedCountRole, actualCountRole);
		
	}
}

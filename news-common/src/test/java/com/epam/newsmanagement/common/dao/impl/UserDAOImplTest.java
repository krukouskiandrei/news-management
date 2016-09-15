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

import com.epam.newsmanagement.common.dao.UserDAO;
import com.epam.newsmanagement.common.entity.User;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import com.epam.newsmanagement.common.utils.AppConfigTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
/**
 * testing entity {@link UserDAOImpl}
 * @author Andrei_Krukouski
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigTest.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class, TransactionalTestExecutionListener.class})
@DatabaseSetup(value = "/schema/UserDAOImplTest.xml", type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = "/schema/UserDAOImplTest.xml", type = DatabaseOperation.DELETE_ALL)
public class UserDAOImplTest {

	@Autowired
	private UserDAO userDAO;
	
	
	/**
	 * testing method countAll()
	 * @throws DAOException
	 */
	@Test
	public void countAllTest() throws DAOException{
		Long expectedSize = 2L;
		Assert.assertEquals(expectedSize, userDAO.countAll());
	}
	
	/**
	 * testing method create(User user)
	 * @throws DAOException
	 */
	@Test
	public void createTest() throws DAOException{
		User expUser = new User();
		String userName = "Max";
		String userLogin = "max@gmail.com";
		String userPassword = "max09";
		expUser.setUserName(userName);
		expUser.setLogin(userLogin);
		expUser.setPassword(userPassword);
		Long userId = userDAO.create(expUser);
		Assert.assertNotNull(userId);
		expUser.setIdUser(userId);
		User resUser = userDAO.getById(userId);
		Assert.assertEquals(expUser, resUser);
		
	}
	
	/**
	 * testing method getById(Long userId)
	 * @throws DAOException
	 */
	@Test
	public void getByIdTest() throws DAOException{
		User expUser = new User();
		Long userId = 1L;
		String userName = "Alex";
		String userLogin = "alex";
		String userPassword = "alex08";
		expUser.setIdUser(userId);
		expUser.setUserName(userName);
		expUser.setLogin(userLogin);
		expUser.setPassword(userPassword);
		User resUser = userDAO.getById(userId);
		Assert.assertEquals(expUser, resUser);
	}
	
	/**
	 * testing method getAll()
	 * @throws DAOException
	 */
	@Test
	public void getAllTest() throws DAOException{
		User expUser1 = new User();
		Long userId1 = 1L;
		String userName1 = "Alex";
		String userLogin1 = "alex";
		String userPassword1 = "alex08";
		expUser1.setIdUser(userId1);
		expUser1.setUserName(userName1);
		expUser1.setLogin(userLogin1);
		expUser1.setPassword(userPassword1);
		User expUser2 = new User();
		Long userId2 = 2L;
		String userName2 = "Mary";
		String userLogin2 = "mary";
		String userPassword2 = "mary69";
		expUser2.setIdUser(userId2);
		expUser2.setUserName(userName2);
		expUser2.setLogin(userLogin2);
		expUser2.setPassword(userPassword2);
		List<User> expUserList = new ArrayList<>();
		expUserList.add(expUser1);
		expUserList.add(expUser2);
		List<User> resUserList = userDAO.getAll();
		Assert.assertEquals(expUserList, resUserList);		
	}
	
	/**
	 * testing method update(User user)
	 * @throws DAOException
	 */
	@Test
	public void updateTest() throws DAOException{
		User expextedUser = new User();
		Long userId = 1L;
		String userName = "Max";
		String userLogin = "max@gamil.com";
		String userPassword = "max08";
		expextedUser.setIdUser(userId);
		expextedUser.setUserName(userName);
		expextedUser.setLogin(userLogin);
		expextedUser.setPassword(userPassword);
		userDAO.update(expextedUser);
		User actualUser = userDAO.getById(userId);
		Assert.assertEquals(expextedUser, actualUser);
	}
	
	/**
	 * testing method delete(Long userId)
	 * @throws DAOException
	 */
	@Test
	public void deleteTest() throws DAOException{
		Long expectedCountUsers = userDAO.countAll();
		Long userId = 1L;
		userDAO.delete(userId);
		Long actualCountUsers = userDAO.countAll();
		actualCountUsers += 1L;
		Assert.assertEquals(expectedCountUsers, actualCountUsers);
	}
	
	/**
	 * testing method checkUser(Long userId)
	 * @throws DAOException
	 */
	@Test
	public void checkUserTest() throws DAOException{
		Long userId = 1L;
		Assert.assertTrue(userDAO.checkUser(userId));
	}
	
	/**
	 * testing method getUser(String login, String password)
	 * @throws DAOException
	 */
	@Test
	public void getUserTest() throws DAOException{
		User expectedUser = new User();
		Long userId = 1L;
		String userName = "Alex";
		String userLogin = "alex";
		String userPassword = "alex08";
		expectedUser.setIdUser(userId);
		expectedUser.setUserName(userName);
		expectedUser.setLogin(userLogin);
		expectedUser.setPassword(userPassword);
		User actualUser = userDAO.getUser(userLogin, userPassword);
		Assert.assertEquals(expectedUser, actualUser);
	}
}

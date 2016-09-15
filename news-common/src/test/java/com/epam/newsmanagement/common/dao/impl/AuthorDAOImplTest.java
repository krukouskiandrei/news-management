package com.epam.newsmanagement.common.dao.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.epam.newsmanagement.common.dao.AuthorDAO;
import com.epam.newsmanagement.common.entity.Author;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import com.epam.newsmanagement.common.utils.AppConfigTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

/**
 * testing entity {@link AuthorDAOImpl}
 * @author Andrei_Krukouski
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigTest.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class, TransactionalTestExecutionListener.class})
@DatabaseSetup(value = "/schema/AuthorDAOImplTest.xml", type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = "/schema/AuthorDAOImplTest.xml", type = DatabaseOperation.DELETE_ALL)
public class AuthorDAOImplTest {

	@Autowired
	private AuthorDAO authorDAO;
	
	/**
	 * testing method create(Author author)
	 * @throws DAOException
	 */
	@Test
	public void create() throws DAOException{
		Author expectedAuthor = new Author();
		String authorName = "Max";
		Timestamp authorExpired = new Timestamp(12L);
		expectedAuthor.setAuthorName(authorName);
		expectedAuthor.setExpiredDate(authorExpired);
		Long expectedAuthorId = authorDAO.create(expectedAuthor);
		Assert.assertNotNull(expectedAuthorId);
		expectedAuthor.setIdAuthor(expectedAuthorId);
		Author actualAuthor = authorDAO.getById(expectedAuthorId);
		Assert.assertEquals(expectedAuthor, actualAuthor);
	}
	
	/**
	 * testing method getById(Long authorId)
	 * @throws DAOException
	 * @throws ParseException
	 */
	@Test
	public void getByIdTest() throws DAOException, ParseException{
		Author expectedAuthor = getAuthor();
		Long authorId = getAuthorId();
		Author actualAuthor = authorDAO.getById(authorId);
		Assert.assertEquals(expectedAuthor, actualAuthor);		
	}
	
	/**
	 * testing method getAll()
	 * @throws DAOException
	 * @throws ParseException
	 */
	@Test
	public void getAllTest() throws DAOException, ParseException{
		List<Author> expectedAuthorList = getListAuthors();
		List<Author> actualAuthorList = authorDAO.getAll();
		Assert.assertEquals(expectedAuthorList, actualAuthorList);
	}
	
	/**
	 * testing method countAll()
	 * @throws DAOException
	 */
	@Test
	public void countAllTest() throws DAOException{
		Long expectedCountAuthors = getCountAuthors();
		Assert.assertEquals(expectedCountAuthors, authorDAO.countAll());
	}
	
	/**
	 * testing method update(Author author)
	 * @throws DAOException
	 * @throws ParseException
	 */
	@Test
	public void updateTest() throws DAOException, ParseException{
		Author expectedAuthor = getUpdateAuthor();
		Long authorId = getAuthorId();
		authorDAO.update(expectedAuthor);
		Author actualAuthor = authorDAO.getById(authorId);
		Assert.assertEquals(expectedAuthor, actualAuthor);
	}
	
	/**
	 * testing method delete(Long authorId)
	 * @throws DAOException
	 */
	@Test
	public void deleteTest() throws DAOException{
		Long authorId = getAuthorId();
		Long expectedCountAuthors = authorDAO.countAll();
		authorDAO.delete(authorId);
		Long actualCountAuthors = authorDAO.countAll();
		actualCountAuthors += 1L;
		Assert.assertEquals(expectedCountAuthors, actualCountAuthors);
	}
	
	/**
	 * testing method getAuthorForNews(Long newsId)
	 * @throws DAOException
	 * @throws ParseException
	 */
	@Test
	public void getAuthorForNewsTest() throws DAOException, ParseException{
		Long newsId = getNewsId();
		Author expectedAuthor = getAuthor();
		Author actualAuthor = authorDAO.getAuthorForNews(newsId);
		Assert.assertEquals(expectedAuthor, actualAuthor);
	}
	
	
	/* setting data*/
	private Author getAuthor() throws ParseException{
		Author expectedAuthor = new Author();
		Long authorId = 1L;
		String authorName = "mary";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse("2017-03-14 08:11:00");
		Timestamp authorExpired = new Timestamp(date.getTime());
		expectedAuthor.setAuthorName(authorName);
		expectedAuthor.setExpiredDate(authorExpired);
		expectedAuthor.setIdAuthor(authorId);
		return expectedAuthor;
	}
	
	private List<Author> getListAuthors() throws ParseException{
		Author expectedAuthor1 = new Author();
		Long authorId1 = 1L;
		String authorName1 = "mary";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = sdf.parse("2017-03-14 08:11:00");
		Timestamp authorExpired1 = new Timestamp(date1.getTime());
		expectedAuthor1.setAuthorName(authorName1);
		expectedAuthor1.setExpiredDate(authorExpired1);
		expectedAuthor1.setIdAuthor(authorId1);
		Author expectedAuthor2 = new Author();
		Long authorId2 = 2L;
		String authorName2 = "fill";
		Date date2 = sdf.parse("2016-11-12 02:12:00");
		Timestamp authorExpired2 = new Timestamp(date2.getTime());
		expectedAuthor2.setAuthorName(authorName2);
		expectedAuthor2.setExpiredDate(authorExpired2);
		expectedAuthor2.setIdAuthor(authorId2);
		List<Author> expectedAuthorList = new ArrayList<>();
		expectedAuthorList.add(expectedAuthor1);
		expectedAuthorList.add(expectedAuthor2);
		return expectedAuthorList;
	}
	
	private Long getAuthorId(){
		return 1L;
	}
	
	private Author getUpdateAuthor() throws ParseException{
		Author expectedAuthor = new Author();
		Long authorId = getAuthorId();
		String authorName = "marcus";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse("2016-03-14 09:11:00");
		Timestamp authorExpired = new Timestamp(date.getTime());
		expectedAuthor.setAuthorName(authorName);
		expectedAuthor.setExpiredDate(authorExpired);
		expectedAuthor.setIdAuthor(authorId);
		return expectedAuthor;
	}
	
	private Long getCountAuthors(){
		return 2L;
	}
	
	private Long getNewsId(){
		return 1L;
	}
		
}

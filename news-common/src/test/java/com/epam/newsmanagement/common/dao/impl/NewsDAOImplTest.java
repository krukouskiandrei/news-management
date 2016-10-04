package com.epam.newsmanagement.common.dao.impl;

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

import com.epam.newsmanagement.common.dao.NewsDAO;
import com.epam.newsmanagement.common.entity.News;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import com.epam.newsmanagement.common.utils.AppConfigTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

/**
 * testing entity {@link NewsDAOImpl}
 * @author Andrei_Krukouski
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigTest.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class, TransactionalTestExecutionListener.class})
@DatabaseSetup(value = "/schema/NewsDAOImplTest.xml", type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = "/schema/NewsDAOImplTest.xml", type = DatabaseOperation.DELETE_ALL)
public class NewsDAOImplTest {

	@Autowired
	NewsDAO newsDAO;
	
	/**
	 * testing method countAll()
	 * @throws DAOException
	 */
	@Test
	public void countAllTest() throws DAOException{
		Long countNews = 4L;
		Assert.assertEquals(countNews, newsDAO.countAll());
	}
	
	/**
	 * testing method {@link NewsDAO#createNewsAuthorLink(Long, Long)}
	 * @throws DAOException
	 */
	@Test
	public void createNewsAuthorLinkTest() throws DAOException{
		Long newsId = 1L;
		Long authorId = 2L;
		newsDAO.createNewsAuthorLink(newsId, authorId);
		Long newAuthorId = 1L;
		newsDAO.createNewsAuthorLink(newsId, newAuthorId);
	}
	/**
	 * testing method {@link NewsDAO#createNewsTagLink(Long, Long)
	 * @throws DAOException
	 */
	@Test
	public void createNewsTagLinkTest() throws DAOException{
		Long newsId = 1L;
		Long tagId = 2L;
		newsDAO.createNewsTagLink(newsId, tagId);
		Long newTagId = 1L;
		newsDAO.createNewsTagLink(newsId, newTagId);
	}
	/**
	 * testing method {@link NewsDAO#deleteNewsTagLinks(Long)
	 * @throws DAOException
	 */
	@Test
	public void deleteNewsTagLinksTest() throws DAOException{
		Long newsId = 3L;
		Long tagId = 3L;
		List<News> news = newsDAO.getNewsByTag(tagId);
		Assert.assertEquals(news.get(0).getIdNews(), newsId);
		newsDAO.deleteNewsTagLinks(newsId);
		news = newsDAO.getNewsByTag(tagId);
		Assert.assertTrue(news.isEmpty());		
	}
	/**
	 * testing method {@link NewsDAO#deleteNewsAuthorLink(Long)
	 * @throws DAOException
	 */
	@Test
	public void deleteNewsAuthorLinkTest() throws DAOException{
		Long newsId = 3L;
		Long authorId = 3L;
		List<News> news = newsDAO.getNewsByAuthor(authorId);
		Assert.assertEquals(news.get(0).getIdNews(), newsId);
		newsDAO.deleteNewsAuthorLink(newsId);;
		news = newsDAO.getNewsByAuthor(authorId);
		Assert.assertTrue(news.isEmpty());		
	}
	
}

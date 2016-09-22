package com.epam.newsmanagement.common.dao.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.epam.newsmanagement.common.dao.TagDAO;
import com.epam.newsmanagement.common.entity.Tag;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import com.epam.newsmanagement.common.utils.AppConfigTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

/**
 * testing entity {@link TagDAOImpl}
 * @author Andrei_Krukouski
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigTest.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class, TransactionalTestExecutionListener.class})
@DatabaseSetup(value = "/schema/TagDAOImplTest.xml", type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = "/schema/TagDAOImplTest.xml", type = DatabaseOperation.DELETE_ALL)
public class TagDAOImplTest {
	
	@Autowired
	private TagDAO tagDAO;
	
	/**
	 * testing method getById(Long idTag)
	 * @throws DAOException
	 */
	@Test
	public void getByIdTest() throws DAOException{
		Tag expectedTag = getTag();
		Long tagId = getTagId();
		Tag actualTag = tagDAO.getById(tagId);
		Assert.assertEquals(expectedTag, actualTag);
	}
	
	private Long getTagId(){
		return 1L;
	}
	
	private Tag getTag(){
		Tag tag = new Tag();
		Long tagId = getTagId();
		String tagName = "TAG_NAME";
		tag.setIdTag(tagId);
		tag.setTagName(tagName);
		return tag;
	}

}

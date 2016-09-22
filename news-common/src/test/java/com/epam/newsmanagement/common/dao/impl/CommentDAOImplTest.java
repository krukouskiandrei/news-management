package com.epam.newsmanagement.common.dao.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.epam.newsmanagement.common.dao.CommentDAO;
import com.epam.newsmanagement.common.entity.Comment;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import com.epam.newsmanagement.common.utils.AppConfigTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

/**
 * testing entity {@link CommentDAOImpl}
 * @author Andrei_Krukouski
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigTest.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class, TransactionalTestExecutionListener.class})
@DatabaseSetup(value = "/schema/CommentDAOImplTest.xml", type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = "/schema/CommentDAOImplTest.xml", type = DatabaseOperation.DELETE_ALL)
public class CommentDAOImplTest {
	
	@Autowired
	private CommentDAO commentDAO;
	
	@Test
	public void getById() throws DAOException, ParseException{
		Long commentId = getCommentId();
		Comment expectedComment = getComment();
		Comment actualComment = commentDAO.getById(commentId);
		Assert.assertEquals(expectedComment, actualComment);
	}
	
	
	private Comment getComment() throws ParseException{
		Comment comment = new Comment();
		Long newsId = getNewsId();
		Long commentId = getCommentId();
		String commentText = "Comment text";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse("2011-11-11 11:11:00");
		Timestamp creationDate = new Timestamp(date.getTime());
		comment.setIdComment(commentId);
		comment.setIdNews(newsId);
		comment.setCommentText(commentText);
		comment.setCreationDate(creationDate);
		return comment;
	}
	
	
	private Long getCommentId(){
		return 1L;
	}
	
	private Long getNewsId(){
		return 1L;
	}
	
	

}

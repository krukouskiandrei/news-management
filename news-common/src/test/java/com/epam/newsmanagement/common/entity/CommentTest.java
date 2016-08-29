package com.epam.newsmanagement.common.entity;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;

/**
 * Testing entity {@link Comment}
 */
public class CommentTest {

    /**
     * testing methods for variable idComment
     */
    @Test
    public void testIdComment() {
        Comment comment = new Comment();
        Assert.assertTrue(comment.getIdComment() == null);
        comment.setIdComment(1L);
        Assert.assertTrue(comment.getIdComment() == 1L);
        comment.setIdComment(2L);
        Assert.assertTrue(comment.getIdComment() == 2L);
    }

    /**
     * testing methods for variable idNews
     */
    @Test
    public void testIdNews() {
        Comment comment = new Comment();
        Assert.assertTrue(comment.getIdNews() == null);
        comment.setIdNews(1L);
        Assert.assertTrue(comment.getIdNews() == 1L);
        comment.setIdNews(2L);
        Assert.assertTrue(comment.getIdNews() == 2L);
    }

    /**
     * testing methods for variable commentText
     */
    @Test
    public void testCommentText() {
        Comment comment = new Comment();
        Assert.assertTrue(comment.getCommentText() == null);
        comment.setCommentText("Max");
        Assert.assertEquals("Max", comment.getCommentText());
        comment.setCommentText("Alan");
        Assert.assertEquals("Alan", comment.getCommentText());
    }

    /**
     * testing methods for variable creationDate
      */
    @Test
    public void testCreationDate(){
        Comment comment = new Comment();
        Assert.assertTrue(comment.getCreationDate() == null);
        Timestamp timestamp1 = new Timestamp(1L);
        comment.setCreationDate(timestamp1);
        Assert.assertEquals(timestamp1, comment.getCreationDate());
        Timestamp timestamp2 = new Timestamp(2L);
        comment.setCreationDate(timestamp2);
        Assert.assertEquals(timestamp2, comment.getCreationDate());
    }

    /**
     * testing overriding method equals()
     */
    @Test
    public void testEquals(){
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        Assert.assertTrue(comment1.equals(comment2));
        comment1.setIdComment(1L);
        comment2.setIdComment(1L);
        comment1.setIdNews(2L);
        comment2.setIdNews(2L);
        comment1.setCommentText("Max");
        comment2.setCommentText("Max");
        Timestamp timestamp1 = new Timestamp(11L);
        Timestamp timestamp2 = new Timestamp(11L);
        comment1.setCreationDate(timestamp1);
        comment2.setCreationDate(timestamp2);
        Assert.assertTrue(comment1.equals(comment2));
        Comment comment3 = new Comment();
        Assert.assertFalse(comment1.equals(comment3));
    }

    /**
     * testing overriding methods hashCode()
     */
    @Test
    public void testHashCode(){
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        Assert.assertTrue(comment1.hashCode() == comment2.hashCode());
        comment1.setIdComment(1L);
        comment2.setIdComment(1L);
        comment1.setIdNews(2L);
        comment2.setIdNews(2L);
        comment1.setCommentText("Max");
        comment2.setCommentText("Max");
        Timestamp timestamp1 = new Timestamp(11L);
        Timestamp timestamp2 = new Timestamp(11L);
        comment1.setCreationDate(timestamp1);
        comment2.setCreationDate(timestamp2);
        Assert.assertTrue(comment1.hashCode() == comment2.hashCode());
    }
}
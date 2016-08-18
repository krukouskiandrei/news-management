package com.epam.newsmanagement.common.entity;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Testing entity {@link News}
 */
public class NewsTest {

    /**
     * testing methods for variable idNews
     */
    @Test
    public void testIdNews(){
        News news = new News();
        Assert.assertTrue(news.getIdNews() == null);
        news.setIdNews(1L);
        Assert.assertTrue(news.getIdNews() == 1L);
        news.setIdNews(2L);
        Assert.assertTrue(news.getIdNews() == 2L);
    }

    /**
     * testing methods for variable title
     */
    @Test
    public void testTitle(){
        News news = new News();
        Assert.assertTrue(news.getTitle() == null);
        news.setTitle("Title1");
        Assert.assertEquals("Title1", news.getTitle());
        news.setTitle("Title2");
        Assert.assertEquals("Title2", news.getTitle());
    }

    /**
     * testing methods for variable shortText
     */
    @Test
    public void testShortText(){
        News news = new News();
        Assert.assertTrue(news.getShortText() == null);
        news.setShortText("Short Text 1");
        Assert.assertEquals("Short Text 1", news.getShortText());
        news.setShortText("Short Text 2");
        Assert.assertEquals("Short Text 2", news.getShortText());
    }

    /**
     * testing methods for variable fullText
     */
    @Test
    public void testFullText(){
        News news = new News();
        Assert.assertTrue(news.getFullText() == null);
        news.setFullText("Full Text 1");
        Assert.assertEquals("Full Text 1", news.getFullText());
        news.setFullText("Full Text 2");
        Assert.assertEquals("Full Text 2", news.getFullText());
    }

    /**
     * testing methods for variable creationDate
     */
    @Test
    public void testCreationDate(){
        News news = new News();
        Assert.assertTrue(news.getCreationDate() == null);
        Timestamp timestamp1 = new Timestamp(1L);
        news.setCreationDate(timestamp1);
        Assert.assertEquals(timestamp1, news.getCreationDate());
        Timestamp timestamp2 = new Timestamp(2L);
        news.setCreationDate(timestamp2);
        Assert.assertEquals(timestamp2, news.getCreationDate());
    }

    /**
     * testing methods for variable modificationDate
     */
    @Test
    public void testModificationDate(){
        News news = new News();
        Assert.assertTrue(news.getModificationDate() == null);
        Date date1 = new Date(1L);
        news.setModificationDate(date1);
        Assert.assertEquals(date1, news.getModificationDate());
        Date date2 = new Date(2L);
        news.setModificationDate(date2);
        Assert.assertEquals(date2, news.getModificationDate());
    }

    /**
     * testing overriding method equals()
     */
    @Test
    public void testEquals(){
        News news1 = new News();
        News news2 = new News();
        Assert.assertTrue(news1.equals(news2));
        news1.setIdNews(1L);
        news2.setIdNews(1L);
        news1.setTitle("Title");
        news2.setTitle("Title");
        news1.setShortText("Short Text");
        news2.setShortText("Short Text");
        news1.setFullText("Full Text");
        news2.setFullText("Full Text");
        news1.setCreationDate(new Timestamp(1L));
        news2.setCreationDate(new Timestamp(1L));
        news1.setModificationDate(new Date(2L));
        news2.setModificationDate(new Date(2L));
        Assert.assertTrue(news1.equals(news2));
    }

    /**
     * testing overriding method hashCode()
     */
    @Test
    public void testHashCode(){
        News news1 = new News();
        News news2 = new News();
        Assert.assertTrue(news1.hashCode() == news2.hashCode());
        news1.setIdNews(1L);
        news2.setIdNews(1L);
        news1.setTitle("Title");
        news2.setTitle("Title");
        news1.setShortText("Short Text");
        news2.setShortText("Short Text");
        news1.setFullText("Full Text");
        news2.setFullText("Full Text");
        news1.setCreationDate(new Timestamp(1L));
        news2.setCreationDate(new Timestamp(1L));
        news1.setModificationDate(new Date(2L));
        news2.setModificationDate(new Date(2L));
        Assert.assertTrue(news1.hashCode() == news2.hashCode());
    }
}

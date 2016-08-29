package com.epam.newsmanagement.common.entity;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Testing entity {@link NewsInfo}
 */
public class NewsInfoTest {

    /**
     * testing methods for variable news
     */
    @Test
    public void testNews(){
        NewsInfo newsInfo = new NewsInfo();
        Assert.assertTrue(newsInfo.getNews() == null);
        News news1 = new News();
        news1.setIdNews(new Long(1));
        newsInfo.setNews(news1);
        Assert.assertEquals(news1, newsInfo.getNews());
    }

    /**
     * testing methods for variable author
     */
    @Test
    public void testAuthor(){
        NewsInfo newsInfo = new NewsInfo();
        Assert.assertTrue(newsInfo.getAuthor() == null);
        Author author = new Author();
        author.setIdAuthor(new Long(1));
        newsInfo.setAuthor(author);
        Assert.assertEquals(author, newsInfo.getAuthor());
    }

    /**
     * testing methods for variable tags
     */
    @Test
    public void testTags(){
        NewsInfo newsInfo = new NewsInfo();
        Assert.assertTrue(newsInfo.getTags() == null);
        Tag tag1 = new Tag();
        tag1.setIdTag(new Long(1));
        Tag tag2 = new Tag();
        tag2.setIdTag(new Long(2));
        List<Tag> tagList = new ArrayList<>();
        tagList.add(tag1);
        tagList.add(tag2);
        newsInfo.setTags(tagList);
        Assert.assertEquals(tagList, newsInfo.getTags());
    }

    /**
     * testing methods for variable comments
     */
    @Test
    public void testComments(){
        NewsInfo newsInfo = new NewsInfo();
        Assert.assertTrue(newsInfo.getComments() == null);
        Comment comment1 = new Comment();
        comment1.setIdComment(new Long(1));
        Comment comment2 = new Comment();
        comment2.setIdComment(new Long(2));
        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment1);
        commentList.add(comment2);
        newsInfo.setComments(commentList);
        Assert.assertEquals(commentList, newsInfo.getComments());
    }

    /**
     * testing overriding methods equals()
     */
    @Test
    public void testEquals(){
        NewsInfo newsInfo1 = new NewsInfo();
        NewsInfo newsInfo2 = new NewsInfo();
        Assert.assertEquals(newsInfo1, newsInfo2);
        Author author1 = new Author();
        Author author2 = new Author();
        author1.setIdAuthor(new Long(1));
        author2.setIdAuthor(new Long(1));
        newsInfo1.setAuthor(author1);
        newsInfo2.setAuthor(author2);
        Assert.assertEquals(newsInfo1, newsInfo2);
    }

    /**
     * testing overriding methods hashCode()
     */
    @Test
    public void testHashCode(){
        NewsInfo newsInfo1 = new NewsInfo();
        NewsInfo newsInfo2 = new NewsInfo();
        Assert.assertEquals(newsInfo1.hashCode(), newsInfo2.hashCode());
        Author author1 = new Author();
        Author author2 = new Author();
        author1.setIdAuthor(new Long(1));
        author2.setIdAuthor(new Long(1));
        newsInfo1.setAuthor(author1);
        newsInfo2.setAuthor(author2);
        Assert.assertEquals(newsInfo1.hashCode(), newsInfo2.hashCode());
    }

}

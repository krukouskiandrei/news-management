package com.epam.newsmanagement.common.entity;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Testing entity {@link SearchParameter}
 */
public class SearchParameterTest {

    /**
     * testing methods for variable author
     */
    @Test
    public void testAuthor(){
        SearchParameter searchParameter = new SearchParameter();
        Assert.assertEquals(searchParameter.getAuthor(), null);
        Author author = new Author();
        author.setIdAuthor(new Long(2));
        searchParameter.setAuthor(author);
        Assert.assertEquals(searchParameter.getAuthor(), author);
    }

    /**
     * testing methods for variable tagList
     */
    @Test
    public void testTagList(){
        SearchParameter searchParameter = new SearchParameter();
        Assert.assertEquals(searchParameter.getTagList(), null);
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        tag1.setIdTag(new Long(1));
        tag2.setIdTag(new Long(2));
        List<Tag> tagList = new ArrayList<Tag>();
        tagList.add(tag1);
        tagList.add(tag2);
        searchParameter.setTagList(tagList);
        Assert.assertEquals(tagList, searchParameter.getTagList());
    }

    /**
     * testing overriding method equals()
     */
    @Test
    public void testEquals(){
        SearchParameter searchParameter1 = new SearchParameter();
        SearchParameter searchParameter2 = new SearchParameter();
        Assert.assertEquals(searchParameter1, searchParameter2);
        Author author1 = new Author();
        author1.setIdAuthor(new Long(1));
        Author author2 = new Author();
        author2.setIdAuthor(new Long(1));
        searchParameter1.setAuthor(author1);
        searchParameter2.setAuthor(author2);
        Assert.assertEquals(searchParameter1, searchParameter2);
    }

    /**
     * testing overriding method hashCode()
     */
    @Test
    public void testHashCode(){
        SearchParameter searchParameter1 = new SearchParameter();
        SearchParameter searchParameter2 = new SearchParameter();
        Assert.assertEquals(searchParameter1.hashCode(), searchParameter2.hashCode());
        Author author1 = new Author();
        author1.setIdAuthor(new Long(1));
        Author author2 = new Author();
        author2.setIdAuthor(new Long(1));
        searchParameter1.setAuthor(author1);
        searchParameter2.setAuthor(author2);
        Assert.assertEquals(searchParameter1.hashCode(), searchParameter2.hashCode());
    }

}
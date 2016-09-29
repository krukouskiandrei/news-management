package com.epam.newsmanagement.common.entity;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testing entity {@link Tag}
 */
public class TagTest {

    /**
     * testing methods for variable idTag
     */
    @Test
    public void testIdTag(){
        Tag tag = new Tag();
        Assert.assertEquals(tag.getIdTag(), null);
        tag.setIdTag(new Long(1));
        Assert.assertEquals(tag.getIdTag(), new Long(1));
    }

    /**
     * testing methods for variable tagName
     */
    @Test
    public void testTagName(){
        Tag tag = new Tag();
        Assert.assertEquals(tag.getTagName(), null);
        String name = new String("Tag Name");
        tag.setTagName(name);
        Assert.assertEquals(tag.getTagName(), name);
    }

    /**
     * testing overriding method equals()
     */
    @Test
    public void testEquals(){
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        Assert.assertEquals(tag1, tag2);
        tag1.setIdTag(new Long(1));
        tag2.setIdTag(new Long(1));
        Assert.assertEquals(tag1, tag2);
        tag1.setTagName("Tag");
        tag2.setTagName("Tag");
        Assert.assertEquals(tag1, tag2);
        tag1.setIdTag(null);
        tag2.setIdTag(null);
        Assert.assertEquals(tag1, tag2);
    }

    /**
     * testing overriding method hashCode()
     */
    @Test
    public void testHashCode(){
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        Assert.assertEquals(tag1.hashCode(), tag2.hashCode());
        tag1.setIdTag(new Long(1));
        tag2.setIdTag(new Long(1));
        Assert.assertEquals(tag1.hashCode(), tag2.hashCode());
    }


}
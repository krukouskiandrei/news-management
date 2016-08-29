package com.epam.newsmanagement.common.entity;

import org.junit.Assert;
import org.junit.Test;
<<<<<<< HEAD
=======
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
>>>>>>> 10563c064db22e544a6b8280f9d38a6262005b53

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
<<<<<<< HEAD
=======
import static org.junit.Assert.assertTrue;
>>>>>>> 10563c064db22e544a6b8280f9d38a6262005b53

/**
 * Testing entity {@link Author}
 */
public class AuthorTest {
    /**
     * testing methods for variable idAuthor
     */
    @Test
    public void testIdAuthor(){
        Author author = new Author();
        Assert.assertTrue(author.getIdAuthor() == null);
        author.setIdAuthor(1L);
        Assert.assertTrue(author.getIdAuthor() == 1L);
        author.setIdAuthor(2L);
        Assert.assertTrue(author.getIdAuthor() == 2L);
    }

    /**
     * testing methods for variable authorName
     */
    @Test
    public void testAuthorName(){
        Author author = new Author();
        Assert.assertTrue(author.getAuthorName() == null);
        author.setAuthorName("Max");
        assertEquals("Max", author.getAuthorName());
        author.setAuthorName("Alan");
        assertEquals("Alan", author.getAuthorName());
    }

    /**
     * testing methods for variable expired
     */
    @Test
    public void testExpired(){
        Author author = new Author();
        Assert.assertTrue(author.getExpiredDate() == null);
        Timestamp timestamp1 = new Timestamp(12L);
        author.setExpiredDate(timestamp1);
        assertEquals(author.getExpiredDate(), timestamp1);
        Timestamp timestamp2 = new Timestamp(13L);
        author.setExpiredDate(timestamp2);
        assertEquals(author.getExpiredDate(), timestamp2);
    }

    /**
     * testing overriding equals() method
     */
    @Test
    public void testEquals(){
        Author author1 = new Author();
        Author author2 = new Author();
        Assert.assertTrue(author1.equals(author2));
        author1.setIdAuthor(1L);
        Assert.assertFalse(author1.equals(author2));
        author2.setIdAuthor(1L);
        author1.setAuthorName("Max");
        author2.setAuthorName("Max");
        Timestamp timestamp1 = new Timestamp(1L);
        Timestamp timestamp2 = new Timestamp(1L);
        author1.setExpiredDate(timestamp1);
        author2.setExpiredDate(timestamp2);
        Assert.assertTrue(author1.equals(author2));
        Author author3 = new Author();
        Assert.assertFalse(author1.equals(author3));
    }

    /**
     * testing overriding hashCode() method
     */
    @Test
    public void testHashCode(){
        Author author1 = new Author();
        Author author2 = new Author();
        author1.setIdAuthor(1L);
        author2.setIdAuthor(1L);
        author1.setAuthorName("Max");
        author2.setAuthorName("Max");
        Timestamp timestamp1 = new Timestamp(1L);
        Timestamp timestamp2 = new Timestamp(1L);
        author1.setExpiredDate(timestamp1);
        author2.setExpiredDate(timestamp2);
        Assert.assertTrue(author1.hashCode() == author2.hashCode());
    }


}

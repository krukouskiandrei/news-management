package com.epam.newsmanagement.common.entity;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testing entity {@link User}
 */
public class UserTest {

    /**
     * testing method for variable idUser
     */
    @Test
    public void testIdUser() {
        User user = new User();
        Assert.assertEquals(user.getIdUser(), null);
        Long id = new Long(1);
        user.setIdUser(id);
        Assert.assertEquals(user.getIdUser(), id);
    }

    /**
     * testing method for variable userName
     */
    @Test
    public void testUserName(){
        User user = new User();
        Assert.assertEquals(user.getUserName(), null);
        String name = new String("Name");
        user.setUserName(name);
        Assert.assertEquals(user.getUserName(), name);
    }

    /**
     * testing method for variable login
     */
    @Test
    public void testLogin(){
        User user = new User();
        Assert.assertEquals(user.getLogin(), null);
        String login = new String("Login");
        user.setLogin(login);
        Assert.assertEquals(login, user.getLogin());
    }

    /**
     * testing method for variable password
     */
    @Test
    public void testPassword(){
        User user = new User();
        Assert.assertEquals(user.getPassword(), null);
        String password = new String("Password");
        user.setPassword(password);
        Assert.assertEquals(password, user.getPassword());
    }

    /**
     * testing method for variable role
     */
    @Test
    public void testRole(){
        User user = new User();
        Assert.assertEquals(user.getRole(), null);
        Role role = new Role();
        role.setUserId(new Long(2));
        user.setRole(role);
        Assert.assertEquals(role, user.getRole());
    }

    /**
     * testing overriding method equals()
     */
    @Test
    public void testEquals(){
        User user1 = new User();
        User user2 = new User();
        Assert.assertEquals(user1, user2);
        user1.setIdUser(new Long(1));
        user2.setIdUser(new Long(1));
        Assert.assertEquals(user1, user2);
    }

    /**
     * testing overriding method hashCode()
     */
    @Test
    public void testHashCode(){
        User user1 = new User();
        User user2 = new User();
        Assert.assertEquals(user1.hashCode(), user2.hashCode());
        user1.setIdUser(new Long(1));
        user2.setIdUser(new Long(1));
        Assert.assertEquals(user1.hashCode(), user2.hashCode());
    }
}
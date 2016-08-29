package com.epam.newsmanagement.common.entity;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testing entity {@link Role}
 */
public class RoleTest {

    /**
     * testing methods for variable userId
     */
    @Test
    public void testUserId(){
        Role role = new Role();
        Assert.assertEquals(role.getUserId(), null);
        role.setUserId(new Long(1));
        Assert.assertEquals(role.getUserId(), new Long(1));
    }

    /**
     * testing methods for variable roleName
     */
    @Test
    public void testRoleName(){
        Role role = new Role();
        Assert.assertEquals(role.getRoleName(), null);
        String name = "ROLE";
        role.setRoleName(name);
        Assert.assertEquals(role.getRoleName(), name);
    }

    /**
     * testing overriding method equals()
     */
    @Test
    public void testEquals(){
        Role role1 = new Role();
        Role role2 = new Role();
        Assert.assertEquals(role1, role2);
        role1.setUserId(new Long(1));
        role2.setUserId(new Long(1));
        Assert.assertEquals(role1, role2);
    }

    /**
     * testing overriding method hashCode()
     */
    @Test
    public void testHashCode(){
        Role role1 = new Role();
        Role role2 = new Role();
        Assert.assertEquals(role1.hashCode(), role2.hashCode());
        role1.setUserId(new Long(1));
        role2.setUserId(new Long(1));
        Assert.assertEquals(role1.hashCode(), role2.hashCode());
    }

}

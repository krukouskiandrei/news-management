package com.epam.newsmanagement.common.dao.impl;

<<<<<<< HEAD
/*import com.epam.newsmanagement.common.dao.RoleDAO;
=======
import com.epam.newsmanagement.common.dao.RoleDAO;
>>>>>>> 10563c064db22e544a6b8280f9d38a6262005b53
import com.epam.newsmanagement.common.entity.Role;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
<<<<<<< HEAD
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;*/
=======
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
>>>>>>> 10563c064db22e544a6b8280f9d38a6262005b53

/**
 *
 */
/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:resources/springContextTest.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class, TransactionalTestExecutionListener.class})
@DatabaseSetup(value = "/resources/RoleDaoImplTest.xml", type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = "/resources/RoleDaoImplTest.xml", type = DatabaseOperation.DELETE_ALL)*/
public class RoleDAOImplTest {

    /*@Autowired
    private RoleDAO roleDAO;*/

    /*@Test
    public void createTest() throws DAOException{
        Role role = new Role();
        String roleName= "GUEST";
        role.setRoleName(roleName);
        role.setUserId(new Long(15));
        roleDAO.create(role);
    }*/


}

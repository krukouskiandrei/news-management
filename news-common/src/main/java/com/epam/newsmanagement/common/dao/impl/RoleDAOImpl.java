package com.epam.newsmanagement.common.dao.impl;

import com.epam.newsmanagement.common.dao.RoleDAO;
import com.epam.newsmanagement.common.entity.Role;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Implementing of {@link RoleDAO}
 */
@Repository
public class RoleDAOImpl /*implements RoleDAO*/{

    private final static String INSERT_ROLE_SQL = "";

    @Autowired
    private DataSource dataSource;

   /* @Override
    public Long create(Role role) throws DAOException{
        Long roleId = null;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement()
        )


    }*/


}

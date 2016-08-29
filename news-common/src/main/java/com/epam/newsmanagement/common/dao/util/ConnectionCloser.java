package com.epam.newsmanagement.common.dao.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 */
public class ConnectionCloser {
    private final static Logger logger = LogManager.getLogger(ConnectionCloser.class);

    private final static String CLOSE_STATEMENT_ERROR = "DIDN'T CLOSE STATEMENT";
    private final static String CLOSE_RESULTSET_ERROR = "DIDN'T CLOSE RESULT SET";

    private ConnectionCloser(){}

    public static void closeConnection(Connection connection, DataSource dataSource, Statement statement){
        try{
            DataSourceUtils.releaseConnection(connection, dataSource);
            statement.close();
        }catch (SQLException e){
            logger.warn(CLOSE_STATEMENT_ERROR);
        }
    }
    public static void closeConnection(Connection connection, DataSource dataSource, Statement statement, ResultSet resultSet){
        closeConnection(connection, dataSource, statement);
        try {
            if (resultSet != null){
                resultSet.close();
            }
        }catch (SQLException e){
            logger.warn(CLOSE_RESULTSET_ERROR);
        }
    }

}
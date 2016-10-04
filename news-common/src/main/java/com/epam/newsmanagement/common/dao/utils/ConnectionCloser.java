package com.epam.newsmanagement.common.dao.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class ConnectionCloser {
	private final static Logger logger = LogManager.getLogger(ConnectionCloser.class);
	
	
	private ConnectionCloser(){
		
	}
	
	public static void closeConnection(Connection connection, Statement statement, DataSource dataSource){
		try{
			statement.close();
			DataSourceUtils.releaseConnection(connection, dataSource);	
		}catch(SQLException e){
			logger.warn("Didn't close statement");
		}
	}
	
	public static void closeConnection(Connection connection, Statement statement, DataSource dataSource, ResultSet resultSet){
		try{
			if(resultSet != null){
				resultSet.close();
			}
		}catch(SQLException e){
			logger.warn("Didn't close result set");
		}
		closeConnection(connection, statement, dataSource);
	}
}

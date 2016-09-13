package com.epam.newsmanagement.common.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.newsmanagement.common.entity.User;
/**
 * uses jdbcTemplate for insert data from resultSet in {@link User} object
 * @author Andrei_Krukouski
 *
 */
public class UserMapper implements RowMapper<User> {
	
	public User mapRow(ResultSet rs, int rowNum) throws SQLException{
		User user = new User();
		user.setIdUser(rs.getLong(1));
		user.setUserName(rs.getString(2));
		user.setLogin(rs.getString(3));
		user.setPassword(rs.getString(4));
		return user;
	}

}

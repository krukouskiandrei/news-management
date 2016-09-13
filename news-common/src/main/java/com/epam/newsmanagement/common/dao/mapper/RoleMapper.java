package com.epam.newsmanagement.common.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.newsmanagement.common.entity.Role;

/**
 * uses jdbcTemplate for insert data from resultSet in {@link Role} object
 * @author Andrei_Krukouski
 *
 */
public class RoleMapper implements RowMapper<Role> {
	
	public Role mapRow(ResultSet rs, int rowNum) throws SQLException{
		Role role = new Role();
		role.setUserId(rs.getLong(1));
		role.setRoleName(rs.getString(2));
		return role;
	}

}

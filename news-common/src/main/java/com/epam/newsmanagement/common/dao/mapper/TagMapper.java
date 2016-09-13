package com.epam.newsmanagement.common.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.newsmanagement.common.entity.Tag;
/**
 * uses jdbcTemplate for insert data from resultSet in {@link Tag} object
 * @author Andrei_Krukouski
 *
 */
public class TagMapper implements RowMapper<Tag>{
	
	public Tag mapRow(ResultSet rs, int rowNum) throws SQLException{
		Tag tag = new Tag();
		tag.setIdTag(rs.getLong(1));
		tag.setTagName(rs.getString(2));
		return tag;
	}

}

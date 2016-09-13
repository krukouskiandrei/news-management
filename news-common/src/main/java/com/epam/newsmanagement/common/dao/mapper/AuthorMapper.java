package com.epam.newsmanagement.common.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.newsmanagement.common.entity.Author;

/**
 * uses jdbcTemplate for insert data from resultSet in {@link Author} object
 * @author Andrei_Krukouski
 *
 */
public class AuthorMapper implements RowMapper<Author> {
	
	public Author mapRow(ResultSet rs, int rowNum) throws SQLException{
		Author author = new Author();
		author.setIdAuthor(rs.getLong(1));
		author.setAuthorName(rs.getString(2));
		author.setExpiredDate(rs.getTimestamp(3));
		return author;
	}

}

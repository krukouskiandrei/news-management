package com.epam.newsmanagement.common.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.newsmanagement.common.entity.News;

/**
 * uses jdbcTemplate for insert data from resultSet in {@link News} object
 * @author Andrei_Krukouski
 *
 */
public class NewsMapper implements RowMapper<News> {
	
	public News mapRow(ResultSet rs, int rowNum) throws SQLException{
		News news = new News();
		news.setIdNews(rs.getLong(1));
		news.setTitle(rs.getString(2));
		news.setShortText(rs.getString(3));
		news.setFullText(rs.getString(4));
		news.setCreationDate(rs.getTimestamp(5));
		news.setModificationDate(rs.getDate(6));
		return news;
	}

}

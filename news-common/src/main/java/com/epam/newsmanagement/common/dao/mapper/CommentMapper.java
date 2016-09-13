package com.epam.newsmanagement.common.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.newsmanagement.common.entity.Comment;
/**
 *  uses jdbcTemplate for insert data from resultSet in {@link Comment} object
 * @author Andrei_Krukouski
 *
 */
public class CommentMapper implements RowMapper<Comment> {
	
	public Comment mapRow(ResultSet rs, int rowNum) throws SQLException{
		Comment comment = new Comment();
		comment.setIdComment(rs.getLong(1));
		comment.setIdNews(rs.getLong(2));
		comment.setCommentText(rs.getString(3));
		comment.setCreationDate(rs.getTimestamp(4));
		return comment;
	}

}

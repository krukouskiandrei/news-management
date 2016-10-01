package com.epam.newsmanagement.client.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.newsmanagement.client.exception.CommandException;
import com.epam.newsmanagement.common.service.AuthorService;
import com.epam.newsmanagement.common.service.NewsService;
import com.epam.newsmanagement.common.service.TagService;

public class FilterNewsCommand implements NewsCommand{
	
	private static final String PAGE = "WEB-INF/views/newslist.jsp";
	
	@Autowired
	private NewsService newsService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private TagService tagService;
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException{
		
		return PAGE;
	}

}

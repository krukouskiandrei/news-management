package com.epam.newsmanagement.client.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.newsmanagement.client.exception.CommandException;
import com.epam.newsmanagement.common.service.NewsService;

public class ShowMessageCommand implements NewsCommand{
	
	private static final String PAGE = "WEB-INF/views/viewnews.jsp";
	
	@Autowired
	private NewsService newsService;
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException{
		
		return PAGE;
	}

}

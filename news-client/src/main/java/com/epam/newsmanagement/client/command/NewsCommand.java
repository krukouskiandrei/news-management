package com.epam.newsmanagement.client.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.newsmanagement.client.exception.CommandException;

public interface NewsCommand {
	
	String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}

package com.epam.newsmanagement.client.controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epam.newsmanagement.client.command.NewsCommand;
import com.epam.newsmanagement.client.command.NewsCommandFactory;

@WebServlet("/NewsServlet")
public class NewsServlet extends HttpServlet{
	
	private static final long serialVersionUID = 2342342342323423234L;
	
	private ClassPathXmlApplicationContext context;
	
	private static final String ERROR = "WEB-INF/views/error.jsp";
	
	private static Logger logger = Logger.getLogger(NewsServlet.class);
	
	public NewsServlet(){
		super();
	}
	
	public void init(ServletConfig config) throws ServletException{
		context = new ClassPathXmlApplicationContext("spring-config\\beans.xml");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		processExecute(request, response);
	}
	public void  doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		processExecute(request, response);
	}
	
	private void processExecute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		NewsCommandFactory factory = (NewsCommandFactory) context.getBean("newCommandFactory");
		NewsCommand command = null;
		String page = null;
		try{
			command = factory.getCommand(request, response);
			page = command.execute(request, response);
		}catch(Exception e){
			logger.error(e.getMessage());
			page = ERROR;
		}
		request.getRequestDispatcher(page).forward(request, response);
	}
	
	public void destroy(){
		context.close();
	}

}

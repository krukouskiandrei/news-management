package com.epam.newsmanagement.client.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

@WebListener
public class NewsContextListener extends Object implements ServletContextListener {
       
	static Logger logger = Logger.getLogger(NewsContextListener.class);
	
    public NewsContextListener() {
        super();
    }

	public void contextDestroyed(ServletContextEvent sce)  { 
    	logger.info("Stop");
    }

	public void contextInitialized(ServletContextEvent sce)  {
    	logger.info("Start");
    }
	
}

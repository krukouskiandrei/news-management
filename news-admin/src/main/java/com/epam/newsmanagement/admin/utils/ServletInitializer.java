package com.epam.newsmanagement.admin.utils;

import javax.servlet.Filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 
 * @author Andrei_Krukouski
 *
 */

@Configuration
public class ServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected String[] getServletMappings(){
		return new String[]{ "/" };
	}
	
	@Override
	protected Class<?>[] getRootConfigClasses(){
		return new Class<?>[]{SpringRootConfig.class};
	}
	
	@Override
	protected Class<?>[] getServletConfigClasses(){
		return new Class<?>[]{SpringWebConfig.class};
	}
	
	@Override
	protected Filter[] getServletFilters(){
		return new Filter[]{new CharacterEncodingFilter("UTF-8", true)};
	}
	
}
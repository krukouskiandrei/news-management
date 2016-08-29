package com.epam.newsmanagement.admin.utils;

<<<<<<< HEAD
import javax.servlet.Filter;

=======
>>>>>>> 10563c064db22e544a6b8280f9d38a6262005b53
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

<<<<<<< HEAD
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
	
=======
import javax.servlet.Filter;

/**
 *
 */
@Configuration
public class ServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected String[] getServletMappings(){
        return new String[]{"/"};
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
>>>>>>> 10563c064db22e544a6b8280f9d38a6262005b53
}

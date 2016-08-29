package com.epam.newsmanagement.admin.utils;

import java.util.Arrays;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import com.epam.newsmanagement.admin.utils.security.SecurityConfig;

/**
 * 
 * @author Andrei_Krukouski
 *
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.epam.newsmanagement.admin", "com.epam.newsmanagement.common"})
@Import({SecurityConfig.class})
public class SpringWebConfig extends WebMvcConfigurerAdapter {
	
	@Bean
	public TilesConfigurer tilesConfigurer(){
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions(new String[]{"/WEB-INF/layout/tiles.xml"});
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
	}

	@Bean
    public ViewResolver viewResolver(){
        return new TilesViewResolver();
    }
	
	@Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter =
                new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));
        return converter;
    }
	
	@Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("resource/info/message",
                "resource/info/error_message");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
	
	@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
        configurer.enable();
    }
	
	@Override
    public void addInterceptors(InterceptorRegistry registry){
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        registry.addInterceptor(interceptor);
    }
	
	@Bean(name = "localResolver")
    public LocaleResolver sessionLocalResolver(){
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
    }
	
	@Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(){
        LocalValidatorFactoryBean lvfb = new LocalValidatorFactoryBean();
        lvfb.setValidationMessageSource(messageSource());
        return lvfb;
    }

   
	
}
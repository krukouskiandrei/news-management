package com.epam.newsmanagement.client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.newsmanagement.client.command.CreateCommentCommand;
import com.epam.newsmanagement.client.command.FilterNewsCommand;
import com.epam.newsmanagement.client.command.NewsCommand;
import com.epam.newsmanagement.client.command.NewsCommandFactory;
import com.epam.newsmanagement.client.command.ShowMessageCommand;
import com.epam.newsmanagement.client.command.ShowNewsCommand;

@Configuration
public class CommandConfig {
	
	public CommandConfig() {
	
	}

	@Bean
	public NewsCommand showNewsCommand(){
		return new ShowNewsCommand();
	}
	
	@Bean
	public NewsCommand filterNewsCommand(){
		return new FilterNewsCommand();
	}
	
	@Bean
	public NewsCommand showMessageCommand(){
		return new ShowMessageCommand();
	}
	
	@Bean
	public NewsCommand createCommentCommand(){
		return new CreateCommentCommand();
	}
	
	@Bean
	public NewsCommandFactory newCommandFactory(){
		return new NewsCommandFactory();
	}

}

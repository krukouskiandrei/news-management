package com.epam.newsmanagement.client.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.newsmanagement.client.exception.CommandException;

public class NewsCommandFactory {

	private static final String COMMAND_PARAMETR = "command";
	
	private enum CommandNames{SHOW_NEWS, SHOW_MESSAGE, CREATE_COMMENT, FILTER_NEWS};
	
	@Autowired
	private ShowNewsCommand showNewsCommand;
	@Autowired 
	private FilterNewsCommand filterNewsCommand;
	@Autowired
	private ShowMessageCommand showMessageCommand;
	@Autowired
	private CreateCommentCommand createCommentCommand;
	
	public NewsCommandFactory(){}
	
	public NewsCommand getCommand(HttpServletRequest request, HttpServletResponse response) throws CommandException{
		NewsCommand command = null;
		if(request.getParameter(COMMAND_PARAMETR) == null){
			throw new CommandException("error: not write command");
		}
		CommandNames name = CommandNames.valueOf(request.getParameter(COMMAND_PARAMETR).toUpperCase());
		switch(name){
		case SHOW_NEWS: 
			command = showNewsCommand;
			break;
		case SHOW_MESSAGE:
			command = showMessageCommand;
			break;
		case CREATE_COMMENT:
			command = createCommentCommand;
			break;
		case FILTER_NEWS:
			command = filterNewsCommand;
			break;
		default:
			throw new CommandException("error: command isn't correct");
		}
		return command;
	}
	
}

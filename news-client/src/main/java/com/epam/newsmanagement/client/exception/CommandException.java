package com.epam.newsmanagement.client.exception;

public class CommandException extends Exception {

	private static final long serialVersionUID = 1213242423423423234L;

	public CommandException() {
		
	}

	public CommandException(String e) {
		super(e);
	}

	public CommandException(Throwable e) {
		super(e);
	}


}
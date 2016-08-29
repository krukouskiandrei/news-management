package com.epam.newsmanagement.common.exception.service;

/**
 * uses how wrapper for {@link java.sql.SQLException}
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = 3244234324253434534L;
	
    public ServiceException(){}

    public ServiceException(Throwable e){
        super(e);
    }

}
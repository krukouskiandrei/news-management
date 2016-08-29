package com.epam.newsmanagement.common.exception.service;

/**
 * uses how wrapper for {@link java.sql.SQLException}
 */
public class ServiceException extends Exception {

<<<<<<< HEAD
	private static final long serialVersionUID = 3244234324253434534L;
	
=======
>>>>>>> 10563c064db22e544a6b8280f9d38a6262005b53
    public ServiceException(){}

    public ServiceException(Throwable e){
        super(e);
    }

}

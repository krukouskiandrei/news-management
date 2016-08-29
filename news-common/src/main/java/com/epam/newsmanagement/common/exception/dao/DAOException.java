package com.epam.newsmanagement.common.exception.dao;

/**
 * uses how wrapper for {@link java.sql.SQLException}
 */
public class DAOException extends Exception {

	private static final long serialVersionUID = 3244242342342342342L;
	
    public DAOException(){}
    public DAOException(Throwable throwable){
        super(throwable);
    }

}

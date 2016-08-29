package com.epam.newsmanagement.common.exception.dao;

/**
 * uses how wrapper for {@link java.sql.SQLException}
 */
public class DAOException extends Exception {

<<<<<<< HEAD
	private static final long serialVersionUID = 3244242342342342342L;
	
=======
>>>>>>> 10563c064db22e544a6b8280f9d38a6262005b53
    public DAOException(){}
    public DAOException(Throwable throwable){
        super(throwable);
    }

}

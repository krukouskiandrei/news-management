package com.epam.newsmanagement.common.exception.dao;

/**
 * uses how wrapper for {@link java.sql.SQLException}
 */
public class DAOException extends Exception {

    public DAOException(){}
    public DAOException(Throwable throwable){
        super(throwable);
    }

}

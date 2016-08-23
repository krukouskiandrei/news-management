package com.epam.newsmanagement.common.exception.service;

/**
 * uses how wrapper for {@link java.sql.SQLException}
 */
public class ServiceException extends Exception {

    public ServiceException(){}

    public ServiceException(Throwable e){
        super(e);
    }

}

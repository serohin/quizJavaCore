package com.kamazz.quiz.dao.exception;

/**
 * Created by kamazz on 26.09.16.
 */
public class DaoSystemException extends  DaoException {

    public DaoSystemException(String message) {
        super(message);
    }

    public DaoSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}

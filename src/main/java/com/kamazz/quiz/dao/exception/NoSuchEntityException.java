package com.kamazz.quiz.dao.exception;

/**
 * Created by kamazz on 27.09.16.
 */
public class NoSuchEntityException extends DaoBusinessException {
    public NoSuchEntityException(String message) {
        super(message);
    }

    public NoSuchEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}

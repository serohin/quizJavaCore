package com.kamazz.quiz.dao.interfaces;

import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.model.User;

import java.sql.Connection;


public interface UserDao {
    User getUserByName(String userName, Connection conn) throws NoSuchEntityException, DaoSystemException;

    User insertNewUser(User user, Connection conn) throws DaoSystemException;
}

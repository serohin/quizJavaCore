package com.kamazz.quiz.dao.impl;


import com.kamazz.quiz.dao.exception.DaoSystemException;
import com.kamazz.quiz.dao.exception.NoSuchEntityException;
import com.kamazz.quiz.dao.interfaces.UserDao;
import com.kamazz.quiz.entity.User;

import java.sql.*;

public class UserDaoImpl implements UserDao {
    public static final String SELECT_USER_BY_USERNAME = "SELECT id,username,password FROM user WHERE username=?";
    public static final String INSERT_NEW_USER = "INSERT INTO user (username, password) VALUES (?, ?)";

    @Override
    public User getUserByName(String userName, Connection conn) throws NoSuchEntityException, DaoSystemException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id,username,password FROM user WHERE username = '" + userName + "'")) {
            if (!rs.next()) {
                throw new NoSuchEntityException("No User for userName='" + userName + "'");
            } else {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                if (rs.next()) {
                    throw new DaoSystemException("DB contains more than 1 user in 'QuizDB.user' for userName='" + username + "'.");
                }
                return new User(id, username, password);
            }
        } catch (SQLException e) {
            throw new DaoSystemException("Some JDBC error for executeQuery SQL='" + SELECT_USER_BY_USERNAME + "'", e);
        }finally {
            System.out.println();
        }
    }

    @Override
    public User insertNewUser(User user, Connection conn) throws DaoSystemException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_NEW_USER, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();
            //чтоб в контроллере 2 раза не ловить NosuchEntityEx...
            //Так красивше, если если ключ сгенерирован, знач вставлен юзер.
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new DaoSystemException("Can't get auto generated key for executeUpdate SQL='" + INSERT_NEW_USER + "'");
                } else {
                    return new User(generatedKeys.getInt(1), user.getUsername(), user.getPassword());
                }
            }
        } catch (SQLException e) {
            throw new DaoSystemException("Some JDBC error for executeQuery SQL='" + INSERT_NEW_USER + "'", e);
        }
    }
}

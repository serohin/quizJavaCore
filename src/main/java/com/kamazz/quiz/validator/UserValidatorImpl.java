package com.kamazz.quiz.validator;

import com.kamazz.quiz.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserValidatorImpl implements UserValidator {
    @Override
    public Map<String, String> validate(User entity) {
        Map<String, String> errorMap = new HashMap<>();
        userNameValidate(entity.getUsername(), errorMap);
        passwordValidate(entity.getPassword(), errorMap);
        return errorMap;
    }

    private void passwordValidate(String password, Map<String, String> errorMap) {
        if (password == null) {
            errorMap.put("passwordNull","username == null");
        }else if(password.length() < 4){
            errorMap.put("password","не менее 4-х символов");
        }else if(password.length() > 20){
            errorMap.put("password","не более 20-ти символов");
        }else if(password.trim().equals("")){
            errorMap.put("password", "заполните пароль");
        }
    }

    private void userNameValidate(String username, Map<String, String> errorMap) {
        if (username == null) {
            errorMap.put("userNameNull","username == null");
        }else if(username.length() < 4){
            errorMap.put("userName","не менее 4-х символов");
        }else if(username.length() > 20){
            errorMap.put("userName","не более 20-ти символов");
        }else if(username.trim().equals("")){
            errorMap.put("username", "заполните username");
        }
    }
}

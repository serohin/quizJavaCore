package com.kamazz.quiz.validator;

import com.kamazz.quiz.model.User;

import java.util.Map;

public interface UserValidator extends Validator<User> {
    @Override
    public Map<String, String> validate(User entity);
}
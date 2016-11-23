package com.kamazz.quiz.validator;

import java.util.Map;


public interface RequestParameterValidator extends Validator<String> {
    @Override
    Map<String, String> validate(String str);

    Map<String, String> validateId(String strId);

}

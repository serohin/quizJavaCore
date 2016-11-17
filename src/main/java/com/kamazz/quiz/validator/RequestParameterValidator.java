package com.kamazz.quiz.validator;

import java.util.Map;

/**
 * Created by kamazz on 15.11.16.
 */
public interface RequestParameterValidator extends Validator<String> {
    @Override
    Map<String, String> validate(String str);

    Map<String, String> validateId(String strId);

}

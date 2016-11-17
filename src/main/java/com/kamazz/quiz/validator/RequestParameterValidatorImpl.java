package com.kamazz.quiz.validator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kamazz on 15.11.16.
 */
public class RequestParameterValidatorImpl implements RequestParameterValidator {

    @Override
    public Map<String, String> validate(String str) {
        Map<String, String> errorMap = new HashMap<>();
        if (str == null) {
            errorMap.put("string", "str == null");
        }
        return errorMap;
    }

    public Map<String, String> validate(String str,Map<String,String> errorMap) {
            if (str == null) {
            errorMap.put("string", "str == null");
        }
        return errorMap;
    }


    public Map<String, String> validateId(String strId){
        Map<String, String> errorMap = new HashMap<>();
        validate(strId,errorMap);

        if(errorMap.isEmpty()){
            try {
                Integer.valueOf(strId);
            }catch (NumberFormatException e){
                errorMap.put("string","не удалось извлечь int из strId");
            }
        }
        return errorMap;
    }
}

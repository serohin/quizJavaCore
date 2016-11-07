package com.kamazz.injection;

import com.kamazz.quiz.filter.BaseFilter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.lang.reflect.Field;
import java.util.List;


public abstract class DependencyInjectionFilter extends BaseFilter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            List<Field> allFields = FieldReflector.collectAllFields(this.getClass(), DependencyInjectionFilter.class);
            List<Field> injectFields = FieldReflector.filterInjectableFields(allFields);

            for (Field field : injectFields) {
                field.setAccessible(true);
                Inject annotation = field.getAnnotation(Inject.class);
                String beanName = annotation.value();
                Object bean = AppContext.getInstance().getBean(beanName);

                if (bean == null) {

                    //throw new ServletException("There isn't bean with name '" + beanName + " in application context.");
                }
                field.set(this, bean);
            }
        }catch (Exception e){
            throw new ServletException("Can't inject bean", e);
        }

    }
}


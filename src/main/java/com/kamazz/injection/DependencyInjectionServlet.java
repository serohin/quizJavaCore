package com.kamazz.injection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.lang.reflect.Field;
import java.util.List;


public class DependencyInjectionServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        try{
            List<Field> allFields = FieldReflector.collectAllFields(this.getClass(), DependencyInjectionServlet.class);
            List<Field> injectFields = FieldReflector.filterInjectableFields(allFields);

            for(Field field : injectFields){
                field.setAccessible(true);
                Inject annotation = field.getAnnotation(Inject.class);
                String  beanName = annotation.value();
                Object bean  = AppContext.getInstance().getBean(beanName);
                if (bean == null){
                    //throw new ServletException("There isn't bean with name '" + beanName + " in application context.");
                }
                field.set(this,bean);
            }

        } catch (Exception e) {
            throw new  ServletException("Can't inject bean", e);
        }
    }
}

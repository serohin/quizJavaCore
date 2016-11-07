package com.kamazz.injection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class AppContext implements ServletContextListener {

    private static final String APP_CTX_PATH = "applicationContextConfigLocation";

    // ApplicationContext implementations are Thread-safe (see Spring docs)
    private static ClassPathXmlApplicationContext appCtx;

    public static ApplicationContext getInstance() {
        return appCtx;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        ServletContext servletContext = servletContextEvent.getServletContext();
        String appCthPath = servletContext.getInitParameter(APP_CTX_PATH);

        if (appCthPath == null) {
            throw new RuntimeException("I need init param " + APP_CTX_PATH + " in web.xml");
        }
        appCtx = new ClassPathXmlApplicationContext(appCthPath);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        appCtx.destroy();

    }
}


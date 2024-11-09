package com.crm.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringContext {
    private static AnnotationConfigApplicationContext context;

    private SpringContext() {}

    public AnnotationConfigApplicationContext getContext() {
        if (context == null) {
            context = new AnnotationConfigApplicationContext(AppConfig.class);
        }

        return context;
    }

    public static void closeContext() {
        if (context != null) {
            context.close();
        }
    }

    public <T> T getBean(Class<T> tClass) {
        context = getContext();
        return context.getBean(tClass);
    }
}

package com.usm.UserManagmentService.Config;

import com.usm.UserManagmentService.Logger.VisitorLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    private VisitorLogger visitorLogger;

    /**
     * > The addInterceptors() function is used to register interceptors
     *
     * @param registry The registry to add the interceptor to.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(visitorLogger);
    }
}

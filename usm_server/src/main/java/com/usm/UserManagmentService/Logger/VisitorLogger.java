package com.usm.UserManagmentService.Logger;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.usm.UserManagmentService.Entity.Visit;
import com.usm.UserManagmentService.Utils.Builders.VisitBuilder;
import com.usm.UserManagmentService.Service.VisitServiceImpl;
import com.usm.UserManagmentService.Utils.HttpRequestResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * This class is used to log the visitor's information.
 */
@Component
public class VisitorLogger implements HandlerInterceptor {

    @Autowired
    private VisitServiceImpl visitorServiceImpl;

    private VisitBuilder visitBuilder;

    /**
     * It saves the visitor's information to the database.
     *
     * @param request  The request object.
     * @param response The response object.
     * @param handler  The handler that is being executed.
     * @return A boolean value.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        final String ip = HttpRequestResponseUtils.getClientIpAddress();
        final String url = HttpRequestResponseUtils.getRequestUrl();
        final String page = HttpRequestResponseUtils.getRequestUri();
        final String refererPage = HttpRequestResponseUtils.getRefererPage();
        final String queryString = HttpRequestResponseUtils.getPageQueryString();
        final String userAgent = HttpRequestResponseUtils.getUserAgent();
        final String requestMethod = HttpRequestResponseUtils.getRequestMethod();
        final LocalDateTime timestamp = LocalDateTime.now();

        Visit visit = new VisitBuilder()
                .setUser(HttpRequestResponseUtils.getAuthenticatedUsername())
                .setIp(ip)
                .setMethod(requestMethod)
                .setUrl(url)
                .setPage(page)
                .setQueryString(queryString)
                .setRefererPage(refererPage)
                .setUserAgent(userAgent)
                .setLoggedTime(timestamp)
                .setUniqueVisit(true).build();

        visitorServiceImpl.saveVisitorInfo(visit);

        return true;
    }
}

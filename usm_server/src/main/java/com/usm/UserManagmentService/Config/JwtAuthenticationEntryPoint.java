package com.usm.UserManagmentService.Config;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -7858869558953243875L;

    /**
     * If the user is not authenticated, send an error response with the status code 401 (Unauthorized) and the message
     * "Unauthorized"
     *
     * @param request       The request object.
     * @param response      The response object that will be used to send the error.
     * @param authException The exception thrown by the authentication manager.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {


        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}

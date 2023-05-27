package com.usm.UserManagmentService.Utils;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public final class HttpRequestResponseUtils {

    private HttpRequestResponseUtils() {
    }

    private static final String[] IP_HEADER_CANDIDATES = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR"};

    /**
     * If the request is not null, then get the request and check the header for the IP address. If the header is not null,
     * then get the IP address from the header. If the header is null, then get the IP address from the request.
     *
     * @return The IP address of the client.
     */
    public static String getClientIpAddress() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "0.0.0.0";
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        for (String header : IP_HEADER_CANDIDATES) {
            String ipList = request.getHeader(header);
            if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
                String ip = ipList.split(",")[0];
                return ip;
            }
        }

        return request.getRemoteAddr();
    }

    /**
     * If the current request is a web request, return the URL of the request. Otherwise, return an empty string.
     *
     * @return The URL of the request.
     */
    public static String getRequestUrl() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "";
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        return request.getRequestURL().toString();
    }

    /**
     * If the request context is not null, get the request URI from the request context.
     *
     * @return The request URI.
     */
    public static String getRequestUri() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "";
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        return request.getRequestURI();
    }

    /**
     * If the request is null, return an empty string. Otherwise, get the request, get the referer header, and return it.
     *
     * @return The referer page.
     */
    public static String getRefererPage() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "";
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        String referer = request.getHeader("Referer");

        return referer != null ? referer : request.getHeader("referer");
    }

    /**
     * If the current request is a web request, return the query string. Otherwise, return an empty string.
     *
     * @return The query string of the current request.
     */
    public static String getPageQueryString() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "";
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        return request.getQueryString();
    }

    /**
     * If the request is null, return an empty string. Otherwise, get the user agent from the request.
     *
     * @return The user agent string of the browser that made the request.
     */
    public static String getUserAgent() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "";
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        String userAgent = request.getHeader("User-Agent");

        return userAgent != null ? userAgent : request.getHeader("user-agent");
    }

    /**
     * If the request context is not null, get the request method from the request context.
     *
     * @return The request method (GET, POST, PUT, DELETE, etc.).
     */
    public static String getRequestMethod() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "";
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        return request.getMethod();
    }

    /**
     * If the user is present, get the username from the user object and return it. Otherwise, return "unauthorized".
     *
     * @return The username of the authenticated user.
     */
    public static String getAuthenticatedUsername() {

        String username = "unauthorized";

        if (isUserPresent()) {

            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            username = user.getUsername();

            return username;
        }

        return username;
    }

    /**
     * If the current user is not anonymous, then return true.
     *
     * @return A boolean value.
     */
    private static boolean isUserPresent() {
        return SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }
}
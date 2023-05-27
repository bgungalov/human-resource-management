package com.usm.UserManagmentService.Entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * This is a class that represents a visit to a web page.
 */
@Entity
@Table(name = "user_action")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String user;
    private String ip;
    private String method;
    private String url;
    private String page;
    private String queryString;
    private String refererPage;
    private String userAgent;
    private LocalDateTime loggedTime;
    private boolean uniqueVisit;

    public Visit() {
    }

    public Visit(int id, String user, String ip, String method, String url, String page, String queryString, String refererPage, String userAgent, LocalDateTime loggedTime, boolean uniqueVisit) {
        this.id = id;
        this.user = user;
        this.ip = ip;
        this.method = method;
        this.url = url;
        this.page = page;
        this.queryString = queryString;
        this.refererPage = refererPage;
        this.userAgent = userAgent;
        this.loggedTime = loggedTime;
        this.uniqueVisit = uniqueVisit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getRefererPage() {
        return refererPage;
    }

    public void setRefererPage(String refererPage) {
        this.refererPage = refererPage;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public LocalDateTime getLoggedTime() {
        return loggedTime;
    }

    public void setLoggedTime(LocalDateTime loggedTime) {
        this.loggedTime = loggedTime;
    }

    public boolean isUniqueVisit() {
        return uniqueVisit;
    }

    public void setUniqueVisit(boolean uniqueVisit) {
        this.uniqueVisit = uniqueVisit;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", ip='" + ip + '\'' +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", page='" + page + '\'' +
                ", queryString='" + queryString + '\'' +
                ", refererPage='" + refererPage + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", loggedTime=" + loggedTime +
                ", uniqueVisit=" + uniqueVisit +
                '}';
    }
}

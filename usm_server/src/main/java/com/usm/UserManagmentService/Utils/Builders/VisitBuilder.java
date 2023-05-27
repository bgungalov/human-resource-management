package com.usm.UserManagmentService.Utils.Builders;

import com.usm.UserManagmentService.Entity.Visit;

import java.time.LocalDateTime;

/**
 * The VisitBuilder class is a builder for the Visit class.
 */
public class VisitBuilder {
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

    public Visit build() {
        return new Visit(this.id, this.user, this.ip, this.method, this.url, this.page,
                this.queryString, this.refererPage, this.userAgent, this.loggedTime, this.uniqueVisit);
    }

    public VisitBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public VisitBuilder setUser(String user) {
        this.user = user;
        return this;
    }

    public VisitBuilder setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public VisitBuilder setMethod(String method) {
        this.method = method;
        return this;
    }

    public VisitBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public VisitBuilder setPage(String page) {
        this.page = page;
        return this;
    }

    public VisitBuilder setQueryString(String queryString) {
        this.queryString = queryString;
        return this;
    }

    public VisitBuilder setRefererPage(String refererPage) {
        this.refererPage = refererPage;
        return this;
    }

    public VisitBuilder setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public VisitBuilder setLoggedTime(LocalDateTime loggedTime) {
        this.loggedTime = loggedTime;
        return this;
    }

    public VisitBuilder setUniqueVisit(boolean uniqueVisit) {
        this.uniqueVisit = uniqueVisit;
        return this;
    }
}

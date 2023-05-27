package com.usm.UserManagmentService.Entity;

import java.io.Serial;
import java.io.Serializable;

/**
 * It's a class that contains the JWT token.
 */
public class JwtResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -8091879091924046844L;

    private final String jwtToken;

    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getToken() {
        return this.jwtToken;
    }
}
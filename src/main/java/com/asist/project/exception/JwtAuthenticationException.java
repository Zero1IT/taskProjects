package com.asist.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

/**
 * createdAt: 11/14/2020
 * project: ProjectsPanel
 *
 * @author Alexander Petrushkin
 */
public class JwtAuthenticationException extends AuthenticationException {
    private HttpStatus status;

    public JwtAuthenticationException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }

    public HttpStatus getStatus() {
        return status;
    }
}

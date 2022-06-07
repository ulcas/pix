package com.lucascarvalho.api.itau.pix.exception;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class ErrorInfo {

    private final String url;
    private final String message;

    public ErrorInfo(HttpServletRequest request, String message) {
        this.url = request.getRequestURL().toString();
        this.message = message;
    }
}

package com.lucascarvalho.api.itau.pix.exception;

import com.lucascarvalho.api.itau.pix.validate.ValidateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    public ErrorInfo handleUnprocessableEntity(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        return new ErrorInfo(request, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ValidateException.class)
    @ResponseBody
    public ErrorInfo handleValidate(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        return new ErrorInfo(request, exception.getMessage());
    }

}

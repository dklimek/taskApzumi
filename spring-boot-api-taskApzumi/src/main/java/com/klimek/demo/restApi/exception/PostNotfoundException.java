package com.klimek.demo.restApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No post with this id in database")
public class PostNotfoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
}

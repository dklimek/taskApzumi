package com.klimek.demo.restApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Post with this id exists in database")
public class PostExistsWithThisIdException extends RuntimeException {
    private static final long serialVersionUID = 2L;
}

package com.klimek.demo.restApi.exception;


import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class PostExceptionController {

    @ExceptionHandler(PostNotfoundException.class)
    public ResponseEntity<Object> PostNotfoundException(PostNotfoundException e) {
        JSONObject response = new JSONObject();
        response.put("message", e.getMessage());
        return new ResponseEntity<>(response.toString(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostExistsWithThisIdException.class)
    public ResponseEntity<String> PostExistsWithThisIdException(PostExistsWithThisIdException e) {
        JSONObject response = new JSONObject();
        response.put("message", e.getMessage());
        return new ResponseEntity<>(response.toString(), HttpStatus.BAD_REQUEST);
    }

}

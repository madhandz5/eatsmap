package com.eatsmap.module.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
* @package : com.eatsmap.module.account.exception
* @name : UserNotFoundException.java
* @date : 2021/12/17 1:50 오후
* @author : ryan
* @version : 1.0.0
* @modifyed :
**/
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}

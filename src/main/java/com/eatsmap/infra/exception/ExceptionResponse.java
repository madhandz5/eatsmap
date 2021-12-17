package com.eatsmap.infra.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
* @package : com.eatsmap.infra.exception
* @name : ExceptionResponse.java
* @date : 2021/12/17 1:56 오후
* @author : ryan
* @version : 1.0.0
* @modifyed :
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String details;
}

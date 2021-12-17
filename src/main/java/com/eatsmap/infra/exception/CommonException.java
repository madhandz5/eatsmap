package com.eatsmap.infra.exception;

import com.eatsmap.infra.common.ErrorCode;
import lombok.Getter;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
* @package : com.eatsmap.infra.exception
* @name : CommonException.java
* @date : 2021/12/17 12:30 오후
* @author : ryan
* @version : 1.0.0
* @modifyed :
**/
@Getter
public class CommonException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorDetailMsg;

    public CommonException(ErrorCode errorcode) {
        this.errorCode = errorcode;
        this.errorDetailMsg = getStackTraceMsg(this);
    }

    public CommonException(ErrorCode errorcode, String errorDetailMsg) {
        this.errorCode = errorcode;
        this.errorDetailMsg = errorDetailMsg;
    }

    public CommonException(ErrorCode errorcode, Exception exception) {
        this.errorCode = errorcode;
        this.errorDetailMsg = getStackTraceMsg(exception);
    }

    protected String getStackTraceMsg(Exception exception) {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

}

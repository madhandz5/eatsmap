package com.eatsmap.infra.common;

import lombok.Data;

/**
 * @author : ryan
 * @version : 1.0.0
 * @package : com.eatsmap.infra.common
 * @name : CommonResponse.java
 * @date : 2021/12/17 12:22 오후
 * @modifyed :
 **/
@Data
public class CommonResponse {

    private boolean success;
    private Object data;

    public static CommonResponse createResponse(boolean success, Object data) {
        CommonResponse response = new CommonResponse();
        response.setSuccess(success);
        response.setData(data);
        return response;
    }

}

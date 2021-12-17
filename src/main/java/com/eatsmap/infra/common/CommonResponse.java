package com.eatsmap.infra.common;

import lombok.Data;

import java.util.Map;

/**
* @package : com.eatsmap.infra.common
* @name : CommonResponse.java
* @date : 2021/12/17 12:22 오후
* @author : ryan
* @version : 1.0.0
* @modifyed :
**/
@Data
public class CommonResponse {

    private boolean success;
    private Map<String, Object> data;

    public static CommonResponse createResponse(boolean success, Map<String, Object> data) {
        CommonResponse response = new CommonResponse();
        response.setSuccess(success);
        response.setData(data);
        return response;
    }

}

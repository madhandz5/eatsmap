package com.eatsmap.infra.common;

import com.eatsmap.infra.exception.CommonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : ryan
 * @version : 1.0.0
 * @package : com.eatsmap.infra.common
 * @name : CommonResponseController.java
 * @date : 2021/12/17 12:22 오후
 * @modifyed :
 **/
@RequiredArgsConstructor
public class CommonResponseController {

    private final ObjectMapper objectMapper;

    // Case 1. Fail (Spring Security Exception)


    // Case 2. Fail (Valid Error)
    //  return
    //      success: false,
    //      data: {
    //          getField(): getDefaultMessage(),
    //          ...
    //      }
    protected ResponseEntity<String> createResponse(BindingResult result) {
        Map<String, Object> data = new HashMap<>() {
            {
                put("error", result);
            }
        };
        CommonResponse response = CommonResponse.createResponse(false, data);
        return convertToResponseEntity(response);
    }

    // Case 3. Fail (Service Method Exception)


    // Case 4. Success
    //  return
    //      success: true,
    //      data: {
    //          account: {
    //              email: "hgd@gmail.com",
    //              name: "홍길동",
    //              ...
    //          },
    //          ...
    //      }

    protected ResponseEntity<String> createResponse(Object data) {
        CommonResponse response = CommonResponse.createResponse(true, data);
        return convertToResponseEntity(response);
    }


    private ResponseEntity<String> convertToResponseEntity(CommonResponse response) {
        String responseBody;
        try {
            responseBody = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException exception) {
            throw new CommonException(ErrorCode.JSON_PROCESS_FAIL, exception);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}

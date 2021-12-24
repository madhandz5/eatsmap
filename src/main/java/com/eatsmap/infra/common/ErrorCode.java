package com.eatsmap.infra.common;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : ryan
 * @version : 1.0.0
 * @package : com.eatsmap.infra.common
 * @name : ErrorCode.java
 * @date : 2021/12/17 12:28 오후
 * @modifyed :
 **/
public enum ErrorCode {

    // account, verification (1000)
    ACCOUNT_NOT_FOUND("1001", "Account Not Found", null),
    VERIFICATION_NOT_FOUND("1002", "Verification Not Found", null),
    INVALID_VERIFICATION("1003", "유효하지 않은 인증입니다.", null),

    LOGIN_PROCESS_PASSWORD_NOTMATCH("1007", "비밀번호가 일치하지 않습니다.", null),
    LOGIN_PROCESS_FAILED("1008", "로그인에 실패하였습니다.", null),
    KAKAO_LOGIN_PROCESS_FAILED("1009", "카카오 로그인에 실패하였습니다.", null),
    LOGOUT_DONE("1010", "이미 로그아웃된 계정입니다.", null),


    ACCOUNT_NOT_EQUALS("1011", "유저가 서로 일치하지 않습니다.", null),
    VERIFICATION_NOT_CORRECT("1012", "Verification Not Correct", null),

    // account - jwt (1200)
    JWT_TOKEN_EXPIRED("1201", "토큰이 만료되었습니다.", null),
    JWT_VERIFICATION_FAIL("1202", "로그인 인증에 실패하였습니다.", null),
    JWT_EXCEPTION_FAIL("1203", "로그인 정보가 유효하지 않습니다.", null),
    ACCESS_DENIED("1204", "접근 권한이 없습니다.", null),

    // GROUP (1300)
    GROUP_IS_NOT_EXISTS("1301", "그룹이 존재하지 않습니다.", null),

    // REVIEW (1400)
    REVIEW_IS_NOT_EXISTS("1401", "리뷰가 존재하지 않습니다.", null),

    // etc (9000)
    JSON_PROCESS_FAIL("9001", "Failed From Processing Json", null),
    WRONG_PATH("9002", "Wrong Path", null),
    CONSTRAINT_PROCESS_FAIL("9003", "정보가 서로 일치하지 않습니다.", null),

    FAILED("9999", "Unexpected Error", null);

    private static final Map<String, ErrorCode> errorMap =
            Arrays.stream(values()).collect(Collectors.toMap(ErrorCode::getCode, e -> e));

    public static ErrorCode findByCode(String code) {
        return errorMap.get(code);
    }

    private String code;
    private String errorMsg;
    private String redirectUrl;

    ErrorCode(String code, String msg, String url) {
        this.code = code;
        this.errorMsg = msg;
        this.redirectUrl = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Map<String, String> getErrorMap() {
        return new HashMap<>() {{
            put("code", code);
            put("message", errorMsg);
        }};
    }

    public String getErrorString() {
        return getErrorJson().toString();
    }

    public JSONObject getErrorJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code", code);
            jsonObject.put("message", errorMsg);
        } catch (JSONException ignore) {
        }
        return jsonObject;
    }

    public String getCustomErrorCodeStr() {
        return "ERROR_CODE_" + this.code;
    }

}

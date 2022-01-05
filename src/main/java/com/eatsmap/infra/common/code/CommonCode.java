package com.eatsmap.infra.common.code;

public enum CommonCode {

    DOMAIN("http://localhost:8088"),
    MAIL_SENDER("khfinal3@gmail.com"),
    UPLOAD_PATH("C:\\FINAL\\file-upload\\");

    private String desc;

    CommonCode(String desc){
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }
}

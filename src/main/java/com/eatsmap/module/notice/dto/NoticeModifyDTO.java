package com.eatsmap.module.notice.dto;

import lombok.Data;

@Data
public class NoticeModifyDTO {

    private Long id;

    private String noticeName;

    private String noticeCode;

    private String text;

    private String link;

    private boolean state;  //client로부터 boolean값을 매핑할 수 있는지 체크! default true

}

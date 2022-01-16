package com.eatsmap.module.notice;

import com.eatsmap.infra.common.CommonResponse;
import com.eatsmap.module.notice.dto.NoticeDTO;
import com.eatsmap.module.notice.dto.NoticeModifyDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "18. Admin_Notice")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/admin/notice")
public class NoticeAdminController {

    private final NoticeAdminService noticeAdminService;

    @ApiOperation(value = "관리자 Notice 등록", notes = "name, code, text 지정 필요")
    @PostMapping(path = "/")
    public ResponseEntity addNotice(@RequestBody NoticeDTO noticeDTO){
        Notice notice = noticeAdminService.addNotice(noticeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(notice);
    }

    @ApiOperation(value = "관리자 Notice 수정")
    @PutMapping(path = "/")
    public ResponseEntity modifyNotice(@RequestBody NoticeModifyDTO noticeModifyDTO){
        Notice notice = noticeAdminService.modifyNotice(noticeModifyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(notice);
    }
}

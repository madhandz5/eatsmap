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
        if(notice == null){
            CommonResponse response = CommonResponse.createResponse(false, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        CommonResponse response = CommonResponse.createResponse(true, notice);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ApiOperation(value = "관리자 Notice 수정")
    @PutMapping(path = "/")
    public ResponseEntity modifyNotice(@RequestBody NoticeModifyDTO noticeModifyDTO){
        Notice notice = noticeAdminService.modifyNotice(noticeModifyDTO);
        if(notice == null){
            CommonResponse response = CommonResponse.createResponse(false, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        CommonResponse response = CommonResponse.createResponse(true, notice);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ApiOperation(value = "관리자 Notice 삭제")
    @DeleteMapping(path = "/{noticeId}")
    public ResponseEntity deleteNotice(@PathVariable String noticeId){
        noticeAdminService.deleteNotice(Long.parseLong(noticeId));
        CommonResponse response = CommonResponse.createResponse(true, noticeId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

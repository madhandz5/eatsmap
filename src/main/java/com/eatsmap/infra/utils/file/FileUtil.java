package com.eatsmap.infra.utils.file;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.UUID;

import com.eatsmap.infra.common.code.CommonCode;
import com.eatsmap.infra.common.code.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	
	public Fileinfo fileUpload(MultipartFile mf) {
		
		Fileinfo file = createFileDTO(mf);
		
		try {
			mf.transferTo(new File(getSavePath() + file.getRenameFileName()));
		} catch (IllegalStateException | IOException e) {
			throw new CommonException(ErrorCode.FILE_UPLOAD_ERROR, e);
		}
		
		return file;
	}
	
	private String getSubPath() {
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		int month = today.get(Calendar.MONTH) + 1;
		int date = today.get(Calendar.DATE);
		return year + "/" + month + "/" + date + "/";
	}
	
	private String getSavePath() {
		//2. 저장경로를 웹어플리케이션 외부로 지정
		//		 저장경로를  외부경로 + /연/월/일 형태로 작성
		String subPath = getSubPath();
		String savePath = CommonCode.UPLOAD_PATH.getDesc() + subPath;
		
		File dir = new File(savePath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		return savePath;
	}

	private Fileinfo createFileDTO(MultipartFile mf) {
		Fileinfo fileDTO = new Fileinfo();
		String originFileName = mf.getOriginalFilename();
		String renameFileName = UUID.randomUUID().toString();
		
		if(originFileName.contains(".")) {
			renameFileName = renameFileName += originFileName.substring(originFileName.lastIndexOf("."));
		}
		
		String savePath = getSubPath();
		
		fileDTO.setOriginFileName(originFileName);
		fileDTO.setRenameFileName(renameFileName);
		fileDTO.setSavePath(savePath);
		fileDTO.setRegDate(LocalDate.now());
		return fileDTO;
	}

}

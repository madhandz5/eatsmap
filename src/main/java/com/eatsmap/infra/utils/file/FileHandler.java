package com.eatsmap.infra.utils.file;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

import com.eatsmap.infra.common.code.CommonCode;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FileHandler {
	
	@GetMapping("download")
	public ResponseEntity<UrlResource> downloadFile(Fileinfo file) throws MalformedURLException, UnsupportedEncodingException{
		
		UrlResource resource = new UrlResource(CommonCode.DOMAIN.getDesc() + file.getDownloadURL());
		
		String originFileName = URLEncoder.encode(file.getOriginFileName(), "UTF-8");
		
		ResponseEntity<UrlResource> response = 
				ResponseEntity.ok().header("Content-Disposition", "attachment; filename="+originFileName)
				.body(resource);
		
		return response;
	}
	

}

package com.eatsmap.infra.utils.file;

import java.time.LocalDate;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@SequenceGenerator(name = "file_seq", sequenceName = "file_seq", initialValue = 1001)
public class Fileinfo {
	
	@Id
	@GeneratedValue
	@Column(name = "file_id")
	private Long id;

	private Long typeId;		//회원 id, review id, group id
	private String originFileName;
	private String renameFileName;
	private String savePath;
	private LocalDate regDate;
	private boolean deleted;
	
	public String getDownloadURL() {
		return "/file/" + savePath + renameFileName;
	}
}

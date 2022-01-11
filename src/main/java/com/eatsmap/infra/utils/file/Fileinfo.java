package com.eatsmap.infra.utils.file;

import java.time.LocalDate;

import com.eatsmap.module.group.MemberGroup;
import com.eatsmap.module.review.Review;
import lombok.Data;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@SequenceGenerator(name = "file_seq", sequenceName = "file_seq", initialValue = 1001)
public class Fileinfo {
	
	@Id
	@GeneratedValue
	@Column(name = "file_id")
	private Long id;

//	private Long typeId;		//회원 id, review id, group id

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "review_id")
	private Review review;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "membergroup_id")
	private MemberGroup memberGroup;

	private String originFileName;
	private String renameFileName;
	private String savePath;
	private LocalDate regDate;
	private boolean deleted;
	
	public String getDownloadURL() {
		return "/file/" + savePath + renameFileName;
	}
}

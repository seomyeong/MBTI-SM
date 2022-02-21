package com.mycompany.myapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CultureBoardComment {
	private long id;
	private Member member;
	private CultureBoard cultureBoard;
	private String comment;
	private int likes;
	private boolean likesStatus;
	private String reportingDate;
	
	public CultureBoardComment() {}
	
	public CultureBoardComment(long id, Member member, CultureBoard cultureBoard, String comment, int likes,
			boolean likesStatus, String reportingDate) {
		super();
		this.id = id;
		this.member = member;
		this.cultureBoard = cultureBoard;
		this.comment = comment;
		this.likes = likes;
		this.likesStatus = likesStatus;
		this.reportingDate = reportingDate;
	}
	
	
	public CultureBoardComment(Member member, CultureBoard cultureBoard, String comment) {
		super();
		this.member = member;
		this.cultureBoard = cultureBoard;
		this.comment = comment;
	}
	
	
	
	


}
